package com.retail.inventory.interceptor;

import com.retail.inventory.annotation.RequireRole;
import com.retail.inventory.exception.BizException;
import com.retail.inventory.exception.BizExceptionEnum;
import com.retail.inventory.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

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

        String token = request.getHeader("Authorization");

        if (token == null || token.isBlank()) {
            throw new BizException(BizExceptionEnum.USER_NOT_LOGIN);
        }

        Claims claims = jwtUtil.parseToken(token);

        request.setAttribute("roles", claims.get("roles"));
        if (handler instanceof HandlerMethod hm) {

            RequireRole requireRole =
                    hm.getMethodAnnotation(
                            RequireRole.class
                    );

            if (requireRole != null) {

                String needRole =
                        requireRole.value();

                List<String> roles =
                        claims.get(
                                "roles",
                                List.class
                        );

                if (!roles.contains(needRole)) {

                    throw new BizException(
                            BizExceptionEnum.NO_PERMISSION
                    );
                }
            }
        }

        return true;
    }
}
