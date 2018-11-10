package bingege.movie.manage.auth.payload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "LoginRequest", description = "登陆")
public class LoginRequest {
    @NotBlank
    @ApiModelProperty("用户密码")
    private String password;
    @NotBlank
    @ApiModelProperty("用户登录名")
    private String username;
    @NotBlank
    @ApiModelProperty("密钥")
    private String secret;
    @NotBlank
    @ApiModelProperty("验证码")
    private String captcha;
}
