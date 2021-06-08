package application.spring.service.impl;

import application.spring.dto.RoleDTO;
import application.spring.repository.RoleRepository;
import application.spring.service.RoleService;
import application.spring.service.mapper.RoleDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.google.common.collect.Lists.transform;

@Service
public class RoleServiceImpl implements RoleService {
    private RoleRepository roleRepo;
    private RoleDTOMapper roleMapper;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepo, RoleDTOMapper roleMapper) {
        this.roleRepo = roleRepo;
        this.roleMapper = roleMapper;
    }

    @Override
    public RoleRepository getRepository() {
        return roleRepo;
    }

    @Override
    public List<RoleDTO> getAllRoles() {
        List<RoleDTO> roleDTOs = transform(getAll(), roleMapper::toDTO);
        return roleDTOs;
    }
}