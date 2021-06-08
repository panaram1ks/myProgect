package application.spring.controllers;

import application.spring.entity.User;
import application.spring.exception.UserNotFoundException;
import application.spring.service.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Controller
public class LoginController {
    private UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @Value("${numbers}")
    private List<Integer> numbers;

    @ModelAttribute("numbers")
    public List<Integer> getNumbers() {
        return numbers;
    }


    @GetMapping(value = {"/", "/login"}) // login тут нужен для корректного логаута
    public String showForm() {
        return "login";
    }

    @SneakyThrows
    @GetMapping("cabinet")
    public String toCabinet(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        if (user.hasRole("ROLE_ADMIN")) {
            return "admin/admin";
        } else if (user.hasRole("ROLE_ENGINEER")) {
            return "engineer/engineer";
        } else if (user.hasRole("ROLE_DIRECTOR")) {
            return "director/director";
        } else if (user.hasRole("ROLE_WORKER")) {
            return "worker/worker";
        } else {
            throw new UserNotFoundException();
        }
    }

    @GetMapping("/admin")
    public String adminIndex(Principal principal, HttpSession session) {
        showUser(principal, session);
        return "redirect:cabinet";
    }

    @GetMapping("/engineer")
    public String engineerIndex(Principal principal, HttpSession session) {
        showUser(principal, session);
        return "redirect:/cabinet";
    }

    @GetMapping("/director")
    public String directorIndex(Principal principal, HttpSession session) {
        showUser(principal, session);
        return "redirect:/cabinet";
    }

    @GetMapping("/worker")
    public String workerIndex(Principal principal, HttpSession session) {
        showUser(principal, session);
        return "redirect:/cabinet";
    }

    protected void showUser(Principal principal, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = userService.getUser(principal.getName());
            session.setAttribute("user", user);
        }
    }
}