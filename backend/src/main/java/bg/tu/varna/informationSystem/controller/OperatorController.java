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
@RequestMapping("/api/v1/operators")
public class OperatorController {

    private final UserService userService;

    @Autowired
    public OperatorController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('MANAGE_USERS')")
    public UserResponseDto find(@PathVariable Long id) {
        return userService.getById(id, RoleTypes.OPERATOR.toString());
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('MANAGE_USERS')")
    public List<UserResponseDto> findAll() {
        return userService.findAll(RoleTypes.OPERATOR.toString());
    }
}
