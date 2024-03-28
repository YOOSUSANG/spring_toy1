package family.project.domain.security;

import family.project.domain.Member;
import family.project.domain.service.MemberService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        SavedRequest savedRequest = requestCache.getRequest(request, response);
        String redirectUrl = null;
        log.info("Success handler triggered."); // 로그 추가

        //id 조회
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal(); // Principal == PrincipalDetails
        Member member = principal.getMember();
        Long id = member.getId();

        //redirect를 진행했을 시에
        if (savedRequest != null) {
            String targetURI = savedRequest.getRedirectUrl();
            log.info("Success URI = {}", targetURI);
            if (targetURI.contains("/myInfo")) {
                redirectUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/myInfo/{id}")
                    .buildAndExpand(id)
                    .toUriString();
                response.sendRedirect(redirectUrl);
            }else if (targetURI.contains("/community/post/new")) {
                redirectUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/community/post/new")
                        .toUriString();
                response.sendRedirect(redirectUrl);

            }else if (targetURI.contains("/item/new")) {
                redirectUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/item/new")
                        .toUriString();
                //여기서 그냥 targetURI로 해주면 ?continue도 나오기 떄문에 깔끔하게 path를 정리하는게 낫다.
                response.sendRedirect(redirectUrl);
            }else if (targetURI.contains("/item/myItem/purchase/confirm")) {
                redirectUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/item")
                        .toUriString();
                //여기서 그냥 targetURI로 해주면 ?continue도 나오기 떄문에 깔끔하게 path를 정리하는게 낫다.
                response.sendRedirect(redirectUrl);
            }else if (targetURI.contains("/item/myItem")) {
                redirectUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/item/myItem")
                        .toUriString();
                //여기서 그냥 targetURI로 해주면 ?continue도 나오기 떄문에 깔끔하게 path를 정리하는게 낫다.
                response.sendRedirect(redirectUrl);

            }
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
