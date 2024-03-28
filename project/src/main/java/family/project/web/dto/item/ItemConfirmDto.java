package family.project.web.dto.item;


import family.project.domain.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemConfirmDto {

    private Long id;
    private String nickname;
    private String name;
    private Integer price;
    private String tradeRegion;

    public ItemConfirmDto(Item item) {
        this.id = item.getId();
        this.nickname = item.getMember().getNickname();
        this.name = item.getName();
        this.price = item.getPrice();
        this.tradeRegion = item.getTradeRegion();

    }
}
