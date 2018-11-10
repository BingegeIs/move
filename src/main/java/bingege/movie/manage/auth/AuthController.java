package bingege.movie.manage.auth;

import bingege.movie.common.api.ApiResponse;
import bingege.movie.common.config.security.JwtTokenProvider;
import bingege.movie.common.exception.BadCaptchaException;
import bingege.movie.common.service.UserService;
import bingege.movie.manage.auth.payload.JwtAuthenticationResponse;
import bingege.movie.manage.auth.payload.LoginRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Api(tags = "后台管理 - 登录认证")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtTokenProvider tokenProvider;
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @ApiOperation(value = "登录")
    public ApiResponse<JwtAuthenticationResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        if (!passwordEncoder.matches(loginRequest.getCaptcha(), loginRequest.getSecret())) {
            throw new BadCaptchaException("验证码错误");
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //登录成功，更新登录时间
        userService.fresh(loginRequest.getUsername());
        String jwt = tokenProvider.generateToken(authentication);
        return ApiResponse.create(new JwtAuthenticationResponse(jwt));
    }

    @GetMapping("test")
    public String genToken() {
        return passwordEncoder.encode("123456");
    }
}
