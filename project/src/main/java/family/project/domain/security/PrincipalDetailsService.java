package family.project.domain.security;

import family.project.domain.Member;
import family.project.domain.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
    private final MemberService memberService;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("email = {}", email);
        Member findMember = memberService.search(email);

        /**
         * 아이디가 정상인데 비밀번호가 공백이거나 틀렸으면 bad
         * 아이디가 비정상이거나 아이디가 공백이면 username
         */
        if (email.isEmpty()) {
            throw new InternalAuthenticationServiceException(email);
        }
        if (findMember == null) {
            log.info("에러 발생");
            throw new UsernameNotFoundException(email);
        }
        //아이디는 맞으나 비밀번호 인증이 틀렸으면 Bad credentials로 진행한다.
        return new PrincipalDetails(findMember);

    }
}
