package family.project.domain;


import family.project.domain.board.Board;
import family.project.domain.enums.MemberType;
import family.project.domain.enums.RoleType;
import family.project.domain.mapped.BasicEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member extends BasicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private String username;
    private String nickname;
    private String email;


    private String password;


    private String img;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Enumerated(EnumType.STRING)
    private MemberType memberType;
    @Embedded
    private Address address;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interest_item_id")
    private InterestItem interestItem;

    @OneToMany(mappedBy = "member")
    private List<Item> items = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<PurchaseItem> purchaseItems = new ArrayList<>();
    @OneToMany(mappedBy = "member")
    private List<PurchasingItem> purchasingItems = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemSaveMember> ItemSaveMembers = new ArrayList<>();


    protected Member() {


    }



    public void changeEmail(String email) {
        this.email = email;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public void changeInterestItem(InterestItem interestItem) {
        this.interestItem = interestItem;
    }



    public Member(String username, String img, RoleType roleType, MemberType memberType, Address address) {
        this.username = username;
        this.img = img;
        this.roleType = roleType;
        this.memberType = memberType;
        this.address = address;
    }

    public Member(String username, String nickname, String email, String password, String img, RoleType roleType, MemberType memberType, Address address) {
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.img = img;
        this.roleType = roleType;
        this.memberType = memberType;
        this.address = address;
    }

    //***** 생성 메소드 *****//

    //test createMember -> 여기다 email, password 정보를 넘겨야 한다.
    public static Member craeteMember(String username, String img, RoleType roleType, MemberType memberType, Address address) {
        Member savedMember = new Member(username, img, roleType, memberType, address);
        return savedMember;
    }

    public static Member craeteMember(String email, String username, String img, RoleType roleType, MemberType memberType, Address address) {
        Member savedMember = new Member(username, img, roleType, memberType, address);
        savedMember.changeEmail(email);
        return savedMember;
    }

    public static Member craeteMember(String username, String nickname, String email, String password, String img, RoleType roleType, MemberType memberType, Address address) {
        Member savedMember = new Member(username, nickname, email, password, img, roleType, memberType, address);
        return savedMember;
    }

    //***** 비지니스 메소드 *****//
    public void ItemCancel(Long itemId) {
        getItemSaveMembers().removeIf(itemSaveMember -> itemSaveMember.getItem().getId() == itemId);
    }
}
