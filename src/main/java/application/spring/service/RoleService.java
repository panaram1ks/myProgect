package application.spring.service;

import application.spring.dto.RoleDTO;
import application.spring.entity.Role;
import application.spring.repository.RoleRepository;

import java.util.List;

public interface RoleService extends Service<Integer, Role, RoleRepository> {
    List<RoleDTO> getAllRoles();
}