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
    private String img;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Enumerated(EnumType.STRING)
    private MemberType memberType;




    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Board> boards = new ArrayList<>();

    protected Member() {}

    public Member(String username, String img, RoleType roleType,MemberType memberType,Address address) {
        this.username = username;
        this.img = img;
        this.roleType = roleType;
        this.memberType = memberType;
        this.address = address;
    }

    //***** 생성 메소드 *****//
    public static Member craeteMember(String username, String img, RoleType roleType,MemberType memberType, Address address) {
        Member savedMember = new Member(username, img, roleType,memberType, address);
        return savedMember;
    }
    //***** 연관 메소드 *****//








}
