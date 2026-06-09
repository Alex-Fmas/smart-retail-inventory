package com.retail.inventory;

import com.retail.inventory.entity.SysRole;
import com.retail.inventory.service.SysRoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class SysRoleServiceTest {
    @Autowired
    SysRoleService sysRoleService;
//    /**
//     * 获取角色
//     * @param id
//     * @return
//     */
//    SysRole getRoleById(Long id);
//
//    /**
//     * 添加角色
//     * @param role
//     * @return
//     */
//    int addRole(SysRole role);
//    /**
//     * 更新角色
//     * @param role
//     * @return
//     */
//    int updateRole(SysRole role);
//    /**
//     * 删除角色
//     * @param id
//     * @return
//     */
//    int deleteRole(Long id);
//    /**
//     * 获取所有角色
//     * @return
//     */
//    List<SysRole> listRoles();

    @Test
    void test02() {
        System.out.println("------------listRoles-------------");
        List<SysRole> listRoles = sysRoleService.listRoles();
        for (SysRole role : listRoles) {
            System.out.println(role);
        }
        System.out.println("------------deleteRole-------------");
        int i = sysRoleService.deleteRole(3L);
        System.out.println("返回值(i):" + i);
        System.out.println("------------listRoles-------------");
        listRoles = sysRoleService.listRoles();
        for (SysRole role : listRoles) {
            System.out.println(role);
        }
    }

    @Test
    void test01() {
        System.out.println("------------getRoleById-------------");
        SysRole role = sysRoleService.getRoleById(1L);
        System.out.println(role);
        System.out.println("------------addRole-------------");
        SysRole role1 = new SysRole();
        role1.setRoleName("testname");
        role1.setDescription("测试使用");
        Long id = sysRoleService.addRole(role1);
        System.out.println("返回值(id):" + id);
        System.out.println("------------updateRole-------------");
        role1.setId(id);
        role1.setRoleName("hahaha");
        int i = sysRoleService.updateRole(role1);
        System.out.println("返回值(i):" + i);
    }
}
