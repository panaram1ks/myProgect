package application.spring.service.impl;

import application.spring.dto.RoleDTO;
import application.spring.dto.UserDTO;
import application.spring.entity.Role;
import application.spring.entity.User;
import application.spring.exception.NotExistsException;
import application.spring.exception.UserAlreadyExistException;
import application.spring.repository.RoleRepository;
import application.spring.repository.UserRepository;
import application.spring.service.UserService;
import application.spring.service.mapper.RoleDTOMapper;
import application.spring.service.mapper.UserDTOMapper;
import com.google.common.collect.Collections2;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.google.common.collect.Sets.difference;
import static com.google.common.collect.Sets.newHashSet;
import static java.util.stream.Collectors.toSet;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepo;
    private RoleRepository roleRepo;
    private UserDTOMapper userMapper;
    private RoleDTOMapper roleMapper;
    private BCryptPasswordEncoder passwordEncoder;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    public UserServiceImpl(@Lazy BCryptPasswordEncoder passwordEncoder,
                           UserRepository userRepo, RoleRepository roleRepo,
                           UserDTOMapper userMapper, RoleDTOMapper roleMapper) {
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
    }

    @Override
    public UserRepository getRepository() {
        return userRepo;
    }

    @Override
    public User addUser(UserDTO userDTO) throws UserAlreadyExistException {
        User user = getUser(userDTO.getName());
        if (user == null) {
            user = userMapper.toEntity(userDTO);
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            Collection<Role> roles = userDTO.getRoles().stream().map(r ->
                    roleRepo.findRoleByName(r.toString()).get()).collect(toSet());
            user.setRoles(roles);
            return userRepo.save(user);
        } else {
            throw new UserAlreadyExistException();
        }
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepo.findAll();
        List<UserDTO> usersDTO = users.stream().map(user -> userMapper.toDTO(user)).collect(Collectors.toList());
        return usersDTO;
    }

    @Override
    public List<UserDTO> getSearchUsers(String keyword, String post) {
        return null;
    }


    @Override
    @SneakyThrows
    public UserDTO getUserById(Integer id) {
        User user = userRepo.findById(id).orElseThrow(() ->
                new NotExistsException("Invalid user Id:" + id));
        return userMapper.toDTO(user);
    }

    @Override
    @SneakyThrows
    @Transactional
    public void editUser(UserDTO userDTO) {
        User oldUser = getById(userDTO.getId());//берем из бд по id
        if (oldUser != null) {
            User newUser = userMapper.toEntity(userDTO, userDTO.getRoles());
           if (!oldUser.getPassword().equals(newUser.getPassword())) {
               String pass = passwordEncoder.encode(userDTO.getPassword());
               newUser.setPassword(pass);
            }
            if (userDTO.getRoles() != null && !userDTO.getRoles().isEmpty()) {
                val r1 = Collections2.transform(oldUser.getRoles(), u -> u.getName());
                val r2 = Collections2.transform(userDTO.getRoles(), u -> u.toString());
                if (!difference(newHashSet(r1), newHashSet(r2)).isEmpty()
                        || !difference(newHashSet(r2), newHashSet(r1)).isEmpty()) {
                    oldUser.setRoles(null);
                    userRepo.save(oldUser);
                    newUser.setRoles(roleRepo.findByNameIn(r2));
                } else {
                    newUser.setRoles(oldUser.getRoles());
                }
                userRepo.save(newUser);
            } else throw new NotExistsException("Invalid user Id:" + userDTO.getId());
        }
    }

    @Override
    public User createAdmin(UserDTO userDTO) {
        User user = userRepo.save(userMapper.toEntity(userDTO));
        Collection<Role> roles;
        List<Role> rls = roleRepo.findAll();
        if (rls != null && !rls.isEmpty()) {
            Optional<Role> roleOptional = roleRepo.findRoleByName("ROLE_ADMIN");
            if (!roleOptional.isPresent()) {
                rls.add(roleMapper.toEntity(RoleDTO.ROLE_ADMIN));
            }
            roles = newHashSet(rls);
        } else {
            roles = newHashSet(
                    new Role("ROLE_ADMIN"),
                    new Role("ROLE_ENGINEER"),
                    new Role("ROLE_DIRECTOR"),
                    new Role("ROLE_WORKER")
            );
        }
        user.setRoles(roles);
        return userRepo.save(user);
    }

    @Override
    public boolean deleteUser(Integer id) {
        return delete(id) ? true : false;
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(toSet());
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = getUser(name);
        if (user == null) {
            System.out.println("Invalid user name or password");
            throw new UsernameNotFoundException("Invalid user name or password");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles())
        );
    }
}