package family.project.domain.security;


import family.project.CustomBCryPasswordEncoder;
import family.project.domain.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final MemberService memberService;
    private final CustomBCryPasswordEncoder bCryPasswordEncoder;
    private final PrincipalDetailsService principalDetailsService;

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider bean = new DaoAuthenticationProvider();
        bean.setHideUserNotFoundExceptions(false);
        bean.setUserDetailsService(principalDetailsService);
        bean.setPasswordEncoder(bCryPasswordEncoder);
        return bean;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(CsrfConfigurer::disable);
        http.authenticationProvider(daoAuthenticationProvider());
        http.authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/myInfo/**").authenticated()
                                .anyRequest().permitAll())
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/loginHome") // 권한이 없는 곳에 가면 해당 경로로 리다이렉트된다.
                                .loginProcessingUrl("/login")
                                .failureHandler(new CustomFailureHandler(memberService))
                                .defaultSuccessUrl("/") //로그인 성공후 기본 redirect
                                .successHandler(new CustomSuccessHandler())
                                .usernameParameter("email")); // email로 check하기 위해서는 username 파라미터를 변경해줘야 한다.

        return http.build();
    }
}
