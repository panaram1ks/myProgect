package application.spring.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;

@Slf4j
@ControllerAdvice
@Component
public class GlobalExceptionHandler {
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handle404(Model model) {
        log.error(HttpStatus.NOT_FOUND.getReasonPhrase());
        model.addAttribute("status", HttpStatus.NOT_FOUND.getReasonPhrase());
        return "error/404error";
    }

    @ExceptionHandler({RuntimeException.class, IOException.class,
            ServletException.class, SQLException.class, NotExistsException.class,
            application.spring.exception.UserAlreadyExistException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handle500(Model model, Exception e) {
        log.error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e);
        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        return "error/500error";
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handle403(HttpServletRequest request, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            log.info(auth.getName()
                    + " was trying to access protected resource: "
                    + request.getRequestURI());
        }
        model.addAttribute("status", HttpStatus.UNAUTHORIZED.getReasonPhrase());
        return "error/403error";
    }
}
