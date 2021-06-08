package application.spring.service;

import application.spring.dto.UserDTO;
import application.spring.entity.User;
import application.spring.exception.UserAlreadyExistException;
import application.spring.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends Service<Integer, User, UserRepository>,
        UserDetailsService {
    User addUser(UserDTO user) throws UserAlreadyExistException;

    List<UserDTO> getAllUsers();

    List<UserDTO> getSearchUsers(String keyword, String post);

    UserDTO getUserById(Integer id);

    void editUser(UserDTO user);

    User createAdmin(UserDTO userDTO);

    boolean deleteUser(Integer id);

    default User getUser(String name) {
        return getRepository().findByName(name).orElse(null);
    }
}