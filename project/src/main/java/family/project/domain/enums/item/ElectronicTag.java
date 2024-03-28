package family.project.domain.enums.item;


import lombok.Getter;

@Getter
public enum ElectronicTag {
    KEYBOARD("키보드"),
    TABLET("테블릿");
    private final String description;

    ElectronicTag(String description) {
        this.description = description;
    }
}
