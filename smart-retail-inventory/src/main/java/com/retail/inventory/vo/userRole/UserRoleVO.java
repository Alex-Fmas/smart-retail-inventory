package com.retail.inventory.vo.userRole;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserRoleVO {
    private Long id;
    private String username;
    private String nickname;
    private String phone;
    private Integer status;
    private LocalDateTime createTime;
    private List<String> roleNames;
}
