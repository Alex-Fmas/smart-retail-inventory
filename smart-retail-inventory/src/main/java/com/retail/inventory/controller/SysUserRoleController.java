package com.retail.inventory.controller;

import com.retail.inventory.common.Result;
import com.retail.inventory.dto.userRole.UserRoleAddDTO;
import com.retail.inventory.dto.userRole.UserRoleDeleteDTO;
import com.retail.inventory.entity.SysRole;
import com.retail.inventory.entity.SysUserRole;
import com.retail.inventory.service.SysUserRoleService;
import com.retail.inventory.vo.role.RoleVO;
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
@RequestMapping("/user-role")
public class SysUserRoleController {
    @Autowired
    SysUserRoleService sysUserRoleService;

    /**
     * 添加用户角色关系
     */
    @PostMapping
    public Result<Integer> add(@Valid @RequestBody UserRoleAddDTO dto) {

        SysUserRole sysUserRole = new SysUserRole();

        BeanUtils.copyProperties(dto, sysUserRole);
        int id = sysUserRoleService.insertUserRole(sysUserRole.getUserId(), sysUserRole.getRoleId());

        return Result.success(id);
    }

    /**
     * 根据用户ID查询拥有的角色ID列表
     */
    @GetMapping("/role-ids")
    public Result<List<Long>> listRoleIdsByUserId(@NotNull @RequestParam Long userId) {
        List<Long> roleIds = sysUserRoleService.getRoleIdsByUserId(userId);
        return Result.success(roleIds);
    }

    /**
     * 根据用户ID查询拥有的角色列表
     */
    @GetMapping("/roles")
    public Result<List<RoleVO>> listRolesByUserId(@NotNull @RequestParam Long userId) {
        List<SysRole> rolesByUserId = sysUserRoleService.getRolesByUserId(userId);

        List<RoleVO> voList = rolesByUserId.stream()
                .map(role -> {
                    RoleVO vo = new RoleVO();
                    BeanUtils.copyProperties(role, vo);
                    return vo;
                })
                .collect(Collectors.toList());

        return Result.success(voList);
    }

    /**
     * 删除用户角色关系
     */
    @DeleteMapping
    public Result<Integer> delete(@Valid @RequestBody UserRoleDeleteDTO dto) {
        int result = sysUserRoleService.deleteByUserId(dto.getUserId());
        return Result.success(result);
    }
}
