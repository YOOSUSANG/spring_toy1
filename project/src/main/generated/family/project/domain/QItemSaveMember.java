package family.project.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QItemSaveMember is a Querydsl query type for ItemSaveMember
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QItemSaveMember extends EntityPathBase<ItemSaveMember> {

    private static final long serialVersionUID = -600873387L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QItemSaveMember itemSaveMember = new QItemSaveMember("itemSaveMember");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QItem item;

    public final QMember member;

    public QItemSaveMember(String variable) {
        this(ItemSaveMember.class, forVariable(variable), INITS);
    }

    public QItemSaveMember(Path<? extends ItemSaveMember> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QItemSaveMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QItemSaveMember(PathMetadata metadata, PathInits inits) {
        this(ItemSaveMember.class, metadata, inits);
    }

    public QItemSaveMember(Class<? extends ItemSaveMember> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.item = inits.isInitialized("item") ? new QItem(forProperty("item"), inits.get("item")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
    }

}

