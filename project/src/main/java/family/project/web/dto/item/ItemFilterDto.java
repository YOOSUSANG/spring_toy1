package family.project.web.dto.item;

import family.project.domain.enums.BoardCheckTag;
import family.project.domain.enums.ItemCheckTag;
import family.project.domain.enums.item.ItemTag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemFilterDto {

    private String title; // 이름
    private ItemTag categoryTag; // 카테고리
    private ItemCheckTag order; // 최신순, 높은 금액순 등등

}
