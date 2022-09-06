package bg.tu.varna.informationSystem.service;

import bg.tu.varna.informationSystem.common.Messages;
import bg.tu.varna.informationSystem.dto.role.RoleDto;
import bg.tu.varna.informationSystem.entity.Role;
import bg.tu.varna.informationSystem.exception.BadRequestException;
import bg.tu.varna.informationSystem.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public RoleService(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    public Role findRoleByName(String name) {
        return roleRepository.findRoleByName(name)
                .orElseThrow(() -> new BadRequestException(Messages.INVALID_ROLE));
    }

    public Role findRoleById(Long id) {
        return roleRepository.findRoleById(id)
                .orElseThrow(() -> new BadRequestException(Messages.INVALID_ROLE));
    }

    public List<RoleDto> findAll() {
        return roleRepository.findAll().stream().map(this::convertToRoleDto).collect(Collectors.toList());
    }

    private RoleDto convertToRoleDto(Role role) {
        RoleDto roleDto = modelMapper.map(role, RoleDto.class);
        roleDto.setRoleName(role.getName());
        return roleDto;
    }
}
