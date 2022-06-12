package bg.tu.varna.informationSystem.controller;

import bg.tu.varna.informationSystem.dto.users.UserRequestDto;
import bg.tu.varna.informationSystem.dto.users.UserResponseDto;
import bg.tu.varna.informationSystem.dto.users.UserStatusDto;
import bg.tu.varna.informationSystem.dto.users.UserUpdateDto;
import bg.tu.varna.informationSystem.service.UserService;
import bg.tu.varna.informationSystem.utils.ResourceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
//@PreAuthorize("hasAnyAuthority('add','view')")
public class UserController {

    private final UserService userService;
    private final ResourceValidator resourceValidator;

    @Autowired
    public UserController(UserService userService, ResourceValidator resourceValidator) {
        this.userService = userService;
        this.resourceValidator = resourceValidator;
    }

    @PostMapping
    public UserResponseDto save(@Valid @RequestBody UserRequestDto userRequestDto) {
        userRequestDto.getLibraryIds().forEach(resourceValidator::validateLibraryAccess);
        return userService.save(userRequestDto);
    }

    @PutMapping("{id}/status")
    public UserResponseDto updateStatus(@PathVariable Long id, @Valid @RequestBody UserStatusDto userStatusDto) {
        UserUpdateDto userUpdateDto = new UserUpdateDto();
        userUpdateDto.setStatus(userStatusDto.isActive());
        return userService.update(id, userUpdateDto);
    }
}
