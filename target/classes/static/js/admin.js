let adminQuestions = [];

async function adminLogin() {
    const username = document.getElementById("adminUsername").value.trim();
    const password = document.getElementById("adminPassword").value;
    const loginBtn = document.querySelector('#adminLoginPanel button[type="submit"]');

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
    loginBtn.innerHTML = '<i class="bi bi-check-circle"></i> Login as Admin';

    if (!res.ok) {
        showToast("Admin login failed. Check credentials.", "danger");
        return;
    }
    
    // Show admin panels
    document.getElementById("adminLoginPanel").classList.add("d-none");
    document.getElementById("adminDashboardPanel").classList.remove("d-none");
    document.getElementById("adminStatus").classList.remove("d-none");
    document.getElementById("adminMenuBtn").classList.remove("d-none");
    
    showToast("Login successful", "success");
    refreshAdmin();
}

async function refreshAdmin() {
    const [users, sessions, logs, questions] = await Promise.all([
        fetch("/api/admin/users").then(r => r.json()),
        fetch("/api/admin/sessions").then(r => r.json()),
        fetch("/api/admin/logs").then(r => r.json()),
        fetch("/api/admin/questions").then(r => r.json())
    ]);
    adminQuestions = questions || [];
    document.getElementById("statUsers").innerText = users.length;
    document.getElementById("statReceivers").innerText = Object.keys(sessions.activeReceivers || {}).length;
    document.getElementById("statQuestions").innerText = adminQuestions.length;
    document.getElementById("statLogs").innerText = logs.length;

    renderUsers(users);
    renderQuestions();
    renderLogs(logs);
}

async function refreshAllData() {
    showToast("Refreshing data...", "info");
    await refreshAdmin();
    showToast("Data refreshed successfully", "success");
}

function renderUsers(users) {
    const html = `
        <table class="table table-striped table-hover">
            <thead>
                <tr>
                    <th>Username</th>
                    <th>Role</th>
                    <th>Status</th>
                    <th>Unique ID</th>
                </tr>
            </thead>
            <tbody>
                ${users.map(u => `
                    <tr>
                        <td><i class="bi bi-person me-2"></i>${safe(u.username)}</td>
                        <td><span class="badge bg-primary">${safe(u.role)}</span></td>
                        <td><span class="badge ${u.status === 'ONLINE' ? 'bg-success' : 'bg-secondary'}">${safe(u.status)}</span></td>
                        <td><code>${safe(u.uniqueId || "-")}</code></td>
                    </tr>
                `).join("")}
            </tbody>
        </table>`;
    document.getElementById("usersTable").innerHTML = html;
}

function renderQuestions() {
    const query = (document.getElementById("questionSearch")?.value || "").toLowerCase().trim();
    const filtered = adminQuestions.filter(q => {
        const corpus = `${q.keyword || ""} ${q.question || ""} ${q.category || ""}`.toLowerCase();
        return !query || corpus.includes(query);
    });
    const html = `
        <table class="table table-striped table-hover">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Keywords</th>
                    <th>Question</th>
                    <th>Category</th>
                </tr>
            </thead>
            <tbody>
                ${filtered.map(q => `
                    <tr style="cursor: pointer;" onclick="fillQuestion(${q.id})">
                        <td><code>${q.id}</code></td>
                        <td>${safe(q.keyword)}</td>
                        <td>${safe(q.question)}</td>
                        <td><span class="badge bg-info">${safe(q.category || "-")}</span></td>
                    </tr>
                `).join("")}
            </tbody>
        </table>`;
    document.getElementById("questionTable").innerHTML = html;
}

function renderLogs(logs) {
    const html = `
        <table class="table table-striped table-hover">
            <thead>
                <tr>
                    <th>Actor</th>
                    <th>Target</th>
                    <th>Action</th>
                    <th>Time</th>
                </tr>
            </thead>
            <tbody>
                ${logs.map(l => `
                    <tr>
                        <td><i class="bi bi-person me-2"></i>${safe(l.actor || "-")}</td>
                        <td><code>${safe(l.targetUniqueId || "-")}</code></td>
                        <td><span class="badge bg-warning text-dark">${safe(l.action || "-")}</span></td>
                        <td><small>${safe(l.timestamp || "-")}</small></td>
                    </tr>
                `).join("")}
            </tbody>
        </table>`;
    document.getElementById("logsTable").innerHTML = html;
}

function fillQuestion(id) {
    const q = adminQuestions.find(item => item.id === id);
    if (!q) return;
    document.getElementById("qId").value = q.id || "";
    document.getElementById("qKeyword").value = q.keyword || "";
    document.getElementById("qQuestion").value = q.question || "";
    document.getElementById("qAnswer").value = q.answer || "";
    document.getElementById("qCategory").value = q.category || "";
    showToast(`Question ${id} loaded for editing`, "info");
}

function getQuestionPayload() {
    return {
        keyword: document.getElementById("qKeyword").value.trim(),
        question: document.getElementById("qQuestion").value.trim(),
        answer: document.getElementById("qAnswer").value.trim(),
        category: document.getElementById("qCategory").value.trim()
    };
}

async function addQuestion() {
    await fetch("/api/admin/questions", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(getQuestionPayload())
    });
    showToast("Question added successfully", "success");
    refreshAdmin();
}

async function updateQuestion() {
    const id = document.getElementById("qId").value;
    if (!id) {
        showToast("Enter question ID for update", "warning");
        return;
    }
    await fetch(`/api/admin/questions/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(getQuestionPayload())
    });
    showToast("Question updated successfully", "success");
    refreshAdmin();
}

async function deleteQuestion() {
    const id = document.getElementById("qId").value;
    if (!id) {
        showToast("Enter question ID for delete", "warning");
        return;
    }
    await fetch(`/api/admin/questions/${id}`, { method: "DELETE" });
    showToast("Question deleted successfully", "success");
    refreshAdmin();
}

async function manualOverride() {
    const uniqueId = document.getElementById("overrideUniqueId").value.trim();
    const question = document.getElementById("overrideQuestion").value.trim();
    
    if (!uniqueId || !question) {
        showToast("Please fill in both receiver ID and message", "warning");
        return;
    }
    
    await fetch("/api/admin/override", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ uniqueId, question })
    });
    showToast("Override message sent successfully", "success");
}

async function clearLogs() {
    await fetch("/api/admin/logs", { method: "DELETE" });
    showToast("Logs cleared successfully", "success");
    refreshAdmin();
}

async function adminLogout() {
    await fetch("/api/auth/logout", { method: "POST" });
    
    // Hide admin panels
    document.getElementById("adminDashboardPanel").classList.add("d-none");
    document.getElementById("adminUsersPanel").classList.add("d-none");
    document.getElementById("adminQuestionPanel").classList.add("d-none");
    document.getElementById("adminOverridePanel").classList.add("d-none");
    document.getElementById("adminLogsPanel").classList.add("d-none");
    document.getElementById("adminStatus").classList.add("d-none");
    document.getElementById("adminMenuBtn").classList.add("d-none");
    
    // Show login panel
    document.getElementById("adminLoginPanel").classList.remove("d-none");
    
    showToast("Logged out successfully", "info");
}

function safe(value) {
    return String(value || "")
        .replaceAll("&", "&amp;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;")
        .replaceAll('"', "&quot;")
        .replaceAll("'", "&#39;");
}
