package bingege.movie.common.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {
    private int status;
    private String message;
    private Object error;
    private LocalDateTime timestamp;

    public static ApiError newObject(int status, String message, Object error) {
        return new ApiError(status, message, error, LocalDateTime.now());
    }

}
