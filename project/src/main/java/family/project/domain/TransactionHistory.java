package family.project.domain;


import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class TransactionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_history_id")
    private Long id;
    //조회만 가능 mappedBy는 해당 FK에 의해 매핑된 것으로 의미
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_item_id")
    private PurchaseItem purchaseItem;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void changePurchaseItem(PurchaseItem purchaseItem) {
        this.purchaseItem = purchaseItem;
        purchaseItem.changeTransactionHistory(this);
    }

    public void changeMember(Member member) {
        this.member = member;
    }

    public static TransactionHistory transactionHistory_create(PurchaseItem purchaseItem,Member member) {
        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.changePurchaseItem(purchaseItem);
        transactionHistory.changeMember(member);
        return transactionHistory;
    }
}
