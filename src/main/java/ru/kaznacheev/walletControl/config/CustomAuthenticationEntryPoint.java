package ru.kaznacheev.walletControl.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import ru.kaznacheev.walletControl.dto.response.BaseResponse;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpStatus.FORBIDDEN.value());
        String json = new ObjectMapper().writeValueAsString(BaseResponse.builder()
                .title("ACCESS_DENIED")
                .detail("Доступ запрещен")
                .status(HttpStatus.FORBIDDEN.value())
                .build());
        response.getWriter().print(json);
    }
}
