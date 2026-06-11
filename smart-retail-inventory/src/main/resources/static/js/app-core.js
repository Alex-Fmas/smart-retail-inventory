/* ============================================================
   Smart Retail Inventory — SPA 核心（工具函数 & 路由 & 登录）
   ============================================================ */

const $ = (sel) => document.querySelector(sel);
const $$ = (sel) => document.querySelectorAll(sel);

function escapeHtml(str) {
  if (str == null) return '';
  return String(str).replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/\"/g,'&quot;');
}

function showToast(msg, type = 'success') {
  const container = document.getElementById('toastContainer');
  const toast = document.createElement('div');
  toast.className = 'toast toast-' + type;
  toast.textContent = msg;
  container.appendChild(toast);
  setTimeout(() => toast.remove(), 3000);
}

function showConfirm(title, body) {
  return new Promise((resolve) => {
    const overlay = document.createElement('div');
    overlay.className = 'confirm-overlay';
    overlay.innerHTML = '<div class=\"confirm-dialog\"><div class=\"confirm-title\">' + escapeHtml(title) + '</div><div class=\"confirm-body\">' + escapeHtml(body) + '</div><div class=\"confirm-actions\"><button class=\"btn btn-outline btn-cancel\">取消</button><button class=\"btn btn-danger btn-ok\">确认</button></div></div>';
    document.body.appendChild(overlay);
    overlay.querySelector('.btn-cancel').onclick = () => { overlay.remove(); resolve(false); };
    overlay.querySelector('.btn-ok').onclick = () => { overlay.remove(); resolve(true); };
    overlay.onclick = (e) => { if (e.target === overlay) { overlay.remove(); resolve(false); } };
  });
}

let _modalResolve = null;
let _modalOverlay = null;

function showModal(title, contentHtml) {
  return new Promise((resolve) => {
    _modalResolve = resolve;
    const overlay = document.createElement('div');
    overlay.className = 'modal-overlay';
    _modalOverlay = overlay;
    overlay.innerHTML = '<div class=\"modal\"><div class=\"modal-title\">' + escapeHtml(title) + '</div><div id=\"modalBody\">' + contentHtml + '</div></div>';
    document.body.appendChild(overlay);
    const submitBtn = overlay.querySelector('#submitForm');
    if (submitBtn) submitBtn.onclick = () => { submitBtn.disabled = true; closeModal('submit'); };
    overlay.onclick = (e) => { if (e.target === overlay) closeModal(null); };
  });
}

function closeModal(result) {
  _modalResolve?.(result);
  _modalResolve = null;
  setTimeout(() => {
    if (_modalOverlay) { _modalOverlay.remove(); _modalOverlay = null; }
  }, 0);
}

function navigate(page) { window.location.hash = page; }
function getCurrentPage() { return window.location.hash.slice(1) || 'dashboard'; }

function isAdmin() {
  const user = API.getUser();
  return user && user.roles && user.roles.includes('ADMIN');
}

function isStaff() {
  const user = API.getUser();
  return user && user.roles && user.roles.includes('STAFF');
}

function requireAdmin() {
  if (!isAdmin()) { showToast('此功能需要管理员权限', 'error'); navigate('dashboard'); return false; }
  return true;
}

// --- 初始化 ---
document.addEventListener('DOMContentLoaded', () => {
  if (!API.getToken()) { renderLogin(); return; }
  renderApp();
  window.addEventListener('hashchange', renderPage);
  renderPage();
});

// --- 登录页 ---
function renderLogin() {
  document.body.innerHTML = ''
    + '<div class=\"login-page\">'
    + '  <div class=\"login-card\">'
    + '    <div class=\"login-logo\"><h1>智慧零售库存管理</h1><p>Smart Retail Inventory</p></div>'
    + '    <div id=\"loginAlert\"></div>'
    + '    <div class=\"form-group\"><label>用户名</label><input class=\"form-input\" id=\"loginUsername\" placeholder=\"请输入用户名\" autocomplete=\"username\"></div>'
    + '    <div class=\"form-group\"><label>密码</label><input class=\"form-input\" type=\"password\" id=\"loginPassword\" placeholder=\"请输入密码\" autocomplete=\"current-password\"></div>'
    + '    <button class=\"btn btn-primary\" id=\"loginBtn\">登 录</button>'
    + '  </div></div>';

  const doLogin = async () => {
    const username = document.getElementById('loginUsername').value.trim();
    const password = document.getElementById('loginPassword').value;
    const alertEl = document.getElementById('loginAlert');
    const btn = document.getElementById('loginBtn');
    if (!username || !password) { alertEl.innerHTML = '<div class=\"alert alert-error\">请输入用户名和密码</div>'; return; }
    btn.disabled = true; btn.textContent = '登录中...'; alertEl.innerHTML = '';
    try {
      const res = await API.auth.login(username, password);
      API.setToken(res.data.token);
      API.setUser({ userId: res.data.userId, username: res.data.username, roles: res.data.roles });
      location.reload();
    } catch (e) { alertEl.innerHTML = '<div class=\"alert alert-error\">' + escapeHtml(e.message) + '</div>'; }
    finally { btn.disabled = false; btn.textContent = '登 录'; }
  };

  document.getElementById('loginBtn').onclick = doLogin;
  document.getElementById('loginPassword').onkeydown = (e) => { if (e.key === 'Enter') doLogin(); };
}

// --- 主布局 ---
function renderApp() {
  const user = API.getUser();
  const userInitial = (user && user.username) ? user.username.charAt(0).toUpperCase() : 'U';
  document.body.innerHTML = ''
    + '<div class=\"app-layout\">'
    + '  <aside class=\"sidebar\" id=\"sidebar\">'
    + '    <div class=\"sidebar-header\"><div class=\"logo\"><span class=\"logo-icon\"></span> 库存管理</div><div class=\"subtitle\">Smart Retail Inventory</div></div>'
    + '    <nav class=\"sidebar-nav\" id=\"sidebarNav\">'
    + '      <div class=\"nav-section\"><div class=\"nav-section-title\">主菜单</div></div>'
    + '      <div class=\"nav-item\" data-page=\"dashboard\"><span class=\"nav-icon\"></span> 仪表盘</div>'
    + '      <div class=\"nav-item\" data-page=\"product\"><span class=\"nav-icon\"></span> 商品管理</div>'
    + '      <div class=\"nav-item\" data-page=\"inventory\"><span class=\"nav-icon\"></span> 库存管理</div>'
    + '      <div class=\"nav-item\" data-page=\"stock-log\"><span class=\"nav-icon\"></span> 库存流水</div>'
    + '      <div class=\"nav-section\"><div class=\"nav-section-title\">系统管理</div></div>'
    + '      <div class=\"nav-item\" data-page=\"user\"><span class=\"nav-icon\"></span> 用户管理</div>'
    + '      <div class=\"nav-item\" data-page=\"role\"><span class=\"nav-icon\"></span> 角色管理</div>'
    + '      <div class=\"nav-item\" data-page=\"user-role\"><span class=\"nav-icon\"></span> 用户角色</div>'
    + '    </nav>'
    + '    <div class=\"sidebar-footer\"><div class=\"user-info\"><div class=\"user-avatar\">' + userInitial + '</div><div><div style=\"color:#fff;font-size:14px\">' + escapeHtml(user.username) + '</div><div style=\"font-size:11px\">' + (isAdmin() ? '管理员' : '员工') + '</div></div></div><button class=\"btn btn-outline btn-sm w-full mt-16\" style=\"color:#cbd5e1;border-color:rgba(255,255,255,.15);margin-top:10px\" id=\"logoutBtn\">退出登录</button></div>'
    + '  </aside>'
    + '  <main class=\"main-content\" id=\"mainContent\"></main>'
    + '</div>'
    + '<div class=\"toast-container\" id=\"toastContainer\"></div>';

  document.querySelectorAll('.nav-item[data-page]').forEach(el => { el.onclick = () => navigate(el.dataset.page); });
  document.getElementById('logoutBtn').onclick = () => { API.clearToken(); localStorage.removeItem('user'); location.reload(); };
}

function renderPage() {
  const page = getCurrentPage();
  document.querySelectorAll('.nav-item').forEach(el => el.classList.toggle('active', el.dataset.page === page));
  const fn = routes[page];
  if (fn) { document.getElementById('mainContent').innerHTML = '<div class=\"spinner\"></div>'; fn(); }
  else { document.getElementById('mainContent').innerHTML = '<div class=\"empty-state\"><div class=\"empty-icon\">?</div><p>页面不存在</p></div>'; }
}
