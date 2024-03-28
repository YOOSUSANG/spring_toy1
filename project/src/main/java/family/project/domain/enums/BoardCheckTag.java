package family.project.domain.enums;


import lombok.Getter;

@Getter
public enum BoardCheckTag {
    RECENT("최신순"),
    VIEW("조회수"),
    COMMENT("답변많은순"),
    LIKE("좋아요순");

    private final String description;

    BoardCheckTag(String description) {
        this.description = description;
    }
}
