package com.retail.inventory.mapper;

import com.retail.inventory.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysUserMapper {
    SysUser getSysUserById(Long id);

    int addSysUser(SysUser sysUser);

    int updateSysUser(SysUser sysUser);

    int deleteSysUser(Long id);

    List<SysUser> ListUser();
}
