package com.retail.inventory.controller;

import com.retail.inventory.common.Result;
import com.retail.inventory.dto.user.UserAddDTO;
import com.retail.inventory.dto.user.UserUpdateDTO;
import com.retail.inventory.entity.SysUser;
import com.retail.inventory.service.SysUserService;
import com.retail.inventory.vo.user.UserVO;
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
@RequestMapping("/user")
public class SysUserController {
    @Autowired
    SysUserService sysUserService;

    /**
     * 根据ID查询用户
     */
    @GetMapping("/{id}")
    public Result<UserVO> getById(@NotNull @PathVariable Long id) {

        SysUser user = sysUserService.getSysUserById(id);

        UserVO vo = new UserVO();
        BeanUtils.copyProperties(user, vo);

        return Result.success(vo);
    }

    /**
     * 查询全部用户
     */
    @GetMapping("/list")
    public Result<List<UserVO>> list() {

        List<SysUser> users = sysUserService.listUser();

        List<UserVO> voList = users.stream()
                .map(user -> {
                    UserVO vo = new UserVO();
                    BeanUtils.copyProperties(user, vo);
                    return vo;
                })
                .collect(Collectors.toList());

        return Result.success(voList);
    }

    /**
     * 新增用户
     */
    @PostMapping
    public Result<Long> add(@Valid @RequestBody UserAddDTO dto) {

        SysUser user = new SysUser();
        BeanUtils.copyProperties(dto, user);

        Long id = sysUserService.addSysUser(user);

        return Result.success(id);
    }

    /**
     * 修改用户
     */
    @PutMapping
    public Result<Integer> update(@Valid @RequestBody UserUpdateDTO dto) {

        SysUser user = new SysUser();
        BeanUtils.copyProperties(dto, user);

        int result = sysUserService.updateSysUser(user);

        return Result.success(result);
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/{id}")
    public Result<Integer> delete(@NotNull @PathVariable Long id) {

        int result = sysUserService.deleteSysUser(id);

        return Result.success(result);
    }

}
