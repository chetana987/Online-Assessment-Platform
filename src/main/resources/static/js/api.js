// API Configuration
const API_BASE = 'http://localhost:8083/api/v1';

// Question APIs
async function getQuestions() {
    const response = await fetch(`${API_BASE}/questions`);
    return response.json();
}

async function getQuestion(id) {
    const response = await fetch(`${API_BASE}/questions/${id}`);
    return response.json();
}

async function createQuestion(data) {
    const response = await fetch(`${API_BASE}/questions`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    });
    return response.json();
}

// Submission APIs
async function createSubmission(data, userId, testSessionId) {
    let url = `${API_BASE}/submissions?userId=${userId}`;
    if (testSessionId) {
        url += `&testSessionId=${testSessionId}`;
    }
    const response = await fetch(url, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    });
    return response.json();
}

async function getSubmission(id) {
    const response = await fetch(`${API_BASE}/submissions/${id}`);
    return response.json();
}

async function getSubmissionsByUser(userId) {
    const response = await fetch(`${API_BASE}/submissions/user/${userId}`);
    return response.json();
}

// Helper functions
function showError(message) {
    alert(message);
}

function showSuccess(message) {
    alert(message);
}

function formatDate(timestamp) {
    if (!timestamp) return '-';
    return new Date(timestamp).toLocaleString();
}

// Test APIs
async function getTests() {
    const response = await fetch(`${API_BASE}/tests`);
    return response.json();
}

async function getTest(id) {
    const response = await fetch(`${API_BASE}/tests/${id}`);
    return response.json();
}

async function createTest(data) {
    console.log('API createTest:', JSON.stringify(data));
    const response = await fetch(`${API_BASE}/tests`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    });
    const result = await response.json();
    console.log('API result:', result);
    return result;
}

async function startTest(data) {
    const response = await fetch(`${API_BASE}/tests/start`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    });
    return response.json();
}

async function getTestSession(id) {
    const response = await fetch(`${API_BASE}/tests/session/${id}`);
    return response.json();
}

async function submitTestSession(id) {
    const response = await fetch(`${API_BASE}/tests/session/${id}/submit`, {
        method: 'POST'
    });
    return response.json();
}

// Admin APIs
async function getDashboard() {
    const response = await fetch(`${API_BASE}/admin/dashboard`);
    return response.json();
}

async function getAdminTests() {
    const response = await fetch(`${API_BASE}/admin/tests`);
    return response.json();
}

async function getTestResults(testId) {
    const response = await fetch(`${API_BASE}/admin/test/${testId}/results`);
    return response.json();
}

// Make functions globally available
window.api = {
    getQuestions,
    getQuestion,
    createQuestion,
    createSubmission,
    getSubmission,
    getSubmissionsByUser,
    getTests,
    getTest,
    createTest,
    startTest,
    getTestSession,
    submitTest: submitTestSession,
    submitTestSession,
    getDashboard,
    getAdminTests,
    getTestResults
};