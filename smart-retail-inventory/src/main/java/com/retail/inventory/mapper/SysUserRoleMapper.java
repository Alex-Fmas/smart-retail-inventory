package com.retail.inventory.mapper;

import com.retail.inventory.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysUserRoleMapper {
    // 给用户分配角色
    int insertUserRole(Long userId, Long roleId);

    // 删除用户的所有角色（修改角色时用）
    int deleteByUserId(Long userId);

    // 根据用户ID查询拥有的角色ID列表
    List<Long> getRoleIdsByUserId(Long userId);

    // 根据用户ID查询角色列表
    List<SysRole> getRolesByUserId(Long userId);

}
