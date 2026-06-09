package com.retail.inventory;

import com.retail.inventory.entity.SysRole;
import com.retail.inventory.service.SysUserRoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class SysUserRoleServiceTest {
    @Autowired
    SysUserRoleService sysUserRoleService;
//    /**
//     * 添加用户角色关系
//     * @param userId
//     * @param roleId
//     * @return
//     */
//    int insertUserRole(Long userId, Long roleId);
//
//    /**
//     * 删除用户角色关系
//     * @param userId
//     * @return
//     */
//    int deleteByUserId(Long userId);
//
//    /**
//     * 根据用户ID查询拥有的角色ID列表
//     * @param userId
//     * @return
//     */
//    List<Long> getRoleIdsByUserId(Long userId);
//
//    /**
//     * 根据用户ID查询角色列表
//     * @param userId
//     * @return
//     */
//    List<SysRole> getRolesByUserId(Long userId);

    @Test
    void test01() {
        System.out.println("-------------insertUserRole---------------");
        int i = sysUserRoleService.insertUserRole(4L, 2L);
        System.out.println(i);
        System.out.println("-------------deleteByUserId---------------");
        int i1 = sysUserRoleService.deleteByUserId(4L);
        System.out.println(i1);
        System.out.println("-------------getRoleIdsByUserId---------------");
        List<Long> roleIds = sysUserRoleService.getRoleIdsByUserId(1L);
        System.out.println(roleIds);
        System.out.println("-------------getRolesByUserId---------------");
        List<SysRole> roles = sysUserRoleService.getRolesByUserId(1L);
        System.out.println(roles);


    }
}
