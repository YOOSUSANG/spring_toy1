package family.project.domain.enums;


import lombok.Getter;

@Getter
public enum MemberType {
    DAD("아빠"), MOM("엄마"), SISTER("누나"), OlD_BROTHER("형"), BROTHER("동생");

    private final String description;

    MemberType(String description) {
        this.description = description;
    }


}
