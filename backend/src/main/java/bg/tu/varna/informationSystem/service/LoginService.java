package bg.tu.varna.informationSystem.service;

import bg.tu.varna.informationSystem.common.Messages;
import bg.tu.varna.informationSystem.dto.LoginRequestDTO;
import bg.tu.varna.informationSystem.dto.LoginResponseDTO;
import bg.tu.varna.informationSystem.entity.Role;
import bg.tu.varna.informationSystem.entity.User;
import bg.tu.varna.informationSystem.exception.BadRequestException;
import bg.tu.varna.informationSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final RoleService roleService;

    @Autowired
    public LoginService(UserRepository userRepository,
                        PasswordEncoder passwordEncoder,
                        TokenService tokenService,
                        RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.roleService = roleService;
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        User user = userRepository.findByUsername(loginRequestDTO.getUsername())
                .orElseThrow(() -> new BadRequestException(Messages.INVALID_USERNAME_OR_PASSWORD));

        if (!user.getIsActive()) {
            throw new BadRequestException(Messages.ACCOUNT_DISABLED);
        }
        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())) {
            throw new BadRequestException(Messages.INVALID_USERNAME_OR_PASSWORD);
        }

        List<String> permissions = userRepository.getUserPermissions(user.getUsername());
        Role role = roleService.findRoleById(user.getRoleId());
        return tokenService.generateToken(user, permissions, role.getName());
    }
}
