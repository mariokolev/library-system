package bg.tu.varna.informationSystem.controller;

import bg.tu.varna.informationSystem.common.RoleTypes;
import bg.tu.varna.informationSystem.dto.users.UserRequestDto;
import bg.tu.varna.informationSystem.dto.users.UserResponseDto;
import bg.tu.varna.informationSystem.dto.users.UserStatusDto;
import bg.tu.varna.informationSystem.dto.users.UserUpdateDto;
import bg.tu.varna.informationSystem.security.UserPrincipal;
import bg.tu.varna.informationSystem.service.UserService;
import bg.tu.varna.informationSystem.utils.ResourceValidator;
import bg.tu.varna.informationSystem.utils.UserPrincipalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final ResourceValidator resourceValidator;

    @Autowired
    public UserController(UserService userService, ResourceValidator resourceValidator) {
        this.userService = userService;
        this.resourceValidator = resourceValidator;
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('MANAGE_USERS','MANAGE_READERS')")
    public UserResponseDto save(@Valid @RequestBody UserRequestDto userRequestDto) {
        userRequestDto.getLibraryIds().forEach(resourceValidator::validateLibraryAccess);
        return userService.save(userRequestDto);
    }

    @PutMapping("{id}/status")
    @PreAuthorize("hasAnyAuthority('MANAGE_READERS')")
    public UserResponseDto updateStatus(@PathVariable Long id, @Valid @RequestBody UserStatusDto userStatusDto) {
        UserUpdateDto userUpdateDto = new UserUpdateDto();
        userUpdateDto.setStatus(userStatusDto.isActive());
        return userService.update(id, userUpdateDto);
    }

    @GetMapping
    public List<UserResponseDto> findAll() {
        UserPrincipal loggedUser = UserPrincipalUtils.getPrincipalFromContext();
        if (loggedUser.getRoleName().equals(RoleTypes.ADMIN.toString())) {
            return userService.findAll();
        }

        return userService.findAll(RoleTypes.READER.toString());
    }
}
