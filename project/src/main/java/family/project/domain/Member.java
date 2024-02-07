package family.project.domain;


import family.project.domain.board.Board;
import family.project.domain.enums.MemberType;
import family.project.domain.enums.RoleType;
import family.project.domain.mapped.BasicEntity;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

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
    private String email;

    @Length(min = 8, max = 15)
    private String password;


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


    public void changeEmail(String email) {
        this.email = email;
    }

    public void changePassword(String password) {
        this.password = password;
    }
    public Member(String username, String img, RoleType roleType,MemberType memberType,Address address) {
        this.username = username;
        this.img = img;
        this.roleType = roleType;
        this.memberType = memberType;
        this.address = address;
    }

    //***** 생성 메소드 *****//

    //test createMember -> 여기다 email, password 정보를 넘겨야 한다.
    public static Member craeteMember(String username, String img, RoleType roleType,MemberType memberType, Address address) {
        Member savedMember = new Member(username, img, roleType,memberType, address);
        return savedMember;
    }
    public static Member craeteMember(String email, String username, String img, RoleType roleType,MemberType memberType, Address address) {
        Member savedMember = new Member(username, img, roleType,memberType, address);
        savedMember.changeEmail(email);
        return savedMember;
    }


    //***** 연관 메소드 *****//








}
