package family.project.domain.enums;

import lombok.Getter;

@Getter
public enum ItemCheckTag {

    RECENT("최신순"),
    LIKE("좋아요순"),
    LOW_PRICE("낮은 가격순"),
    HIGH_PRICE("높은 가격순");

    private final String description;
    ItemCheckTag(String description) {
        this.description = description;
    }
}
