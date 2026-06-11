/* ============================================================
   Smart Retail Inventory — 库存流水 & 用户管理 & 角色管理
   ============================================================ */

// ======================= 库存流水 =======================
async function renderStockLog() {
  document.getElementById('mainContent').innerHTML = ''
    + '<div class=\"topbar\"><h1 class=\"page-title\">库存流水</h1><div class=\"topbar-actions\">'
    + (isAdmin() || isStaff() ? '<button class=\"btn btn-primary\" id=\"addBtn\">+ 新增变动</button>' : '')
    + '</div></div>'
    + '<div class=\"card\"><div class=\"table-container\" id=\"dataTable\"><div class=\"spinner\"></div></div></div>';

  const typeMap = {1:'销售', 2:'调拨', 3:'采购入库', 4:'盘点损益'};
  const typeBadge = {1:'badge-blue', 2:'badge-yellow', 3:'badge-green', 4:'badge-gray'};
  const areaMap = {0:'外部', 1:'货架', 2:'仓库'};

  async function loadAndRender() {
    try {
      const res = await API.stockLog.list();
      const data = res.data || [];
      if (data.length === 0) {
        document.getElementById('dataTable').innerHTML = '<div class=\"empty-state\"><div class=\"empty-icon\">*</div><p>暂无库存流水记录</p></div>';
      } else {
        document.getElementById('dataTable').innerHTML = '<table><thead><tr><th>ID</th><th>商品ID</th><th>类型</th><th>来源</th><th>目标</th><th>数量</th><th>操作人</th><th>时间</th><th>操作</th></tr></thead><tbody>'
          + data.map(l => '<tr><td>' + l.id + '</td><td>' + l.productId + '</td><td><span class=\"badge ' + (typeBadge[l.type]||'badge-gray') + '\">' + (typeMap[l.type]||l.type) + '</span></td><td>' + (areaMap[l.fromArea]||'-') + '</td><td>' + (areaMap[l.toArea]||'-') + '</td><td>' + l.quantity + '</td><td>' + (l.operatorId||'-') + '</td><td>' + (l.createTime||'-') + '</td><td>' + (isAdmin() ? '<button class=\"btn btn-danger btn-xs delBtn\" data-id=\"' + l.id + '\">删除</button>' : '-') + '</td></tr>').join('')
          + '</tbody></table>';
      }
      document.querySelectorAll('.delBtn').forEach(btn => {
        btn.onclick = async () => {
          if (!await showConfirm('确认删除','确定要删除该流水记录吗？')) return;
          try { await API.stockLog.delete(parseInt(btn.dataset.id)); showToast('删除成功'); loadAndRender(); } catch(e) { showToast(e.message,'error'); }
        };
      });
    } catch (e) {
      document.getElementById('dataTable').innerHTML = '<div class=\"alert alert-error\">加载失败: ' + escapeHtml(e.message) + '</div>';
    }
  }

  loadAndRender();
  const addBtn = document.getElementById('addBtn');
  if (addBtn) {
    addBtn.onclick = async () => {
      if (!requireAdmin()) return;
      const r = await showModal('新增库存变动',
        '<div class=\"form-group\"><label>商品ID <span style=\"color:red\">*</span></label><input class=\"form-input\" type=\"number\" id=\"fProductId\"></div>'
        + '<div class=\"form-group\"><label>变动类型 <span style=\"color:red\">*</span></label><select class=\"form-select\" id=\"fType\"><option value=\"1\">销售</option><option value=\"2\">调拨</option><option value=\"3\">采购入库</option><option value=\"4\">盘点损益</option></select></div>'
        + '<div class=\"form-group\"><label>来源区域 <span style=\"color:red\">*</span></label><select class=\"form-select\" id=\"fFromArea\"><option value=\"0\">外部</option><option value=\"1\">货架</option><option value=\"2\">仓库</option></select></div>'
        + '<div class=\"form-group\"><label>目标区域 <span style=\"color:red\">*</span></label><select class=\"form-select\" id=\"fToArea\"><option value=\"0\">外部</option><option value=\"1\">货架</option><option value=\"2\">仓库</option></select></div>'
        + '<div class=\"form-group\"><label>数量 <span style=\"color:red\">*</span></label><input class=\"form-input\" type=\"number\" id=\"fQuantity\" min=\"1\"></div>'
        + '<div class=\"form-group\"><label>操作人ID <span style=\"color:red\">*</span></label><input class=\"form-input\" type=\"number\" id=\"fOperatorId\"></div>'
        + '<div class=\"form-actions\"><button class=\"btn btn-outline\" onclick=\"closeModal(null)\">取消</button><button class=\"btn btn-primary\" id=\"submitForm\">保存</button></div>'
      );
      if (!r) return;
      const data = {
        productId: parseInt(document.getElementById('fProductId')?.value) || 0,
        fromArea: parseInt(document.getElementById('fFromArea')?.value) || 0,
        toArea: parseInt(document.getElementById('fToArea')?.value) || 0,
        quantity: parseInt(document.getElementById('fQuantity')?.value) || 0,
        type: parseInt(document.getElementById('fType')?.value) || 1,
        operatorId: parseInt(document.getElementById('fOperatorId')?.value) || 0,
        createTime: new Date().toISOString(),
      };
      if (!data.productId || !data.quantity || !data.operatorId) { showToast('请填写必填项','error'); return; }
      try { await API.stockChange.change(data); showToast('变动成功'); loadAndRender(); } catch(e) { showToast(e.message,'error'); }
    };
  }
}

// ======================= 用户管理 =======================
async function renderUser() {
  document.getElementById('mainContent').innerHTML = ''
    + '<div class=\"topbar\"><h1 class=\"page-title\">用户管理</h1><div class=\"topbar-actions\">'
    + '<button class=\"btn btn-outline\" id=\"chgPwdBtn\">修改密码</button>'
    + (isAdmin() ? '<button class=\"btn btn-primary\" id=\"addBtn\">+ 新增</button>' : '')
    + '</div></div>'
    + '<div class=\"card\"><div class=\"table-container\" id=\"dataTable\"><div class=\"spinner\"></div></div></div>';

  async function loadAndRender() {
    try {
      const res = await API.user.list();
      const data = res.data || [];
      if (data.length === 0) {
        document.getElementById('dataTable').innerHTML = '<div class=\"empty-state\"><div class=\"empty-icon\">*</div><p>暂无用户</p></div>';
      } else {
        document.getElementById('dataTable').innerHTML = '<table><thead><tr><th>ID</th><th>用户名</th><th>昵称</th><th>手机号</th><th>状态</th><th>创建时间</th>' + (isAdmin() ? '<th>操作</th>' : '') + '</tr></thead><tbody>'
          + data.map(u => '<tr><td>' + u.id + '</td><td>' + escapeHtml(u.username) + '</td><td>' + escapeHtml(u.nickname||'-') + '</td><td>' + escapeHtml(u.phone||'-') + '</td><td>' + (u.status===1?'<span class=\"badge badge-green\">启用</span>':'<span class=\"badge badge-red\">禁用</span>') + '</td><td>' + (u.createTime||'-') + '</td>' + (isAdmin() ? '<td><button class=\"btn btn-outline btn-xs mr-8 editBtn\" data-id=\"' + u.id + '\">编辑</button><button class=\"btn btn-warning btn-xs mr-8 resetBtn\" data-id=\"' + u.id + '\">重置</button><button class=\"btn btn-danger btn-xs mr-8 logicDelBtn\" data-id=\"' + u.id + '\">禁用</button><button class=\"btn btn-danger btn-xs delBtn\" data-id=\"' + u.id + '\">删除</button></td>' : '') + '</tr>').join('')
          + '</tbody></table>';
      }
      bindUserActions(loadAndRender);
    } catch (e) {
      document.getElementById('dataTable').innerHTML = '<div class=\"alert alert-error\">加载失败: ' + escapeHtml(e.message) + '</div>';
    }
  }

  function bindUserActions(reload) {
    document.querySelectorAll('.editBtn').forEach(btn => {
      btn.onclick = async () => {
        try { const r = await API.user.getById(parseInt(btn.dataset.id)); showUserForm('编辑用户', r.data||{}, reload); } catch(e) { showToast(e.message,'error'); }
      };
    });
    document.querySelectorAll('.resetBtn').forEach(btn => {
      btn.onclick = async () => {
        if (!await showConfirm('重置密码','确定要重置该用户的密码吗？')) return;
        try { await API.user.resetPassword({id: parseInt(btn.dataset.id)}); showToast('密码已重置'); } catch(e) { showToast(e.message,'error'); }
      };
    });
    document.querySelectorAll('.logicDelBtn').forEach(btn => {
      btn.onclick = async () => {
        if (!await showConfirm('禁用用户','确定要禁用该用户吗？（逻辑删除）')) return;
        try { await API.user.deleteLogic(parseInt(btn.dataset.id)); showToast('已禁用'); reload(); } catch(e) { showToast(e.message,'error'); }
      };
    });
    document.querySelectorAll('.delBtn').forEach(btn => {
      btn.onclick = async () => {
        if (!await showConfirm('物理删除','确定要永久删除该用户吗？此操作不可恢复！')) return;
        try { await API.user.delete(parseInt(btn.dataset.id)); showToast('已删除'); reload(); } catch(e) { showToast(e.message,'error'); }
      };
    });
  }

  async function showUserForm(title, item, onSaved) {
    const r = await showModal(title,
      '<div class=\"form-group\"><label>用户名 <span style=\"color:red\">*</span></label><input class=\"form-input\" id=\"fUsername\" value=\"' + escapeHtml(item.username||'') + '\"' + (item.id?' disabled':'') + '></div>'
      + (item.id ? '' : '<div class=\"form-group\"><label>密码 <span style=\"color:red\">*</span></label><input class=\"form-input\" type=\"password\" id=\"fPassword\"></div>')
      + '<div class=\"form-group\"><label>昵称 <span style=\"color:red\">*</span></label><input class=\"form-input\" id=\"fNickname\" value=\"' + escapeHtml(item.nickname||'') + '\"></div>'
      + '<div class=\"form-group\"><label>手机号</label><input class=\"form-input\" id=\"fPhone\" value=\"' + escapeHtml(item.phone||'') + '\"></div>'
      + (item.id ? '<div class=\"form-group\"><label>状态</label><select class=\"form-select\" id=\"fStatus\"><option value=\"1\"' + (item.status===1?' selected':'') + '>启用</option><option value=\"0\"' + (item.status===0?' selected':'') + '>禁用</option></select></div>' : '')
      + '<div class=\"form-actions\"><button class=\"btn btn-outline\" onclick=\"closeModal(null)\">取消</button><button class=\"btn btn-primary\" id=\"submitForm\">保存</button></div>'
    );
    if (!r) return;
    const data = {
      username: document.getElementById('fUsername')?.value?.trim(),
      nickname: document.getElementById('fNickname')?.value?.trim(),
      phone: document.getElementById('fPhone')?.value?.trim(),
    };
    if (item && item.id) {
      data.id = item.id;
      const stEl = document.getElementById('fStatus');
      if (stEl) data.status = parseInt(stEl.value) || 1;
    } else {
      data.password = document.getElementById('fPassword')?.value || '';
    }
    if (!data.username || !data.nickname || (!item.id && !data.password)) { showToast('请填写必填项','error'); return; }
    try {
      if (item && item.id) await API.user.update(data); else await API.user.add(data);
      showToast('保存成功'); onSaved();
    } catch(e) { showToast(e.message,'error'); }
  }

  async function showChangePwdForm() {
    const user = API.getUser();
    const r = await showModal('修改密码',
      '<div class=\"form-group\"><label>当前密码 <span style=\"color:red\">*</span></label><input class=\"form-input\" type=\"password\" id=\"fOldPwd\" placeholder=\"请输入当前密码\"></div>'
      + '<div class=\"form-group\"><label>新密码 <span style=\"color:red\">*</span></label><input class=\"form-input\" type=\"password\" id=\"fNewPwd\" placeholder=\"请输入新密码\"></div>'
      + '<div class=\"form-group\"><label>确认新密码 <span style=\"color:red\">*</span></label><input class=\"form-input\" type=\"password\" id=\"fNewPwd2\" placeholder=\"请再次输入新密码\"></div>'
      + '<div class=\"form-actions\"><button class=\"btn btn-outline\" onclick=\"closeModal(null)\">取消</button><button class=\"btn btn-primary\" id=\"submitForm\">保存</button></div>'
    );
    if (!r) return;
    const oldPwd = document.getElementById('fOldPwd')?.value || '';
    const newPwd = document.getElementById('fNewPwd')?.value || '';
    const newPwd2 = document.getElementById('fNewPwd2')?.value || '';
    if (!oldPwd || !newPwd) { showToast('请填写必填项','error'); return; }
    if (newPwd !== newPwd2) { showToast('两次密码不一致','error'); return; }
    try {
      await API.user.changePassword({ id: user.userId, password: oldPwd, newPassword: newPwd });
      showToast('密码修改成功，请重新登录');
      setTimeout(() => { API.clearToken(); localStorage.removeItem('user'); location.reload(); }, 1500);
    } catch(e) { showToast(e.message,'error'); }
  }

  loadAndRender();
  const addBtn = document.getElementById('addBtn');
  if (addBtn) addBtn.onclick = () => showUserForm('新增用户', {}, loadAndRender);
  document.getElementById('chgPwdBtn').onclick = showChangePwdForm;
}

// ======================= 角色管理 =======================
async function renderRole() {
  document.getElementById('mainContent').innerHTML = ''
    + '<div class=\"topbar\"><h1 class=\"page-title\">角色管理</h1><div class=\"topbar-actions\">'
    + (isAdmin() ? '<button class=\"btn btn-primary\" id=\"addBtn\">+ 新增</button>' : '')
    + '</div></div>'
    + '<div class=\"card\"><div class=\"table-container\" id=\"dataTable\"><div class=\"spinner\"></div></div></div>';

  async function loadAndRender() {
    try {
      const res = await API.role.list();
      const data = res.data || [];
      if (data.length === 0) {
        document.getElementById('dataTable').innerHTML = '<div class=\"empty-state\"><div class=\"empty-icon\">*</div><p>暂无角色</p></div>';
      } else {
        document.getElementById('dataTable').innerHTML = '<table><thead><tr><th>ID</th><th>角色名</th><th>描述</th>' + (isAdmin() ? '<th>操作</th>' : '') + '</tr></thead><tbody>'
          + data.map(r => '<tr><td>' + r.id + '</td><td><span class=\"badge badge-blue\">' + escapeHtml(r.roleName) + '</span></td><td>' + escapeHtml(r.description||'-') + '</td>' + (isAdmin() ? '<td><button class=\"btn btn-outline btn-xs mr-8 editBtn\" data-id=\"' + r.id + '\">编辑</button><button class=\"btn btn-danger btn-xs delBtn\" data-id=\"' + r.id + '\">删除</button></td>' : '') + '</tr>').join('')
          + '</tbody></table>';
      }
      bindRoleActions(loadAndRender);
    } catch (e) {
      document.getElementById('dataTable').innerHTML = '<div class=\"alert alert-error\">加载失败: ' + escapeHtml(e.message) + '</div>';
    }
  }

  function bindRoleActions(reload) {
    document.querySelectorAll('.editBtn').forEach(btn => {
      btn.onclick = async () => {
        try { const r = await API.role.getById(parseInt(btn.dataset.id)); showRoleForm('编辑角色', r.data||{}, reload); } catch(e) { showToast(e.message,'error'); }
      };
    });
    document.querySelectorAll('.delBtn').forEach(btn => {
      btn.onclick = async () => {
        if (!await showConfirm('确认删除','确定要删除该角色吗？')) return;
        try { await API.role.delete(parseInt(btn.dataset.id)); showToast('删除成功'); reload(); } catch(e) { showToast(e.message,'error'); }
      };
    });
  }

  async function showRoleForm(title, item, onSaved) {
    const r = await showModal(title,
      '<div class=\"form-group\"><label>角色名 <span style=\"color:red\">*</span></label><input class=\"form-input\" id=\"fRoleName\" value=\"' + escapeHtml(item.roleName||'') + '\"' + (item.id?' disabled':'') + '></div>'
      + '<div class=\"form-group\"><label>描述</label><input class=\"form-input\" id=\"fDescription\" value=\"' + escapeHtml(item.description||'') + '\"></div>'
      + '<div class=\"form-actions\"><button class=\"btn btn-outline\" onclick=\"closeModal(null)\">取消</button><button class=\"btn btn-primary\" id=\"submitForm\">保存</button></div>'
    );
    if (!r) return;
    const data = {
      roleName: document.getElementById('fRoleName')?.value?.trim(),
      description: document.getElementById('fDescription')?.value?.trim(),
    };
    if (item && item.id) data.id = item.id;
    if (!data.roleName) { showToast('角色名不能为空','error'); return; }
    try {
      if (item && item.id) await API.role.update(data); else await API.role.add(data);
      showToast('保存成功'); onSaved();
    } catch(e) { showToast(e.message,'error'); }
  }

  loadAndRender();
  const addBtn = document.getElementById('addBtn');
  if (addBtn) addBtn.onclick = () => showRoleForm('新增角色', {}, loadAndRender);
}

// --- 路由表 ---
const routes = {
  'dashboard': renderDashboard,
  'product': renderProduct,
  'inventory': renderInventory,
  'stock-log': renderStockLog,
  'user': renderUser,
  'role': renderRole,
};
