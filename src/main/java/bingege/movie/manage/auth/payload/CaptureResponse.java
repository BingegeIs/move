package bingege.movie.manage.auth.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CaptureResponse {
    private String secret;
    private String base64Img;
}
