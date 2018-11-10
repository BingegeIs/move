package bingege.movie.common.api;

import bingege.movie.common.exception.*;
import org.hibernate.validator.internal.engine.path.NodeImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * 自定义异常处理器，用于处理Controller层抛出的各种异常。
 *
 * @author ywb
 */
@RestControllerAdvice
public class CustomizeExceptionHandler {
    private static final String RESOURCE_CONFILICT_MESSAGE = "请求冲突";
    private static final String UNAUTHORIZED_MESSAGE = "认证失败";
    private static final String EXCEPTION_MESSAGE = "服务器出现了一个未知异常。";
    private static final String RESOURCE_NOT_FOUND_MESSAGE = "请求的对象不存在！";
    private static final String METHOD_ARGUMENT_TYPE_MISMATCH_MESSAGE = "请求参数类型有误，请修改后重试！";
    private static final String BIND_MESSAGE = "请求参数类型有误，请修改后重试！";
    private static final String CONSTRAINT_VIOLATION_MESSAGE = "请求参数不符合规范，请检查后再试！";
    private static final String METHOD_ARGUMENT_NOT_VALID_MESSAGE = "请求参数不符合规范，请修改后重试！";
    private static final String HTTP_MESSAGE_NOT_READABLE_EXCEPTION_MESSAGE = "请求参数格式错误，请修改后重试！";
    public static final String SERVICE_UNAVAILABLE_MESSAGE = "服务不可用";
    public static final String PAYLOAD_TOO_LARGE_MESSAGE = "请求的参数太大！";
    public static final String UNSUPPORTED_MEDIA_TYPE_MESSAGE = "不支持该媒体类型";
    public static final String ACCESS_DENIED_EXCEPTION = "权限不足";
    public static final String GONE_MESSAGE = "被请求的资源在服务器上已经不再可用。";
    public static final String TOO_MANY_REQUESTS_MESSAGE = "请求频率太高";
    public static final String METHOD_NOT_ALLOWED = "方法不被允许";
    public static final String PRECONDITION_FAILED_MESSAGE = "请求条件不符合要求!";
    public static final String INTERNAL_SERVER_ERROR_MESSAGE = "服务器出现未知异常";
    public static final String BAD_REQUEST_MESSAGE = "请求错误";
    private static final String LOGIN_FAIL_MESSAGE = "用户登录失败，用户名或密码错误。";

    private static Logger log = LoggerFactory.getLogger(CustomizeExceptionHandler.class);

    /**
     * 处理请求参数校验异常
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiError handleValidationExceptions(MethodArgumentNotValidException e) {
        log.debug(e.getMessage(), e);
        Map<String, String> errorMessages = new HashMap<>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            String field = error.getField();
            errorMessages.put(field, error.getDefaultMessage());
        }
        return ApiError.newObject(HttpStatus.BAD_REQUEST.value(), METHOD_ARGUMENT_NOT_VALID_MESSAGE, errorMessages);
    }

    /**
     * 处理Controller层method中参数校验异常
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ApiError handleMethodValidationExceptions(ConstraintViolationException e) {
        log.debug(e.getMessage(), e);
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        Map<String, String> errorMessages = new HashMap<>();
        for (ConstraintViolation violation : constraintViolations) {
            PathImpl propertyPath = (PathImpl) violation.getPropertyPath();
            NodeImpl leafNode = propertyPath.getLeafNode();
            errorMessages.put(leafNode.getName(), violation.getMessage());
        }
        return ApiError.newObject(HttpStatus.BAD_REQUEST.value(), CONSTRAINT_VIOLATION_MESSAGE, errorMessages);
    }

    /**
     * 处理自定义BadRequestException
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadCaptchaException.class)
    public ApiError handleBadCaptchaException(BadCaptchaException e) {
        log.debug(e.getMessage(), e);
        return ApiError.newObject(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
    }

    /**
     * 处理自定义BadCredentialsException, 用户登录验证异常
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadCredentialsException.class)
    public ApiError handleBadCredentialsException(BadCredentialsException e) {
        log.debug(e.getMessage(), e);
        return ApiError.newObject(HttpStatus.BAD_REQUEST.value(), LOGIN_FAIL_MESSAGE, e.getMessage());
    }

    /**
     * 处理自定义BadRequestException
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ApiError handleBadRequestException(BadRequestException e) {
        log.debug(e.getMessage(), e);
        return ApiError.newObject(HttpStatus.BAD_REQUEST.value(), BAD_REQUEST_MESSAGE, e.getMessage());
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ApiError handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.debug(e.getMessage(), e);
        return ApiError.newObject(HttpStatus.BAD_REQUEST.value(), BAD_REQUEST_MESSAGE, e.getMessage());
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ApiError handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.debug(e.getMessage(), e);
        return ApiError.newObject(HttpStatus.METHOD_NOT_ALLOWED.value(), METHOD_NOT_ALLOWED, e.getMessage());
    }

    /**
     * 处理自定义AppException
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(AppException.class)
    public ApiError handleAppException(AppException e) {
        log.error(e.getMessage(), e);
        return ApiError.newObject(HttpStatus.INTERNAL_SERVER_ERROR.value(), INTERNAL_SERVER_ERROR_MESSAGE, e.getMessage());
    }

    /**
     * 处理请求参数自动转换为实体失败的异常。
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ApiError handleMessageReadExceptions(HttpMessageNotReadableException e) {
        log.debug(e.getMessage(), e);
        return ApiError.newObject(HttpStatus.NOT_ACCEPTABLE.value(), HTTP_MESSAGE_NOT_READABLE_EXCEPTION_MESSAGE,
                e.getCause().getMessage());
    }

    /**
     * 请求参数太大，比如文件上传
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ApiError handlePayloadTooLargeExceptions(MaxUploadSizeExceededException e) {
        log.debug(e.getMessage(), e);
        return ApiError.newObject(HttpStatus.PAYLOAD_TOO_LARGE.value(), PAYLOAD_TOO_LARGE_MESSAGE,
                e.getMessage());
    }

    /**
     * 处理请求参数映射过程中，
     * 对象类型转换失败的异常。
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ApiError handleMessageBindExceptions(BindException e) {
        log.debug(e.getMessage(), e);
        List<FieldError> fieldErrors = e.getFieldErrors();
        Map<String, String> errorMessages = new HashMap<>();
        for (FieldError error : fieldErrors) {
            String field = error.getField();
            Object rejectedValue = error.getRejectedValue();
            String message = "[\'" + rejectedValue + "\'不能应用于参数" + field + "]";
            errorMessages.put(field, message);
        }
        return ApiError.newObject(HttpStatus.BAD_REQUEST.value(), BIND_MESSAGE,
                errorMessages);
    }

    /**
     * 处理请求参数映射过程中，
     * 对象类型转换失败的异常。
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ApiError handleMessageTypeMismatchExceptions(MethodArgumentTypeMismatchException e) {
        log.debug(e.getMessage(), e);
        Map<String, String> errorMessages = new HashMap<>();
        errorMessages.put(e.getName(), e.getValue() + "无法应用于" + e.getName());
        return ApiError.newObject(HttpStatus.BAD_REQUEST.value(), METHOD_ARGUMENT_TYPE_MISMATCH_MESSAGE,
                errorMessages);
    }

    /**
     * 资源未找到异常处理
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ApiError handleResourceNotFoundExceptions(ResourceNotFoundException e) {
        log.debug(e.getMessage(), e);
        return ApiError.newObject(HttpStatus.NOT_FOUND.value(), RESOURCE_NOT_FOUND_MESSAGE,
                e.getMessage());
    }

    /**
     * 媒体类型不支持异常处理
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(UnsupportedMediaTypeException.class)
    public ApiError handleUnsupportedMediaTypeExceptions(UnsupportedMediaTypeException e) {
        log.debug(e.getMessage(), e);
        return ApiError.newObject(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), UNSUPPORTED_MEDIA_TYPE_MESSAGE,
                e.getMessage());
    }

    /**
     * 服务不可用
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(ServiceUnavailableException.class)
    public ApiError handleServiceUnavailableExceptions(ServiceUnavailableException e) {
        log.error(e.getMessage(), e);
        return ApiError.newObject(HttpStatus.SERVICE_UNAVAILABLE.value(), SERVICE_UNAVAILABLE_MESSAGE,
                e.getMessage());
    }

    /**
     * 资源冲突异常处理
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ResourceConflictException.class)
    public ApiError handleResourceConflictExceptions(ResourceConflictException e) {
        log.debug(e.getMessage(), e);
        return ApiError.newObject(HttpStatus.CONFLICT.value(), RESOURCE_CONFILICT_MESSAGE,
                e.getMessage());
    }

    /**
     * 资源冲突异常处理
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public ApiError handleUnauthorizedExceptions(UnauthorizedException e) {
        log.debug(e.getMessage(), e);
        return ApiError.newObject(HttpStatus.UNAUTHORIZED.value(), UNAUTHORIZED_MESSAGE,
                e.getMessage());
    }

    /**
     * 权限不足访问失败。
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ApiError handleExceptions(AccessDeniedException e) {
        log.debug(e.getMessage(), e);
        return ApiError.newObject(HttpStatus.FORBIDDEN.value(), ACCESS_DENIED_EXCEPTION,
                e.getMessage());
    }

    /**
     * 被请求的资源在服务器上已经不再可用。
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.GONE)
    @ExceptionHandler(NoLongerAvailableException.class)
    public ApiError handleExceptions(NoLongerAvailableException e) {
        log.debug(e.getMessage(), e);
        return ApiError.newObject(HttpStatus.GONE.value(), GONE_MESSAGE,
                e.getMessage());
    }

    /**
     * 被请求的资源在服务器上已经不再可用。
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    @ExceptionHandler(RequestTooFrequentlyException.class)
    public ApiError handleRequestTooFrequentlyExceptions(RequestTooFrequentlyException e) {
        log.debug(e.getMessage(), e);
        return ApiError.newObject(HttpStatus.TOO_MANY_REQUESTS.value(), TOO_MANY_REQUESTS_MESSAGE,
                e.getMessage());
    }

    /**
     * 被请求的资源在服务器上已经不再可用。
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(MethodNotAllowedException.class)
    public ApiError handleMethodNotAllowedExceptions(MethodNotAllowedException e) {
        log.debug(e.getMessage(), e);
        return ApiError.newObject(HttpStatus.METHOD_NOT_ALLOWED.value(), METHOD_NOT_ALLOWED,
                e.getMessage());
    }

    /**
     * 请求条件不符合要求。
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    @ExceptionHandler(PreconditionFailedException.class)
    public ApiError handlePreconditionFailedExceptions(PreconditionFailedException e) {
        log.debug(e.getMessage(), e);
        return ApiError.newObject(HttpStatus.PRECONDITION_FAILED.value(), PRECONDITION_FAILED_MESSAGE,
                e.getMessage());
    }

    /**
     * Exception异常集中处理。
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ApiError handleExceptions(Exception e) {
        log.error(e.getMessage(), e);
        return ApiError.newObject(HttpStatus.INTERNAL_SERVER_ERROR.value(), EXCEPTION_MESSAGE,
                e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ApiError InternalAuthenticationServiceExceptions(InternalAuthenticationServiceException e) {
        log.error(e.getMessage(), e);
        return ApiError.newObject(HttpStatus.UNAUTHORIZED.value(), UNAUTHORIZED_MESSAGE,
                e.getMessage());
    }
}
