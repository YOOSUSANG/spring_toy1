package family.project.domain.enums;

import lombok.Getter;

@Getter
public enum BoardTag {
    ALL("전체 게시판"),
    TRAVEL("여행 게시판"),
    DOMESTIC("국내 게시판"),
    OVERSEAS("해외 게시판"),
    TRAVEL_REVIEW("여행 리뷰 게시판"),
    TRAVEL_TIPS("여행 팁 게시판"),
    PREGNANCY("육아 게시판"),
    PREGNANCY_CHILDBIRTH("임신과 출산 게시판"),
    INFANT_CARE_PARENTING("유아 돌봄과 양육 게시판"),
    PARENTING_PRODUCT_REVIEW("육아 용품 리뷰 게시판"),
    PARENTING_TIPS("육아 팁 게시판"),
    FOOD("음식 게시판"),
    FOOD_RECOMMENDATIONS_REVIEWS("음식 추천과 리뷰 게시판"),
    RESTAURANTS_BY_REGION("지역별 맛집 게시판"),
    SHARE_COOKING_RECIPES("요리 레시피 게시판");
    private String description;

    BoardTag(String description) {
        this.description = description;
    }
}
