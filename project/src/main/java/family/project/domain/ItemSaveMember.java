package family.project.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class ItemSaveMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //여러개의 itemSaveMember(item 포함)가 member에 속한다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    //여러개의 itemSaveMember(member 포함)가 item에 속한다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;


    //연관관계 메소드//
    public void changeMember(Member member) {
        this.member = member;
        member.getItemSaveMembers().add(this);
    }

    //아이템에서 굳이 멤버로 조회할 필요가 없다.
    public void changeItem(Item item) {
        this.item = item;
    }

    //생성 메소드//
    public static ItemSaveMember createItemSaveMember(Member member, Item item) {
        ItemSaveMember itemSaveMember = new ItemSaveMember();
        itemSaveMember.changeMember(member);
        itemSaveMember.changeItem(item);
        return itemSaveMember;
    }
}
