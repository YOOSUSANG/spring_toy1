package family.project.domain.enums.item;

import lombok.Getter;

@Getter
public enum ItemTag {

    FOOD("음식"),
    KOREA("한식"),
    JAPAN("일식"),
    AMERICA("양식"),
    GROCERIES("식료품"),
    VITAMIN("비타민"),
    ELECTRONIC("전자제품"),
    KEYBOARD("키보드"),
    TABLET("테블릿");
    private final String description;

    ItemTag(String description) {
        this.description = description;
    }

}
