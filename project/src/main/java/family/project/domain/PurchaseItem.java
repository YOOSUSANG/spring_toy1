package family.project.domain;


import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class PurchaseItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(mappedBy = "purchaseItem")
    private TransactionHistory transactionHistory;

    public void changeItem(Item item) {
        this.item = item;
    }

    public void changeTransactionHistory(TransactionHistory transactionHistory) {
        this.transactionHistory = transactionHistory;
    }
    public void changeMember(Member member) {
        this.member = member;
        member.getPurchaseItems().add(this);
    }

    public static PurchaseItem createPurchaseItem(Item item, Member member) {
        PurchaseItem purchaseItem = new PurchaseItem();
        purchaseItem.changeItem(item);
        purchaseItem.changeMember(member);
        return purchaseItem;
    }

}
