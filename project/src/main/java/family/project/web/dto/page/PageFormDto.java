package family.project.web.dto.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageFormDto {

    int start;
    int end;
    int totalPage;
    int currentPage;
    int nextPage;
    int previousPage;


}
