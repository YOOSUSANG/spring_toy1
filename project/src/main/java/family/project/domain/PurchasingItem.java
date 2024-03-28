package family.project.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class PurchasingItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchasing_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void changeItem(Item item) {
        this.item = item;
    }

    public void changeMember(Member member) {
        this.member = member;
        member.getPurchasingItems().add(this);
    }
    public static PurchasingItem createPurchasingItem(Item item, Member member) {
        PurchasingItem purchasingItem = new PurchasingItem();
        purchasingItem.changeItem(item);
        purchasingItem.changeMember(member);
        return purchasingItem;
    }
}
