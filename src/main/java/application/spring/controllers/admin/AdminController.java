package application.spring.controllers.admin;

import application.spring.dto.RoleDTO;
import application.spring.dto.UserDTO;
import application.spring.entity.User;
import application.spring.exception.UserAlreadyExistException;
import application.spring.exception.UserNotFoundException;
import application.spring.service.RoleService;
import application.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {
    private UserService userService;
    private RoleService roleService;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(UserService userService, RoleService roleService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @ModelAttribute("roles")
    public List<RoleDTO> initializeProfiles() {
        List<RoleDTO> roles = roleService.getAllRoles().stream().collect(toList());
        return roles;
    }

    @GetMapping("/allUsers")
    public String showUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/allUsers";
    }

//    @GetMapping("/deleteUser/{id}")
//    public String deleteUser(@PathVariable Integer id, HttpSession httpSession) {
//        userService.deleteUser(id);
//        return "redirect:/allUsers";
//    }

    @DeleteMapping(value = "/deleteUser/{id}")
    public String deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return "redirect:/allUsers";
    }

//    @RequestMapping(value = "/deleteUser/{id}", method = {RequestMethod.DELETE, RequestMethod.GET})
//        public String deleteUser(@PathVariable Integer id) {
//        userService.deleteUser(id);
//        return "redirect:/allUsers";
//    }


    @GetMapping("/createUser")
    public String createUser(Model model) {
        UserDTO userDTO = new UserDTO();
        model.addAttribute("userDTO", userDTO);
        return "admin/createUser";
    }

    @PostMapping("/register")
    public String registerUser(@Valid UserDTO userDTO, BindingResult bindingResult,
                               @RequestParam Optional<List<RoleDTO>> roles, Model model) {
        if (bindingResult.hasErrors()) {
            return "admin/createUser";
        } else {
            try {
                userService.addUser(userDTO);
                userDTO = new UserDTO();
                model.addAttribute("userDTO", userDTO);
                model.addAttribute("message", "ok");
                return "admin/createUser";
            } catch (UserAlreadyExistException e) {
                bindingResult.rejectValue("name", e.getMessage());
                return "admin/createUser";
            }
        }
    }

    @GetMapping("/editUser/{id}")
    public String editUser(@PathVariable Integer id, Model model) {
        UserDTO userDTO = userService.getUserById(id);
        model.addAttribute("userDTO", userDTO);
        return "admin/editUser";
    }

    // @PatchMapping("/update/{id}")
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @ModelAttribute UserDTO userDTO) {
        User user = userService.getById(id);
        if (user != null) {
            userService.editUser(userDTO);
        }
        return "redirect:/allUsers";
    }

}