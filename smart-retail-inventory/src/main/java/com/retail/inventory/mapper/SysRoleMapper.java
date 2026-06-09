package com.retail.inventory.mapper;


import com.retail.inventory.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMapper{
    SysRole getRoleById(Long id);

    int addRole(SysRole role);

    int updateRole(SysRole role);

    int deleteRole(Long id);

    List<SysRole> listRoles();
}
