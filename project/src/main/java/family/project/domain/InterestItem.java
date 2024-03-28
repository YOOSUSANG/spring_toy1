package family.project.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class InterestItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interest_item_id")
    private Long id;

    @OneToOne(mappedBy = "interestItem", fetch = FetchType.LAZY)
    private Member member;

    @OneToMany
    private List<Item> items = new ArrayList<>();

    public void changeMember(Member member) {
        this.member = member;
        member.changeInterestItem(this);
    }

    public void addItem(Item item) {
        getItems().add(item);
    }

    public static InterestItem interestItem_create(Member member, Item item) {
        InterestItem interestItem = new InterestItem();
        interestItem.changeMember(member);
        interestItem.addItem(item);
        return interestItem;
    }
}
