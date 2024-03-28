package family.project.domain;

import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
public class CategoryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_item_id")
    private Long id;


    //여러 개의 카테고리 상품이 하나의 아이템을 선택할 수 있다.
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_category_id")
    private ItemCategory itemCategory;

    public void changeItem(Item item) {
        this.item = item;
    }

}
