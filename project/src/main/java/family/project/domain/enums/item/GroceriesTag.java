package family.project.domain.enums.item;

import lombok.Getter;

@Getter
public enum GroceriesTag {

    VITAMIN("비타민");

    private final String description;

    GroceriesTag(String description) {
        this.description = description;
    }

}
