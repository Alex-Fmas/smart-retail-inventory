/* ============================================================
   Smart Retail Inventory — API 服务层
   ============================================================ */

const API = (() => {
  const BASE = '';

  const getToken = () => localStorage.getItem('token');
  const setToken = (t) => localStorage.setItem('token', t);
  const clearToken = () => localStorage.removeItem('token');
  const getUser = () => {
    try { return JSON.parse(localStorage.getItem('user')); } catch { return null; }
  };
  const setUser = (u) => localStorage.setItem('user', JSON.stringify(u));

  async function request(method, url, body = null) {
    const headers = { 'Content-Type': 'application/json' };
    const token = getToken();
    if (token) headers['Authorization'] = token;
    const opts = { method, headers };
    if (body && method !== 'GET') opts.body = JSON.stringify(body);
    const res = await fetch(BASE + url, opts);
    const json = await res.json();
    if (json.code === 200 || json.code === 0) return json;
    throw new Error(json.message || 'Request failed');
  }

  const get = (url) => request('GET', url);
  const post = (url, body) => request('POST', url, body);
  const put = (url, body) => request('PUT', url, body);
  const del = (url, body) => request('DELETE', url, body);

  const auth = {
    login: (username, password) => post('/auth/login', { username, password }),
  };

  const product = {
    list: () => get('/product/list'),
    getById: (id) => get('/product/' + id),
    add: (d) => post('/product', d),
    update: (d) => put('/product', d),
    delete: (id) => del('/product/' + id),
  };

  const inventory = {
    list: () => get('/inventory/list'),
    getById: (id) => get('/inventory/' + id),
    add: (d) => post('/inventory', d),
    update: (d) => put('/inventory', d),
    delete: (id) => del('/inventory/' + id),
  };

  // 库存变动（带事务）
  const stockChange = {
    change: (d) => post('/stock/change', d),
  };

  const stockLog = {
    list: () => get('/stock-log/list'),
    getById: (id) => get('/stock-log/' + id),
    add: (d) => post('/stock-log', d),
    delete: (id) => del('/stock-log/' + id),
  };

  const user = {
    list: () => get('/user/list'),
    getById: (id) => get('/user/' + id),
    add: (d) => post('/user', d),
    update: (d) => put('/user', d),
    delete: (id) => del('/user/' + id),
    deleteLogic: (id) => del('/user/logic/' + id),
    changePassword: (d) => put('/user/change-password', d),
    resetPassword: (d) => put('/user/reset-password', d),
  };

  const role = {
    list: () => get('/role/list'),
    getById: (id) => get('/role/' + id),
    add: (d) => post('/role', d),
    update: (d) => put('/role', d),
    delete: (id) => del('/role/' + id),
  };

  const userRole = {
    add: (d) => post('/user-role', d),
    roleIds: (userId) => get('/user-role/role-ids/' + userId),
    roles: (userId) => get('/user-role/roles/' + userId),
    delete: (d) => del('/user-role', d),
    update: (d) => put('/user-role', d),
  };

  return { getToken, setToken, clearToken, getUser, setUser, auth, product, inventory, stockChange, stockLog, user, role, userRole };
})();
