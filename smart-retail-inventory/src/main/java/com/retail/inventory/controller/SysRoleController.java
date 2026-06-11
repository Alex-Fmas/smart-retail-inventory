package com.retail.inventory.controller;

import com.retail.inventory.common.Result;
import com.retail.inventory.dto.role.RoleAddDTO;
import com.retail.inventory.dto.role.RoleUpdateDTO;
import com.retail.inventory.exception.BizException;
import com.retail.inventory.exception.BizExceptionEnum;
import com.retail.inventory.service.SysRoleService;
import com.retail.inventory.vo.role.RoleVO;
import com.retail.inventory.entity.SysRole;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("/role")
public class SysRoleController {
    @Autowired
    SysRoleService sysRoleService;

    /**
     * 根据ID查询角色
     */
    @GetMapping("/{id}")
    public Result<RoleVO> getById(@NotNull @PathVariable Long id) {

        SysRole role = sysRoleService.getRoleById(id);
        if(role == null){
            throw new BizException(BizExceptionEnum.ROLE_NOT_EXIST);
        }

        RoleVO vo = new RoleVO();
        BeanUtils.copyProperties(role, vo);

        return Result.success(vo);
    }

    /**
     * 查询全部角色
     */
    @GetMapping("/list")
    public Result<List<RoleVO>> list() {

        List<SysRole> roles = sysRoleService.listRoles();

        List<RoleVO> voList = roles.stream()
                .map(role -> {
                    RoleVO vo = new RoleVO();
                    BeanUtils.copyProperties(role, vo);
                    return vo;
                })
                .collect(Collectors.toList());

        return Result.success(voList);
    }

    /**
     * 新增角色
     */
    @PostMapping
    public Result<Long> add(@Valid @RequestBody RoleAddDTO dto) {

        SysRole role = new SysRole();

        BeanUtils.copyProperties(dto, role);

        Long id = sysRoleService.addRole(role);

        return Result.success(id);
    }

    /**
     * 修改角色
     */
    @PutMapping
    public Result<Integer> update(@Valid @RequestBody RoleUpdateDTO dto) {

        SysRole role = new SysRole();

        BeanUtils.copyProperties(dto, role);

        int result = sysRoleService.updateRole(role);

        return Result.success(result);
    }

    /**
     * 删除角色
     */
    @DeleteMapping("/{id}")
    public Result<Integer> delete(@NotNull @PathVariable Long id) {

        int result = sysRoleService.deleteRole(id);

        return Result.success(result);
    }
}
