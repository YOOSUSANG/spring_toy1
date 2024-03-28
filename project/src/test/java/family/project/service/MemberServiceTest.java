package family.project.service;

import family.project.domain.Address;
import family.project.domain.Member;
import family.project.domain.enums.MemberType;
import family.project.domain.enums.RoleType;
import family.project.domain.service.MemberService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
@SpringBootTest()
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @BeforeEach
    public void 상태_생성() {
        Member member1 = Member.craeteMember("yoosusang1", null, RoleType.USER, MemberType.MOM, new Address("서울", "압구정", "111-111"));
        Member member2 = Member.craeteMember("yoosusang2", null, RoleType.USER, MemberType.DAD, new Address("서울", "가로수길", "111-112"));
        Member member3 = Member.craeteMember("yoosusang3", null, RoleType.USER, MemberType.SISTER, new Address("서울", "도봉구", "111-113"));
        memberService.register(member1);
        memberService.register(member2);
        memberService.register(member3);

    }

    @AfterEach
    public void 상턔_초기화() {
        memberService.clear();
    }

    @DisplayName("회원가입")
    @Test
    public void 회원가입_확인() throws Exception {
        //given

        //when
        List<Member> all = memberService.search();
        Member findMemberById = memberService.search(1L);
        Member findMemberByUsername = memberService.search("yoosusang2");
        //then
        //all의 username들이 containsExactly한지 체크하는 것이다.
        assertThat(all).extracting("username").containsExactly("yoosusang1", "yoosusang2", "yoosusang3");
        assertThat(all.size()).isEqualTo(3);
        assertEquals(findMemberByUsername.getUsername(), "yoosusang2", "이름이 같습니다.");
        assertThat(findMemberByUsername.getUsername()).isEqualTo("yoosusang2");
    }

    @DisplayName("중복회원")
    @Test
    public void 중복회원_테스트() throws Exception {
        //given
        Member member4 = Member.craeteMember("yoosusang3", null, RoleType.USER, MemberType.BROTHER, new Address("의정부", "가능동", "111-114"));
        //when
        //then
        //예외처리를 검증하는 것 -> memberService.register시 DataIntegrityViolationException.class가 발생하기를 원한다.
        assertThrows(DataIntegrityViolationException.class, () -> {
            memberService.register(member4);
        });

    }

    @DisplayName("삭제")
    @Test
    public void 삭제_확인() throws Exception {
        //given
        Member member4 = Member.craeteMember("yoosusang4", null, RoleType.USER, MemberType.BROTHER, new Address("의정부", "가능동", "111-114"));
        Member member5 = Member.craeteMember("yoosusang5", null, RoleType.USER, MemberType.BROTHER, new Address("수원", "원천동", "111-115"));
        Member member6 = Member.craeteMember("yoosusang6", null, RoleType.USER, MemberType.BROTHER, new Address("수원", "메탄동", "111-116"));
        memberService.register(member4);
        memberService.register(member5);
        memberService.register(member6);
        //when
        memberService.remove(member4);
        List<Member> all = memberService.search();
        memberService.clear();
        List<Member> allAfterRemove = memberService.search();
        //then
        assertThat(all).extracting("username").containsExactly("yoosusang1", "yoosusang2", "yoosusang3", "yoosusang5", "yoosusang6");
        assertThat(all.size()).isEqualTo(5);
        assertThat(allAfterRemove.size()).isEqualTo(0);
    }


}