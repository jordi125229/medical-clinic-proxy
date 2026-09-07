package model;

import lombok.*;
import org.springframework.data.domain.Page;
import java.util.List;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@Builder
public class PageableDto<T> {
    private List<T> content;
    private Integer pageSize;
    private Integer pageNumber;
    private Long total;
    private Integer totalPages;

    public static <T> PageableDto<T> create(List<T> content, Page page) {
        return new PageableDto<>(content, page.getSize(), page.getPageable().getPageNumber(), page.getTotalElements(),
                page.getTotalPages());
    }
}
