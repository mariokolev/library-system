package bg.tu.varna.informationSystem.utils;

import bg.tu.varna.informationSystem.common.Messages;
import bg.tu.varna.informationSystem.common.RoleTypes;
import bg.tu.varna.informationSystem.entity.Library;
import bg.tu.varna.informationSystem.exception.BadRequestException;
import bg.tu.varna.informationSystem.security.UserPrincipal;
import bg.tu.varna.informationSystem.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ResourceValidator {

    private final LibraryService libraryService;

    @Autowired
    public ResourceValidator(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    public void validateLibraryAccess(Long libraryId) {
        UserPrincipal userPrincipal = UserPrincipalUtils.getPrincipalFromContext();

        if (userPrincipal.getRoleName().equals(RoleTypes.ADMIN.toString())) {
            return;
        }

        List<Library> libraries = libraryService.getLibrariesByUserId(userPrincipal.getId());
        if (libraries.isEmpty()) {
            throw new BadRequestException(Messages.NOT_ASSIGNED_TO_LIBRARY);
        }

        boolean none = libraries.stream().noneMatch(library -> library.getId().equals(libraryId));
        if (none) {
            throw new BadRequestException(Messages.ACCESS_DENIED);
        }
    }

    public void validateUserAccess(String roleName) {
        UserPrincipal userPrincipal = UserPrincipalUtils.getPrincipalFromContext();

        if (!userPrincipal.getRoleName().equals(RoleTypes.ADMIN.toString()) && !roleName.equals(RoleTypes.READER.toString())) {
            throw new BadRequestException("Don't have permissions to create " + roleName);
        }
    }
}
