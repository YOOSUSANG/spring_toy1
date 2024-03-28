package family.project.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTransactionHistory is a Querydsl query type for TransactionHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTransactionHistory extends EntityPathBase<TransactionHistory> {

    private static final long serialVersionUID = 1752910529L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTransactionHistory transactionHistory = new QTransactionHistory("transactionHistory");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    public final QPurchaseItem purchaseItem;

    public QTransactionHistory(String variable) {
        this(TransactionHistory.class, forVariable(variable), INITS);
    }

    public QTransactionHistory(Path<? extends TransactionHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTransactionHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTransactionHistory(PathMetadata metadata, PathInits inits) {
        this(TransactionHistory.class, metadata, inits);
    }

    public QTransactionHistory(Class<? extends TransactionHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
        this.purchaseItem = inits.isInitialized("purchaseItem") ? new QPurchaseItem(forProperty("purchaseItem"), inits.get("purchaseItem")) : null;
    }

}

