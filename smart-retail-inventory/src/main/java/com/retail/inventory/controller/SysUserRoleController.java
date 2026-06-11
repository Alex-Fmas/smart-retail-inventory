package com.retail.inventory.controller;

import com.retail.inventory.annotation.RequireRole;
import com.retail.inventory.common.Result;
import com.retail.inventory.dto.userRole.UserRoleAddDTO;
import com.retail.inventory.dto.userRole.UserRoleDeleteDTO;
import com.retail.inventory.dto.userRole.UserRoleUpdateDTO;
import com.retail.inventory.entity.SysRole;
import com.retail.inventory.entity.SysUserRole;
import com.retail.inventory.service.SysUserRoleService;
import com.retail.inventory.vo.userRole.UserRoleVO;
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
    @RequireRole("ADMIN")
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
    @GetMapping("/role-ids/{userId}")
    public Result<List<Long>> listRoleIdsByUserId(@NotNull @PathVariable Long userId) {
        List<Long> roleIds = sysUserRoleService.getRoleIdsByUserId(userId);
        return Result.success(roleIds);
    }

    /**
     * 根据用户ID查询拥有的角色列表
     */
    @GetMapping("/roles/{userId}")
    public Result<UserRoleVO> listRolesByUserId(@NotNull @PathVariable Long userId) {
        List<SysRole> rolesByUserId = sysUserRoleService.getRolesByUserId(userId);

        List<String> roleNames = rolesByUserId.stream()
                .map(SysRole::getRoleName)
                .collect(Collectors.toList());

        UserRoleVO roleVO = new UserRoleVO();
        roleVO.setRoleNames(roleNames);

        return Result.success(roleVO);
    }

    /**
     * 删除用户角色关系
     */
    @RequireRole("ADMIN")
    @DeleteMapping
    public Result<Integer> delete(@Valid @RequestBody UserRoleDeleteDTO dto) {
        int result = sysUserRoleService.deleteByUserId(dto.getUserId());
        return Result.success(result);
    }

    /**
     * 修改用户角色关系
     */
    @RequireRole("ADMIN")
    @PutMapping
    public Result<Integer> update(@Valid @RequestBody UserRoleUpdateDTO dto) {
        SysUserRole sysUserRole = new SysUserRole();
        BeanUtils.copyProperties(dto, sysUserRole);
        int result = sysUserRoleService.changeUserRole(sysUserRole.getUserId(), sysUserRole.getRoleId());
        return Result.success(result);
    }
}
