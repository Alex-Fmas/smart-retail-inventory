package com.retail.inventory;

import com.retail.inventory.entity.SysUser;
import com.retail.inventory.service.SysUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class SysUserServiceTest {
    @Autowired
    SysUserService sysUserService;

//    /**
//     * 根据id查询用户
//     * @param id
//     * @return
//     */
//    SysUser getSysUserById(Long id);
//
//    /**
//     * 添加用户
//     * @param sysUser
//     * @return
//     */
//    Long addSysUser(SysUser sysUser);
//
//    /**
//     * 修改用户
//     * @param sysUser
//     * @return
//     */
//    int updateSysUser(SysUser sysUser);
//
//    /**
//     * 删除用户
//     * @param id
//     * @return
//     */
//    int deleteSysUser(Long id);
//
//    /**
//     * 查询所有用户
//     * @return
//     */
//    List<SysUser> ListUser();
    @Test
    void test02() {
        System.out.println("------------ListUser------------");
        List<SysUser> list = sysUserService.listUser();
        for (SysUser sysUser : list) {
            System.out.println(sysUser);
        }
        System.out.println("------------deleteSysUser------------");
        int i = sysUserService.deleteSysUser(3L);
        System.out.println("返回值(i):" + i);
        System.out.println("------------ListUser------------");
        list = sysUserService.listUser();
        for (SysUser sysUser : list) {
            System.out.println(sysUser);
        }
    }

    @Test
    void test01() {
        System.out.println("------------getSysUserById------------");
        SysUser sysUser = sysUserService.getSysUserById(1L);
        System.out.println(sysUser);
        System.out.println("------------addSysUser------------");
        SysUser sysUser1 = new SysUser();
        sysUser1.setUsername("alex");
        sysUser1.setPassword("123456");
        sysUser1.setNickname("测试人员");
        sysUser1.setPhone("12345678901");
        sysUser1.setStatus(1);
        sysUser1.setCreateTime(LocalDateTime.now());
        Long id = sysUserService.addSysUser(sysUser1);
        System.out.println("返回值(id):" + id);
        System.out.println("------------getByUsername------------");
        SysUser sysUser2 = sysUserService.getSysUserByName("alex");
        System.out.println(sysUser2);
        System.out.println("------------updateSysUser------------");
        sysUser2.setNickname("测试人员1");
        int i = sysUserService.updateSysUser(sysUser2);
        System.out.println("返回值(i):" + i);
    }
}
