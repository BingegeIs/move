package bingege.movie.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 资源已存在冲突（比如命名已存在）
 *
 * @author ywb
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceConflictException extends RuntimeException {
    private String resourceName;
    private String fieldName;
    private Object fieldValue;


    public ResourceConflictException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("'%s'为'%s'的%s资源已存在", fieldName, fieldValue, resourceName));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public ResourceConflictException(String message) {
        super(message);
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }
}
