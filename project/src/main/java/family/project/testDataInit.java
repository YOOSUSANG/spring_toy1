package family.project;

import family.project.domain.Address;
import family.project.domain.Member;
import family.project.domain.enums.MemberType;
import family.project.domain.enums.RoleType;
import family.project.domain.service.MemberService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class testDataInit {

    private final MemberService memberService;

    @PostConstruct
    public void init() {
        memberService.register(Member.craeteMember("유수상", "이러쿵저러쿵", "dbtntkd456@naver.com", "123123123",
                null, RoleType.USER, MemberType.BROTHER, new Address("경기도", "의정부시", "329")));

    }
}
