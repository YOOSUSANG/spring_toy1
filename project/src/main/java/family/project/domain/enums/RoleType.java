package family.project.domain.enums;

import lombok.Getter;

@Getter
public enum RoleType {

    USER("유저"), MANAGER("매니저"), ADMIN("관리자");
    private String description;

    RoleType(String description) {
        this.description = description;
    }
}
