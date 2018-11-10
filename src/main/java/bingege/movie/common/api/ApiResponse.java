package bingege.movie.common.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private int status;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    public static <T> ApiResponse<T> create(int status, String message, T data) {
        return new ApiResponse(status, message, data, LocalDateTime.now());
    }

    public static <T> ApiResponse<T> create(T data) {
        return ApiResponse.create(HttpStatus.OK.value(), "OK", data);
    }

    public static <T> ApiResponse<T> create(T data, String msg) {
        return ApiResponse.create(HttpStatus.OK.value(), msg, data);
    }

    public static <T> ApiResponse<PagedResponse<T>> createPages(Page<T> page) {
        return create(PagedResponse.create(page));
    }
}
