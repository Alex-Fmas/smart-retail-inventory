package com.retail.inventory.vo.user;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserVO {
    private Long id;
    private String username;
    private String nickname;
    private String phone;
    private Integer status;
    private LocalDateTime createTime;
}
