package bingege.movie.common.exception;

/**
 * 错误验证码
 */
public class BadCaptchaException extends RuntimeException {

  public BadCaptchaException(String message) {
    super(message);
  }

  public BadCaptchaException(String message, Throwable cause) {
    super(message, cause);
  }
}
