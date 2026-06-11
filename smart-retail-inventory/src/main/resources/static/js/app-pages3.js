/* ============================================================
   Smart Retail Inventory — 用户角色分配管理
   ============================================================ */

async function renderUserRole() {
  if (!requireAdmin()) return;

  document.getElementById('mainContent').innerHTML = ''
    + '<div class=\"topbar\"><h1 class=\"page-title\">用户角色分配</h1></div>'
    + '<div class=\"card\"><div class=\"card-header\"><span class=\"card-title\">用户角色列表</span></div><div class=\"table-container\" id=\"dataTable\"><div class=\"spinner\"></div></div></div>';

  async function loadAndRender() {
    try {
      const [usersRes, rolesRes] = await Promise.all([API.user.list(), API.role.list()]);
      const users = usersRes.data || [];
      const roles = rolesRes.data || [];
      const roleMap = {}; roles.forEach(r => { roleMap[r.id] = r.roleName; });

      const userRoleMap = {};
      await Promise.all(users.map(async (u) => {
        try {
          const rr = await API.userRole.roleIds(u.id);
          userRoleMap[u.id] = rr.data || [];
        } catch { userRoleMap[u.id] = []; }
      }));

      if (users.length === 0) {
        document.getElementById('dataTable').innerHTML = '<div class=\"empty-state\"><div class=\"empty-icon\">*</div><p>暂无用户</p></div>';
      } else {
        document.getElementById('dataTable').innerHTML = '<table><thead><tr><th>ID</th><th>用户名</th><th>昵称</th><th>状态</th><th>当前角色</th><th>操作</th></tr></thead><tbody>'
          + users.map(u => {
            const roleNames = (userRoleMap[u.id] || []).map(rid => roleMap[rid] || ('#'+rid)).join(', ') || '<span class=\"badge badge-gray\">无角色</span>';
            return '<tr><td>' + u.id + '</td><td>' + escapeHtml(u.username) + '</td><td>' + escapeHtml(u.nickname||'-') + '</td><td>' + (u.status===1?'<span class=\"badge badge-green\">启用</span>':'<span class=\"badge badge-red\">禁用</span>') + '</td><td>' + roleNames + '</td><td><button class=\"btn btn-outline btn-xs assignBtn\" data-uid=\"' + u.id + '\" data-uname=\"' + escapeHtml(u.username) + '\">分配角色</button></td></tr>';
          }).join('')
          + '</tbody></table>';
      }
      bindActions(users, roles, loadAndRender);
    } catch (e) {
      document.getElementById('dataTable').innerHTML = '<div class=\"alert alert-error\">加载失败: ' + escapeHtml(e.message) + '</div>';
    }
  }

  function bindActions(users, roles, reload) {
    document.querySelectorAll('.assignBtn').forEach(btn => {
      btn.onclick = async () => {
        const uid = parseInt(btn.dataset.uid);
        const uname = btn.dataset.uname;
        let currentRoleId = null;
        try { const rr = await API.userRole.roleIds(uid); currentRoleId = (rr.data||[])[0] || null; } catch {}

        const roleOpts = roles.map(r => '<option value=\"' + r.id + '\"' + (r.id === currentRoleId ? ' selected' : '') + '>' + escapeHtml(r.roleName) + (r.description ? ' (' + escapeHtml(r.description) + ')' : '') + '</option>').join('');

        const r = await showModal('分配角色 — ' + escapeHtml(uname),
          '<div class=\"form-group\"><label>选择角色 <span style=\"color:red\">*</span></label><select class=\"form-select\" id=\"fRoleId\"><option value=\"\">-- 请选择 --</option>' + roleOpts + '</select></div>'
          + '<div style=\"font-size:12px;color:var(--text-secondary);margin-bottom:16px;\">选择角色后将替换该用户当前所有角色</div>'
          + '<div class=\"form-actions\">'
          + '<button class=\"btn btn-outline\" onclick=\"closeModal(null)\">取消</button>'
          + (currentRoleId ? '<button class=\"btn btn-danger\" onclick=\"closeModal(\'clear\')\">清除角色</button>' : '')
          + '<button class=\"btn btn-primary\" id=\"submitForm\">保存</button>'
          + '</div>'
        );
        if (!r || r === null) return;
        if (r === 'clear') {
          try { await API.userRole.delete({ userId: uid, roleId: currentRoleId }); showToast('已清除角色'); reload(); } catch(e) { showToast(e.message,'error'); }
          return;
        }
        const roleId = parseInt(document.getElementById('fRoleId')?.value) || 0;
        if (!roleId) { showToast('请选择角色','error'); return; }
        try {
          if (currentRoleId) {
            await API.userRole.update({ userId: uid, roleId: roleId });
          } else {
            await API.userRole.add({ userId: uid, roleId: roleId });
          }
          showToast('分配成功'); reload();
        } catch(e) { showToast(e.message,'error'); }
      };
    });
  }

  loadAndRender();
}
