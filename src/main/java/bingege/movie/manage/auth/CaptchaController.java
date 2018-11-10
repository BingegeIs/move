package bingege.movie.manage.auth;

import bingege.movie.common.api.ApiResponse;
import bingege.movie.common.exception.AppException;
import bingege.movie.manage.auth.payload.CaptureResponse;
import com.google.code.kaptcha.Producer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@RestController
@RequestMapping("/api/captcha/")
@Api(tags = "后台管理 - 验证码")
public class CaptchaController {
    private static final Logger logger = LoggerFactory.getLogger(CaptchaController.class);

    @Autowired
    Producer captchaProducer;
    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("")
    @ApiOperation(value = "获取图片验证码")
    public ApiResponse<CaptureResponse> getCaptcha() {
        String capText = captchaProducer.createText();
        String encodedCapText = passwordEncoder.encode(capText);
        BufferedImage bi = captchaProducer.createImage(capText);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(bi, "png", os);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage());
            throw new AppException("can't write captcha image");
        }
        String imgStr = Base64.getEncoder().encodeToString(os.toByteArray());
        logger.info("captcha:" + capText);
        return ApiResponse.create(new CaptureResponse(encodedCapText, imgStr));
    }
}
