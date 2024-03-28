package family.project.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPurchasingItem is a Querydsl query type for PurchasingItem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPurchasingItem extends EntityPathBase<PurchasingItem> {

    private static final long serialVersionUID = 1764821532L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPurchasingItem purchasingItem = new QPurchasingItem("purchasingItem");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QItem item;

    public final QMember member;

    public QPurchasingItem(String variable) {
        this(PurchasingItem.class, forVariable(variable), INITS);
    }

    public QPurchasingItem(Path<? extends PurchasingItem> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPurchasingItem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPurchasingItem(PathMetadata metadata, PathInits inits) {
        this(PurchasingItem.class, metadata, inits);
    }

    public QPurchasingItem(Class<? extends PurchasingItem> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.item = inits.isInitialized("item") ? new QItem(forProperty("item"), inits.get("item")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
    }

}

