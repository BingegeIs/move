package bingege.movie.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 被请求的资源在服务器上已经不再可用，而且没有任何已知的转发地址。
 * @author ywb
 */
@ResponseStatus(HttpStatus.GONE)
public class NoLongerAvailableException extends RuntimeException{
    public NoLongerAvailableException(String message) {
        super(message);
    }

    public NoLongerAvailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
