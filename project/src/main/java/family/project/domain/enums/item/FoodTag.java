package family.project.domain.enums.item;

import lombok.Getter;

@Getter
public enum FoodTag {

    KOREA("한식"),
    JAPAN("일식"),
    AMERICA("양식");

    private final String description;

    FoodTag(String description) {
        this.description = description;
    }


}
