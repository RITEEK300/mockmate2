let receiverClient = null;
let receiverUniqueId = null;
let receiverSession = null;
let answerCount = 0;
let sessionStartTime = null;
let sessionTimerInterval = null;

async function receiverLogin() {
    const username = document.getElementById("username").value.trim();
    const password = document.getElementById("password").value;
    const loginBtn = document.getElementById("loginBtn");

    // Show loading state
    loginBtn.disabled = true;
    loginBtn.innerHTML = '<span class="spinner-custom me-2"></span> Logging in...';

    const res = await fetch("/api/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password })
    });

    // Reset button state
    loginBtn.disabled = false;
    loginBtn.innerHTML = '<i class="bi bi-check-circle"></i> Login to Receive Answers';

    if (!res.ok) {
        showToast("Login failed. Please check your credentials.", "danger");
        return;
    }

    const data = await res.json();
    receiverSession = data;
    receiverUniqueId = data.uniqueId;
    document.getElementById("uniqueIdBox").innerText = receiverUniqueId;
    postLoginUi();
    connectReceiverSocket();
    loadHistory();
    startSessionTimer();
    showToast("Successfully logged in!", "success");
}

function connectReceiverSocket() {
    receiverClient = new StompJs.Client({
        brokerURL: `ws://${window.location.host}/ws?uid=${receiverUniqueId}`,
        reconnectDelay: 3000
    });

    receiverClient.onConnect = () => {
        setReceiverStatus(true);
        receiverClient.subscribe("/user/queue/answers", (frame) => {
            const payload = JSON.parse(frame.body);
            prependAnswer(payload);
        });
    };

    receiverClient.onWebSocketClose = () => setReceiverStatus(false);
    receiverClient.activate();
}

function setReceiverStatus(online) {
    const node = document.getElementById("receiverStatus");
    if (online) {
        node.className = "status-online";
        document.getElementById("receiverMode").innerHTML = '<i class="bi bi-circle-fill"></i> Active';
        showToast("Connected to server", "success");
    } else {
        node.className = "status-offline";
        document.getElementById("receiverMode").innerHTML = '<i class="bi bi-circle"></i> Offline';
        showToast("Disconnected from server", "warning");
    }
}

async function loadHistory() {
    const res = await fetch(`/api/receiver/history/${receiverUniqueId}`);
    const history = await res.json();
    const box = document.getElementById("answersContainer");
    
    if (history.length === 0) {
        box.innerHTML = `
            <div class="text-center py-5 text-muted">
                <i class="bi bi-hourglass-split display-4 mb-3 d-block"></i>
                <p>No history yet</p>
                <small>Answers will appear here as senders submit them</small>
            </div>`;
    } else {
        box.innerHTML = "";
        answerCount = history.length;
        document.getElementById("answerCount").innerText = answerCount;
        history.reverse().forEach(prependAnswer);
    }
}

function prependAnswer(item) {
    const box = document.getElementById("answersContainer");
    
    // Remove empty state if present
    if (box.querySelector('.text-center')) {
        box.innerHTML = "";
    }
    
    const card = document.createElement("div");
    card.className = "answer-card animate-fade-up";
    card.innerHTML = `
        <div class="answer-header">
            <strong>Q:</strong> ${escapeHtml(item.question || "-")}
        </div>
        <div class="answer-body">
            <strong>A:</strong> ${escapeHtml(item.answer || "-")}
        </div>
        <div class="answer-meta">
            <span><i class="bi bi-person"></i> Source: ${item.source || "N/A"}</span>
            <span><i class="bi bi-clock"></i> ${formatTime(item.timestamp)}</span>
        </div>`;
    box.prepend(card);
    
    // Update answer count
    answerCount++;
    document.getElementById("answerCount").innerText = answerCount;
    
    // Limit to 30 answers
    while (box.children.length > 30) box.removeChild(box.lastChild);
    
    // Scroll to top
    card.scrollIntoView({ behavior: "smooth", block: "start" });
    
    showToast("New answer received!", "info");
}

async function receiverLogout() {
    await fetch("/api/auth/logout", { method: "POST" });
    if (receiverClient) receiverClient.deactivate();
    receiverSession = null;
    setReceiverStatus(false);
    
    // Stop session timer
    if (sessionTimerInterval) {
        clearInterval(sessionTimerInterval);
        sessionTimerInterval = null;
    }
    
    // Reset UI
    document.getElementById("receiverMode").innerHTML = '<i class="bi bi-circle"></i> Standby';
    document.getElementById("receiverProfileButton").classList.add("d-none");
    document.getElementById("receiverProfileMenu").classList.add("d-none");
    document.getElementById("receiverIdentityPanel").classList.add("d-none");
    document.getElementById("receiverFeedPanel").classList.add("d-none");
    document.getElementById("receiverLoginPanel").classList.remove("d-none");
    document.getElementById("answersContainer").innerHTML = `
        <div class="text-center py-5 text-muted">
            <i class="bi bi-hourglass-split display-4 mb-3 d-block"></i>
            <p>Waiting for answers...</p>
            <small>Answers will appear here as senders submit them</small>
        </div>`;
    
    // Reset counters
    answerCount = 0;
    document.getElementById("answerCount").innerText = "0";
    document.getElementById("sessionTime").innerText = "00:00";
    
    showToast("Logged out successfully", "info");
}

function postLoginUi() {
    document.getElementById("receiverLoginPanel").classList.add("d-none");
    document.getElementById("receiverIdentityPanel").classList.remove("d-none");
    document.getElementById("receiverFeedPanel").classList.remove("d-none");
    document.getElementById("receiverProfileButton").classList.remove("d-none");
    document.getElementById("receiverUserLabel").innerText = receiverSession.username;
    document.getElementById("receiverProfileInfo").innerHTML = `
        <strong>User:</strong> ${receiverSession.username}<br>
        <strong>Role:</strong> ${receiverSession.role}<br>
        <strong>Unique ID:</strong> ${receiverUniqueId}`;
}

function copyUniqueId() {
    if (!receiverUniqueId) return;
    navigator.clipboard.writeText(receiverUniqueId);
    showToast("Receiver ID copied to clipboard!", "success");
}

function clearAllAnswers() {
    const box = document.getElementById("answersContainer");
    box.innerHTML = `
        <div class="text-center py-5 text-muted">
            <i class="bi bi-hourglass-split display-4 mb-3 d-block"></i>
            <p>Waiting for answers...</p>
            <small>Answers will appear here as senders submit them</small>
        </div>`;
    answerCount = 0;
    document.getElementById("answerCount").innerText = "0";
    showToast("All answers cleared", "info");
}

function startSessionTimer() {
    sessionStartTime = new Date();
    sessionTimerInterval = setInterval(updateSessionTime, 1000);
}

function updateSessionTime() {
    if (!sessionStartTime) return;
    const now = new Date();
    const diff = Math.floor((now - sessionStartTime) / 1000);
    
    const hours = Math.floor(diff / 3600);
    const minutes = Math.floor((diff % 3600) / 60);
    const seconds = diff % 60;
    
    const timeStr = hours > 0 
        ? `${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`
        : `${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`;
    
    document.getElementById("sessionTime").innerText = timeStr;
}

function formatTime(ts) {
    if (!ts) return "-";
    const d = new Date(ts);
    return Number.isNaN(d.getTime()) ? ts : d.toLocaleString();
}

function escapeHtml(value) {
    return value
        .replaceAll("&", "&amp;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;")
        .replaceAll('"', "&quot;")
        .replaceAll("'", "&#39;");
}
