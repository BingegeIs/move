package bingege.movie.common.api;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class PagedResponse<T> {

    private List<T> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean last;

    public PagedResponse(List<T> content, long totalElements, int page, int size) {
        this.content = content;
        this.totalElements = totalElements;
        this.last = true;
        this.page = page;
        this.size = size;
        this.totalPages = (int) Math.ceil((double) totalElements / (double) size);
    }

    public static <T> PagedResponse<T> create(Page<T> pages) {
        return new PagedResponse<>(pages.getContent(), pages.getTotalElements(), pages.getNumber(), pages.getSize());
    }
}
