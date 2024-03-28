package family.project.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QInterestItem is a Querydsl query type for InterestItem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInterestItem extends EntityPathBase<InterestItem> {

    private static final long serialVersionUID = 1281897608L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QInterestItem interestItem = new QInterestItem("interestItem");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<Item, QItem> items = this.<Item, QItem>createList("items", Item.class, QItem.class, PathInits.DIRECT2);

    public final QMember member;

    public QInterestItem(String variable) {
        this(InterestItem.class, forVariable(variable), INITS);
    }

    public QInterestItem(Path<? extends InterestItem> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QInterestItem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QInterestItem(PathMetadata metadata, PathInits inits) {
        this(InterestItem.class, metadata, inits);
    }

    public QInterestItem(Class<? extends InterestItem> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
    }

}

