package bingege.movie.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 表示客户端错误，意味着对于目标资源的访问请求被拒绝。
 * 这通常发生于采用除 GET 和 HEAD 之外的方法进行条件请求时，
 * 这时候，请求的操作——通常是上传或修改文件——无法执行，从而返回该错误状态码。
 * @author ywb
 */
@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class PreconditionFailedException extends RuntimeException {

    public PreconditionFailedException(String message) {
        super(message);
    }

    public PreconditionFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
