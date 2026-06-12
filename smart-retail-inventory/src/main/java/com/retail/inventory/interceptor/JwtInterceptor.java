package com.retail.inventory.interceptor;

import com.retail.inventory.annotation.RequireRole;
import com.retail.inventory.context.CurrentUserHolder;
import com.retail.inventory.exception.BizException;
import com.retail.inventory.exception.BizExceptionEnum;
import com.retail.inventory.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
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

        //--------------------------
        System.out.println(
                request.getMethod()
                        + " "
                        + request.getRequestURI()
        );
        //--------------------------

        String token = request.getHeader("Authorization");


        if (token == null || token.isBlank()) {
            throw new BizException(BizExceptionEnum.USER_NOT_LOGIN);
        }

        Claims claims = jwtUtil.parseToken(token);

        Long userId = claims.get("userId", Long.class);
        String username = claims.getSubject();

        request.setAttribute("roles", claims.get("roles"));

        CurrentUserHolder.set(
                userId,
                username
        );

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

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex
    ) {
        CurrentUserHolder.clear();
    }
}
