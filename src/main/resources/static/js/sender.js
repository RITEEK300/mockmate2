let senderClient = null;
let senderTarget = null;
let senderSession = null;

async function senderLogin() {
    const username = document.getElementById("senderUsername").value.trim();
    const password = document.getElementById("senderPassword").value;
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
    loginBtn.innerHTML = '<i class="bi bi-check-circle"></i> Login as Sender';

    if (!res.ok) {
        showToast("Sender login failed. Check credentials and try again.", "danger");
        return;
    }
    senderSession = await res.json();
    postLoginUi();
    connectSenderSocket();
    showToast("Successfully logged in!", "success");
}

function connectSenderSocket() {
    senderClient = new StompJs.Client({
        brokerURL: `ws://${window.location.host}/ws?uid=sender-${Date.now()}`,
        reconnectDelay: 3000
    });
    senderClient.onConnect = () => updateSenderStatus(true);
    senderClient.onWebSocketClose = () => updateSenderStatus(false);
    senderClient.activate();
}

function updateSenderStatus(online) {
    const statusBadge = document.getElementById("senderStatusBadge");
    if (online) {
        statusBadge.className = "pulse-dot";
        showToast("Connected to server", "success");
    } else {
        statusBadge.className = "pulse-dot warning";
        showToast("Disconnected from server", "warning");
    }
}

function bindTarget() {
    senderTarget = document.getElementById("targetUniqueId").value.trim();
    if (!senderTarget) {
        showToast("Enter receiver unique ID first.", "warning");
        return;
    }
    document.getElementById("bindingStatus").innerHTML = `<i class="bi bi-link-45deg"></i> Bound: ${senderTarget}`;
    document.getElementById("bindingStatus").className = "badge bg-success";
    showToast(`Connected target set to ${senderTarget}`, "success");
}

function sendViaWs() {
    const question = document.getElementById("questionText").value.trim();
    if (!senderTarget) {
        showToast("Bind a receiver unique ID first.", "warning");
        return;
    }
    if (!senderClient || !senderClient.connected) {
        showToast("WebSocket is not connected yet.", "warning");
        return;
    }
    if (!question) {
        showToast("Please enter a question.", "warning");
        return;
    }
    const payload = {
        uniqueId: senderTarget,
        question: question
    };
    senderClient.publish({
        destination: "/app/send",
        body: JSON.stringify(payload)
    });
    prependSentAnswer({
        question,
        answer: "Live delivery queued to receiver feed.",
        source: "WS_DISPATCHED",
        timestamp: new Date().toISOString()
    });
    showToast("Question sent using WebSocket.", "success");
    document.getElementById("questionText").value = "";
}

async function previewAnswer() {
    const question = document.getElementById("questionText").value.trim();
    if (!question) {
        showToast("Please enter a question first.", "warning");
        return;
    }
    const res = await fetch("/api/sender/preview", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ uniqueId: senderTarget || "preview", question })
    });
    if (!res.ok) {
        const text = await res.text();
        showToast("Preview failed: " + text, "danger");
        return;
    }
    const payload = await res.json();
    prependSentAnswer(payload);
    showToast(`Preview ready from ${payload.source}.`, "success");
}

async function sendViaRest() {
    const question = document.getElementById("questionText").value.trim();
    if (!senderTarget) {
        showToast("Bind a receiver unique ID first.", "warning");
        return;
    }
    if (!question) {
        showToast("Please enter a question.", "warning");
        return;
    }
    const res = await fetch("/api/sender/generate", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ uniqueId: senderTarget, question })
    });
    if (!res.ok) {
        const text = await res.text();
        showToast("Error: " + text, "danger");
        return;
    }
    const payload = await res.json();
    prependSentAnswer(payload);
    document.getElementById("questionText").value = "";
    showToast(`Answer generated from ${payload.source}.`, "success");
}

async function senderLogout() {
    await fetch("/api/auth/logout", { method: "POST" });
    if (senderClient) senderClient.deactivate();
    senderSession = null;
    senderTarget = null;
    
    document.getElementById("senderWorkspace").classList.add("d-none");
    document.getElementById("senderResponses").classList.add("d-none");
    document.getElementById("loginPanel").classList.remove("d-none");
    document.getElementById("profileButton").classList.add("d-none");
    document.getElementById("profileMenu").classList.add("d-none");
    
    // Reset binding status
    document.getElementById("bindingStatus").innerHTML = '<i class="bi bi-circle-fill"></i> Not Connected';
    document.getElementById("bindingStatus").className = "badge bg-warning text-dark";
    
    showToast("Logged out successfully", "info");
}

function postLoginUi() {
    document.getElementById("loginPanel").classList.add("d-none");
    document.getElementById("senderWorkspace").classList.remove("d-none");
    document.getElementById("senderResponses").classList.remove("d-none");
    document.getElementById("profileButton").classList.remove("d-none");
    document.getElementById("senderUserLabel").innerText = senderSession.username;
    document.getElementById("senderProfileInfo").innerHTML = `
        <strong>User:</strong> ${senderSession.username}<br>
        <strong>Role:</strong> ${senderSession.role}`;
}

function prependSentAnswer(item) {
    const box = document.getElementById("sentAnswerList");
    
    // Remove empty state if present
    if (box.querySelector('.text-center')) {
        box.innerHTML = "";
    }
    
    const card = document.createElement("div");
    card.className = "answer-card animate-fade-up";
    card.innerHTML = `
        <div class="answer-header">
            <strong>Q:</strong> ${escapeHtml(item.question || "")}
        </div>
        <div class="answer-body">
            <strong>A:</strong> ${escapeHtml(item.answer || "")}
        </div>
        <div class="answer-meta">
            <span><i class="bi bi-person"></i> Source: ${item.source || "N/A"}</span>
            <span><i class="bi bi-clock"></i> ${formatTime(item.timestamp)}</span>
        </div>`;
    box.prepend(card);
    while (box.children.length > 8) box.removeChild(box.lastChild);
}

function clearHistory() {
    const box = document.getElementById("sentAnswerList");
    box.innerHTML = `
        <div class="text-center py-5 text-muted">
            <i class="bi bi-journal display-4 mb-3 d-block"></i>
            <p>No answers sent yet</p>
        </div>`;
    showToast("History cleared", "info");
}

function setQuestion(text) {
    document.getElementById("questionText").value = text;
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
