package family.project.web.dto.item;

import family.project.domain.Item;
import family.project.domain.enums.item.ItemTag;
import family.project.domain.food.Food;
import family.project.domain.groceries.HealthGroceries;
import family.project.domain.product.Electronic;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewItemFormDto {
    private Long id;
    //이미지 파일
    private List<MultipartFile> imageFiles;
    private ItemTag itemTag;
    private String name;
    private Integer price;

    @Lob
    private String content;
    private String tradePlace;

    public NewItemFormDto(Item item) {
        this.id = item.getId();
        this.itemTag = item.getItemTag();
        this.name = item.getName();
        this.price = item.getPrice();
        this.tradePlace = item.getTradeRegion();
        if (item instanceof Food) {
            Food changeItem = (Food) item;
            this.content = changeItem.getInformation();
        }
        if (item instanceof HealthGroceries) {
            HealthGroceries changeItem = (HealthGroceries) item;
            this.content = changeItem.getInformation();
        }
        if (item instanceof Electronic) {
            Electronic changeItem = (Electronic) item;
            this.content = changeItem.getInformation();
        }
    }





}
