package family.project.web.dto.item;

import family.project.domain.Item;
import family.project.domain.Member;
import family.project.domain.PurchasingItem;
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
public class ItemPurchasingConfirmDto {

    private Long id;
    private String name;
    private String nickname;
    private Integer price;

    private LocalDateTime createTime;
    private List<UploadFile> imgFile;
    private Integer likesCount;
    private ItemTag itemTag;
    private String tradeRegion;
    @Lob
    private String content;

    public ItemPurchasingConfirmDto(PurchasingItem purchasingItem) {
        Item item = purchasingItem.getItem();
        Member member = purchasingItem.getMember();
        this.id = purchasingItem.getId();
        this.name = item.getName();
        this.nickname = member.getNickname();
        this.price = item.getPrice();
        this.createTime = item.getCreateDate();
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
}
