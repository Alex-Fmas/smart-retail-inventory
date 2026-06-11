package com.retail.inventory.interceptor;

import com.retail.inventory.exception.BizException;
import com.retail.inventory.exception.BizExceptionEnum;
import com.retail.inventory.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) {

        String token =
                request.getHeader("Authorization");

        if (token == null || token.isBlank()) {
            throw new BizException(BizExceptionEnum.USER_NOT_LOGIN);
        }

        jwtUtil.parseToken(token);

        return true;
    }
}
