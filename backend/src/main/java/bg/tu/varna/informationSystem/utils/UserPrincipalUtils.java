package bg.tu.varna.informationSystem.utils;

import bg.tu.varna.informationSystem.security.UserPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserPrincipalUtils {

    public static UserPrincipal getPrincipalFromContext() {
        return (UserPrincipal) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }
}
