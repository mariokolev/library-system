package bg.tu.varna.informationSystem.dto.users;

import javax.validation.constraints.NotNull;

public class UserStatusDto {

    @NotNull
    private Boolean isActive;

    public Boolean isActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}
