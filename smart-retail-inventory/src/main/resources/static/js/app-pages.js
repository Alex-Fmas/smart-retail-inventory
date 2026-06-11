/* ============================================================
   Smart Retail Inventory — 仪表盘 & 商品管理 & 库存管理
   ============================================================ */

// ======================= 仪表盘 =======================
async function renderDashboard() {
  document.getElementById('mainContent').innerHTML = ''
    + '<div class=\"topbar\"><h1 class=\"page-title\">仪表盘</h1></div>'
    + '<div class=\"stats-grid\">'
    + '<div class=\"stat-card\"><div class=\"stat-icon blue\">P</div><div class=\"stat-info\"><div class=\"stat-value\" id=\"statProduct\">-</div><div class=\"stat-label\">商品总数</div></div></div>'
    + '<div class=\"stat-card\"><div class=\"stat-icon green\">I</div><div class=\"stat-info\"><div class=\"stat-value\" id=\"statInventory\">-</div><div class=\"stat-label\">库存记录</div></div></div>'
    + '<div class=\"stat-card\"><div class=\"stat-icon yellow\">L</div><div class=\"stat-info\"><div class=\"stat-value\" id=\"statStockLog\">-</div><div class=\"stat-label\">库存流水</div></div></div>'
    + '<div class=\"stat-card\"><div class=\"stat-icon purple\">U</div><div class=\"stat-info\"><div class=\"stat-value\" id=\"statUser\">-</div><div class=\"stat-label\">系统用户</div></div></div>'
    + '</div>'
    + '<div class=\"card\"><div class=\"card-header\"><span class=\"card-title\">最近库存流水</span></div><div class=\"table-container\" id=\"recentLogs\"><div class=\"spinner\"></div></div></div>';

  try {
    const [products, inventories, stockLogs, users] = await Promise.all([
      API.product.list(), API.inventory.list(), API.stockLog.list(), API.user.list()
    ]);
    document.getElementById('statProduct').textContent = products.data.length;
    document.getElementById('statInventory').textContent = inventories.data.length;
    document.getElementById('statStockLog').textContent = stockLogs.data.length;
    document.getElementById('statUser').textContent = users.data.length;

    const logs = (stockLogs.data || []).slice(-5).reverse();
    if (logs.length === 0) {
      document.getElementById('recentLogs').innerHTML = '<div class=\"empty-state\"><div class=\"empty-icon\">*</div><p>暂无库存流水记录</p></div>';
    } else {
      const typeMap = {1:'销售',2:'调拨',3:'采购入库',4:'盘点损益'};
      const areaMap = {0:'外部',1:'货架',2:'仓库'};
      document.getElementById('recentLogs').innerHTML = '<table><thead><tr><th>ID</th><th>商品ID</th><th>类型</th><th>来源</th><th>目标</th><th>数量</th><th>时间</th></tr></thead><tbody>'
        + logs.map(l => '<tr><td>' + l.id + '</td><td>' + l.productId + '</td><td><span class=\"badge badge-blue\">' + (typeMap[l.type]||l.type) + '</span></td><td>' + (areaMap[l.fromArea]||'-') + '</td><td>' + (areaMap[l.toArea]||'-') + '</td><td>' + l.quantity + '</td><td>' + (l.createTime||'-') + '</td></tr>').join('')
        + '</tbody></table>';
    }
  } catch (e) {
    document.getElementById('mainContent').innerHTML = '<div class=\"alert alert-error\">加载失败: ' + escapeHtml(e.message) + '</div>';
  }
}

// ======================= 商品管理 =======================
async function renderProduct() {
  document.getElementById('mainContent').innerHTML = ''
    + '<div class=\"topbar\"><h1 class=\"page-title\">商品管理</h1><div class=\"topbar-actions\"><button class=\"btn btn-primary\" id=\"addBtn\">+ 新增</button></div></div>'
    + '<div class=\"card\"><div class=\"table-container\" id=\"dataTable\"><div class=\"spinner\"></div></div></div>';

  async function loadAndRender() {
    try {
      const res = await API.product.list();
      const data = res.data || [];
      if (data.length === 0) {
        document.getElementById('dataTable').innerHTML = '<div class=\"empty-state\"><div class=\"empty-icon\">*</div><p>暂无商品，请新增</p></div>';
      } else {
        document.getElementById('dataTable').innerHTML = '<table><thead><tr><th>ID</th><th>条码</th><th>名称</th><th>售价</th><th>单位</th><th>操作</th></tr></thead><tbody>'
          + data.map(p => '<tr><td>' + p.id + '</td><td>' + escapeHtml(p.barcode) + '</td><td>' + escapeHtml(p.name) + '</td><td>' + (p.price||0) + '</td><td>' + escapeHtml(p.unit||'-') + '</td><td><button class=\"btn btn-outline btn-xs mr-8 editBtn\" data-id=\"' + p.id + '\">编辑</button><button class=\"btn btn-danger btn-xs delBtn\" data-id=\"' + p.id + '\">删除</button></td></tr>').join('')
          + '</tbody></table>';
      }
      bindProductActions(loadAndRender);
    } catch (e) {
      document.getElementById('dataTable').innerHTML = '<div class=\"alert alert-error\">加载失败: ' + escapeHtml(e.message) + '</div>';
    }
  }

  function bindProductActions(reload) {
    document.querySelectorAll('.editBtn').forEach(btn => {
      btn.onclick = async () => {
        try { const r = await API.product.getById(parseInt(btn.dataset.id)); showProductForm('编辑商品', r.data || {}, reload); } catch(e) { showToast(e.message,'error'); }
      };
    });
    document.querySelectorAll('.delBtn').forEach(btn => {
      btn.onclick = async () => {
        if (!await showConfirm('确认删除','确定要删除该商品吗？')) return;
        try { await API.product.delete(parseInt(btn.dataset.id)); showToast('删除成功'); reload(); } catch(e) { showToast(e.message,'error'); }
      };
    });
  }

  async function showProductForm(title, item, onSaved) {
    const r = await showModal(title,
      '<div class=\"form-group\"><label>商品条码 <span style=\"color:red\">*</span></label><input class=\"form-input\" id=\"fBarcode\" value=\"' + escapeHtml(item.barcode||'') + '\"></div>'
      + '<div class=\"form-group\"><label>商品名称 <span style=\"color:red\">*</span></label><input class=\"form-input\" id=\"fName\" value=\"' + escapeHtml(item.name||'') + '\"></div>'
      + '<div class=\"form-group\"><label>售价 <span style=\"color:red\">*</span></label><input class=\"form-input\" type=\"number\" step=\"0.01\" id=\"fPrice\" value=\"' + escapeHtml(item.price||'') + '\"></div>'
      + '<div class=\"form-group\"><label>单位</label><input class=\"form-input\" id=\"fUnit\" value=\"' + escapeHtml(item.unit||'') + '\"></div>'
      + '<div class=\"form-actions\"><button class=\"btn btn-outline\" onclick=\"closeModal(null)\">取消</button><button class=\"btn btn-primary\" id=\"submitForm\">保存</button></div>'
    );
    if (!r) return;
    const data = {
      barcode: document.getElementById('fBarcode')?.value?.trim(),
      name: document.getElementById('fName')?.value?.trim(),
      price: parseFloat(document.getElementById('fPrice')?.value) || 0,
      unit: document.getElementById('fUnit')?.value?.trim(),
    };
    if (item && item.id) data.id = item.id;
    if (!data.barcode || !data.name) { showToast('条码和名称不能为空','error'); return; }
    try {
      if (item && item.id) await API.product.update(data); else await API.product.add(data);
      showToast('保存成功'); onSaved();
    } catch(e) { showToast(e.message,'error'); }
  }

  loadAndRender();
  document.getElementById('addBtn').onclick = () => showProductForm('新增商品', {}, loadAndRender);
}

// ======================= 库存管理 =======================
async function renderInventory() {
  document.getElementById('mainContent').innerHTML = ''
    + '<div class=\"topbar\"><h1 class=\"page-title\">库存管理</h1><div class=\"topbar-actions\"><button class=\"btn btn-primary\" id=\"addBtn\">+ 新增</button></div></div>'
    + '<div class=\"card\"><div class=\"table-container\" id=\"dataTable\"><div class=\"spinner\"></div></div></div>';

  async function loadAndRender() {
    try {
      const [invRes, prodRes] = await Promise.all([API.inventory.list(), API.product.list()]);
      const inventories = invRes.data || [];
      const products = prodRes.data || [];
      const prodMap = {}; products.forEach(p => { prodMap[p.id] = p.name; });
      if (inventories.length === 0) {
        document.getElementById('dataTable').innerHTML = '<div class=\"empty-state\"><div class=\"empty-icon\">*</div><p>暂无库存记录</p></div>';
      } else {
        const areaMap = {1:'货架', 2:'仓库'};
        document.getElementById('dataTable').innerHTML = '<table><thead><tr><th>ID</th><th>商品</th><th>区域</th><th>数量</th><th>预警阈值</th><th>状态</th><th>操作</th></tr></thead><tbody>'
          + inventories.map(inv => {
            const low = inv.quantity <= inv.minThreshold;
            return '<tr><td>' + inv.id + '</td><td>' + escapeHtml(prodMap[inv.productId]||('#'+inv.productId)) + '</td><td>' + (areaMap[inv.areaType]||inv.areaType) + '</td><td>' + inv.quantity + '</td><td>' + (inv.minThreshold||0) + '</td><td>' + (low ? '<span class=\"badge badge-red\">库存不足</span>' : '<span class=\"badge badge-green\">正常</span>') + '</td><td><button class=\"btn btn-outline btn-xs mr-8 editBtn\" data-id=\"' + inv.id + '\">编辑</button><button class=\"btn btn-danger btn-xs delBtn\" data-id=\"' + inv.id + '\">删除</button></td></tr>';
          }).join('') + '</tbody></table>';
      }
      bindInvActions(loadAndRender, products);
    } catch (e) {
      document.getElementById('dataTable').innerHTML = '<div class=\"alert alert-error\">加载失败: ' + escapeHtml(e.message) + '</div>';
    }
  }

  function bindInvActions(reload, products) {
    document.querySelectorAll('.editBtn').forEach(btn => {
      btn.onclick = async () => {
        try { const r = await API.inventory.getById(parseInt(btn.dataset.id)); showInvForm('编辑库存', r.data||{}, reload, products); } catch(e) { showToast(e.message,'error'); }
      };
    });
    document.querySelectorAll('.delBtn').forEach(btn => {
      btn.onclick = async () => {
        if (!await showConfirm('确认删除','确定要删除该库存记录吗？')) return;
        try { await API.inventory.delete(parseInt(btn.dataset.id)); showToast('删除成功'); reload(); } catch(e) { showToast(e.message,'error'); }
      };
    });
  }

  async function showInvForm(title, item, onSaved, products) {
    const prodOpts = products.map(p => '<option value=\"' + p.id + '\"' + (item.productId === p.id ? ' selected' : '') + '>' + escapeHtml(p.name) + '</option>').join('');
    const r = await showModal(title,
      '<div class=\"form-group\"><label>商品 <span style=\"color:red\">*</span></label><select class=\"form-select\" id=\"fProductId\">' + prodOpts + '</select></div>'
      + '<div class=\"form-group\"><label>区域</label><select class=\"form-select\" id=\"fAreaType\"><option value=\"1\"' + (item.areaType===1?' selected':'') + '>货架</option><option value=\"2\"' + (item.areaType===2?' selected':'') + '>仓库</option></select></div>'
      + '<div class=\"form-group\"><label>数量 <span style=\"color:red\">*</span></label><input class=\"form-input\" type=\"number\" id=\"fQuantity\" value=\"' + escapeHtml(item.quantity||0) + '\"></div>'
      + '<div class=\"form-group\"><label>预警阈值</label><input class=\"form-input\" type=\"number\" id=\"fMinThreshold\" value=\"' + escapeHtml(item.minThreshold||0) + '\"></div>'
      + '<div class=\"form-actions\"><button class=\"btn btn-outline\" onclick=\"closeModal(null)\">取消</button><button class=\"btn btn-primary\" id=\"submitForm\">保存</button></div>'
    );
    if (!r) return;
    const data = {
      productId: parseInt(document.getElementById('fProductId')?.value) || 0,
      areaType: parseInt(document.getElementById('fAreaType')?.value) || 1,
      quantity: parseInt(document.getElementById('fQuantity')?.value) || 0,
      minThreshold: parseInt(document.getElementById('fMinThreshold')?.value) || 0,
    };
    if (item && item.id) data.id = item.id;
    try {
      if (item && item.id) await API.inventory.update(data); else await API.inventory.add(data);
      showToast('保存成功'); onSaved();
    } catch(e) { showToast(e.message,'error'); }
  }

  loadAndRender();
  document.getElementById('addBtn').onclick = async () => {
    try { const pr = await API.product.list(); showInvForm('新增库存', {}, loadAndRender, pr.data||[]); } catch(e) { showToast(e.message,'error'); }
  };
}
