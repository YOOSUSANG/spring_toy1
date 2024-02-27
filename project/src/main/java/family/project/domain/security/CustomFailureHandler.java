package family.project.domain.security;

import family.project.domain.Member;
import family.project.domain.service.MemberService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final MemberService memberService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String errormessage = "";
        String message = exception.getMessage();

        log.info("excepion messages before = {}", exception.getMessage());

        if (exception instanceof InternalAuthenticationServiceException) {
            errormessage = "아이디를 확인해주세요.";
        }

        if (exception instanceof UsernameNotFoundException) {
            Member findMember = memberService.search(message);
            if (findMember == null) {
                errormessage = "잘못된 사용자 접근입니다. 아이디를 다시 확인해주세요.";

            }

        }
        if (exception instanceof BadCredentialsException) {
            errormessage = "비밀번호를 다시 확인해주세요.";
        }
        errormessage = URLEncoder.encode(errormessage, StandardCharsets.UTF_8);
        setDefaultFailureUrl("/login?error=true&exception=" + errormessage);
        super.onAuthenticationFailure(request, response, exception);
    }
}
