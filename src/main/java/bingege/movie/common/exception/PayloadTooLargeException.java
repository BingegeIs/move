package bingege.movie.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * 上传的文件太大
 * @author ywb
 */
@ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
public class PayloadTooLargeException extends MaxUploadSizeExceededException {

    public PayloadTooLargeException(long maxUploadSize) {
        super(maxUploadSize);
    }

}
