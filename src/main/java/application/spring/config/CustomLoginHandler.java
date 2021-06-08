package application.spring.config;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Slf4j
@Component
public class CustomLoginHandler implements AuthenticationSuccessHandler {
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @SneakyThrows(IOException.class)
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            System.out.println(authority.getAuthority());
            if (authority.getAuthority().equals("ROLE_ADMIN")) {
                redirectStrategy.sendRedirect(request, response, "/admin");//делаем редирект на админ контроллер
                log.info("admin " + authentication.getName() + " is logged in");
                break;
            } else if (authority.getAuthority().equals("ROLE_ENGINEER")) {
                redirectStrategy.sendRedirect(request, response, "/engineer");
                log.info("engineer " + authentication.getName() + " is logged in");
                break;
            } else if (authority.getAuthority().equals("ROLE_DIRECTOR")) {
                redirectStrategy.sendRedirect(request, response, "/director");
                log.info("director " + authentication.getName() + " is logged in");
                break;
            } else if (authority.getAuthority().equals("ROLE_WORKER")) {
                redirectStrategy.sendRedirect(request, response, "/worker");
                log.info("worker " + authentication.getName() + " is logged in");
                break;
            } else {
                System.out.println("Role unknown!");
                throw new IllegalStateException("Role unknown");
            }
        }
    }
}