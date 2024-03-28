package family.project.web.dto.community;

import family.project.domain.enums.BoardCheckTag;
import family.project.domain.enums.BoardTag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardFilterDto {

    private String title;
    private BoardTag categoryTag;
    private BoardCheckTag order;





}
