package family.project.web.dto.item;

import family.project.domain.Item;
import family.project.domain.enums.item.ItemTag;
import family.project.domain.file.UploadFile;
import family.project.domain.food.Food;
import family.project.domain.groceries.HealthGroceries;
import family.project.domain.product.Electronic;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemAllFormDto {

    private Long id;
    private String name;
    private String nickname;
    private Integer price;

    private LocalDateTime createTime;
    private List<UploadFile> imgFile;
    private Integer likesCount;
    private ItemTag itemTag;
    private String tradeRegion;
    private Boolean soldOut;
    @Lob
    private String content;
    public ItemAllFormDto(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.nickname = item.getMember().getNickname();
        this.createTime = item.getCreateDate();
        this.price = item.getPrice();
        this.imgFile = item.getImageFiles();
        this.likesCount = item.getLikesCount();
        this.itemTag = item.getItemTag();
        this.tradeRegion = item.getTradeRegion();

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
    public ItemAllFormDto(Item item, Boolean soldOut) {
        this.id = item.getId();
        this.name = item.getName();
        this.nickname = item.getMember().getNickname();
        this.createTime = item.getCreateDate();
        this.price = item.getPrice();
        this.imgFile = item.getImageFiles();
        this.likesCount = item.getLikesCount();
        this.itemTag = item.getItemTag();
        this.tradeRegion = item.getTradeRegion();
        this.soldOut = soldOut;

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
