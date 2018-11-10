package bingege.movie.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 在HTTP协议中，响应状态码  429 Too Many Requests
 * 表示在一定的时间内用户发送了太多的请求，即超出了“频次限制”。
 *
 * @author ywb
 */
@ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
public class RequestTooFrequentlyException extends RuntimeException {

    public RequestTooFrequentlyException(String message) {
        super(message);
    }

    public RequestTooFrequentlyException(String message, Throwable cause) {
        super(message, cause);
    }
}
