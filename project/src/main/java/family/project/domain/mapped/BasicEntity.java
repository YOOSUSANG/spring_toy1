package family.project.domain.mapped;


import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
public abstract class BasicEntity {

    //생성일은 update 막기
    @Column(updatable = false)
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    //JPA persist 되기 전에 발생하는 메소드 --> JPA 기술이다.
    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        createDate = now;
        updateDate = now;
    }


    @PreUpdate
    public void preUpdate() {
        updateDate = LocalDateTime.now();
    }
}
