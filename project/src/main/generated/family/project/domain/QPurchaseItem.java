package family.project.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPurchaseItem is a Querydsl query type for PurchaseItem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPurchaseItem extends EntityPathBase<PurchaseItem> {

    private static final long serialVersionUID = -1607414049L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPurchaseItem purchaseItem = new QPurchaseItem("purchaseItem");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QItem item;

    public final QMember member;

    public final QTransactionHistory transactionHistory;

    public QPurchaseItem(String variable) {
        this(PurchaseItem.class, forVariable(variable), INITS);
    }

    public QPurchaseItem(Path<? extends PurchaseItem> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPurchaseItem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPurchaseItem(PathMetadata metadata, PathInits inits) {
        this(PurchaseItem.class, metadata, inits);
    }

    public QPurchaseItem(Class<? extends PurchaseItem> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.item = inits.isInitialized("item") ? new QItem(forProperty("item"), inits.get("item")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
        this.transactionHistory = inits.isInitialized("transactionHistory") ? new QTransactionHistory(forProperty("transactionHistory"), inits.get("transactionHistory")) : null;
    }

}

