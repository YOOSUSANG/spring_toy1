package family.project.domain.mapped;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBasicEntity is a Querydsl query type for BasicEntity
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QBasicEntity extends EntityPathBase<BasicEntity> {

    private static final long serialVersionUID = -308351711L;

    public static final QBasicEntity basicEntity = new QBasicEntity("basicEntity");

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> updateDate = createDateTime("updateDate", java.time.LocalDateTime.class);

    public QBasicEntity(String variable) {
        super(BasicEntity.class, forVariable(variable));
    }

    public QBasicEntity(Path<? extends BasicEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBasicEntity(PathMetadata metadata) {
        super(BasicEntity.class, metadata);
    }

}

