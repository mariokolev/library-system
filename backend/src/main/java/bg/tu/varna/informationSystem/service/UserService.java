package bg.tu.varna.informationSystem.service;

import bg.tu.varna.informationSystem.common.Messages;
import bg.tu.varna.informationSystem.common.RoleTypes;
import bg.tu.varna.informationSystem.dto.libraries.LibraryResponseDto;
import bg.tu.varna.informationSystem.dto.users.UserRequestDto;
import bg.tu.varna.informationSystem.dto.users.UserResponseDto;
import bg.tu.varna.informationSystem.dto.users.UserUpdateDto;
import bg.tu.varna.informationSystem.entity.Role;
import bg.tu.varna.informationSystem.entity.User;
import bg.tu.varna.informationSystem.exception.BadRequestException;
import bg.tu.varna.informationSystem.repository.UserRepository;
import bg.tu.varna.informationSystem.security.UserPrincipal;
import bg.tu.varna.informationSystem.utils.UserPrincipalUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final RoleService roleService;
    private final LibraryService libraryService;

    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       ModelMapper modelMapper,
                       RoleService roleService,
                       LibraryService libraryService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.roleService = roleService;
        this.libraryService = libraryService;
    }

    @Transactional
    public UserResponseDto save(UserRequestDto userRequestDto) {
        UserPrincipal userPrincipal = UserPrincipalUtils.getPrincipalFromContext();

        if (!userPrincipal.getRoleName().equals(RoleTypes.ADMIN.toString()) && !userRequestDto.getRoleName().equals(RoleTypes.READER.toString())) {
            throw new BadRequestException("Don't have permissions to create " + userRequestDto.getRoleName());
        }

        Boolean ifExists = userRepository.existsByUsername(userRequestDto.getUsername());

        if (ifExists) {
            throw new BadRequestException(Messages.USERNAME_IS_TAKEN);
        }

        User user = convertToEntity(userRequestDto);
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        user.setIsActive(true);
        User savedUser = userRepository.save(user);
        List<LibraryResponseDto> libraries = libraryService.findByIds(userRequestDto.getLibraryIds());

        if (libraries.isEmpty()) {
            throw new BadRequestException(Messages.LIBRARY_NOT_FOUND);
        }

        List<Long> ids = libraries.stream().map(LibraryResponseDto::getId).collect(Collectors.toList());
        libraryService.assignUserToLibraries(user.getId(), ids);

        return convertToResponseDto(savedUser);
    }

    @Transactional
    public UserResponseDto update(Long id, UserUpdateDto userUpdateDto) {
        User user = findById(id);

        if (user.getIsActive() != userUpdateDto.getStatus()) {
            user.setIsActive(userUpdateDto.getStatus());
        }

        return convertToResponseDto(userRepository.save(user));
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException(Messages.ACCOUNT_NOT_FOUND));
    }

    public User findById(Long userId, String roleName) {
        return userRepository.findById(userId, roleName)
                .orElseThrow(() -> new BadRequestException(Messages.ACCOUNT_NOT_FOUND));
    }

    public UserResponseDto getById(Long userId) {
        return convertToResponseDto(findById(userId));
    }

    public UserResponseDto getById(Long userId, String roleName) {
        return convertToResponseDto(findById(userId, roleName));
    }

    public List<UserResponseDto> findAll(String roleName) {
        return userRepository.findAllByRole(roleName).stream().map(this::convertToResponseDto).collect(Collectors.toList());
    }

    private UserResponseDto convertToResponseDto(User user) {
        UserResponseDto responseDto = modelMapper.map(user, UserResponseDto.class);
        Role role = roleService.findRoleById(user.getRoleId());
        responseDto.setRoleName(role.getName());
        responseDto.setDateAdded(user.getDateAdded().toString());
        return responseDto;
    }

    private User convertToEntity(UserRequestDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        Role role = roleService.findRoleByName(userDto.getRoleName());
        user.setRoleId(role.getId());
        return user;
    }
}
