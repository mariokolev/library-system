package bg.tu.varna.informationSystem.controller;

import bg.tu.varna.informationSystem.common.RoleTypes;
import bg.tu.varna.informationSystem.dto.users.UserResponseDto;
import bg.tu.varna.informationSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/readers")
public class ReaderController {
    private final UserService userService;

    @Autowired
    public ReaderController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('MANAGE_READERS')")
    public UserResponseDto find(@PathVariable Long id) {
        return userService.getById(id, RoleTypes.READER.toString());
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('MANAGE_READERS')")
    public List<UserResponseDto> findAll() {
        return userService.findAll(RoleTypes.READER.toString());
    }
}
