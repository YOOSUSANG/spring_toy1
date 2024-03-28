package family.project.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -1003206139L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMember member = new QMember("member1");

    public final family.project.domain.mapped.QBasicEntity _super = new family.project.domain.mapped.QBasicEntity(this);

    public final QAddress address;

    public final ListPath<family.project.domain.board.Board, family.project.domain.board.QBoard> boards = this.<family.project.domain.board.Board, family.project.domain.board.QBoard>createList("boards", family.project.domain.board.Board.class, family.project.domain.board.QBoard.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath img = createString("img");

    public final QInterestItem interestItem;

    public final ListPath<Item, QItem> items = this.<Item, QItem>createList("items", Item.class, QItem.class, PathInits.DIRECT2);

    public final ListPath<ItemSaveMember, QItemSaveMember> ItemSaveMembers = this.<ItemSaveMember, QItemSaveMember>createList("ItemSaveMembers", ItemSaveMember.class, QItemSaveMember.class, PathInits.DIRECT2);

    public final EnumPath<family.project.domain.enums.MemberType> memberType = createEnum("memberType", family.project.domain.enums.MemberType.class);

    public final StringPath nickname = createString("nickname");

    public final ListPath<Order, QOrder> orders = this.<Order, QOrder>createList("orders", Order.class, QOrder.class, PathInits.DIRECT2);

    public final StringPath password = createString("password");

    public final ListPath<PurchaseItem, QPurchaseItem> purchaseItems = this.<PurchaseItem, QPurchaseItem>createList("purchaseItems", PurchaseItem.class, QPurchaseItem.class, PathInits.DIRECT2);

    public final ListPath<PurchasingItem, QPurchasingItem> purchasingItems = this.<PurchasingItem, QPurchasingItem>createList("purchasingItems", PurchasingItem.class, QPurchasingItem.class, PathInits.DIRECT2);

    public final EnumPath<family.project.domain.enums.RoleType> roleType = createEnum("roleType", family.project.domain.enums.RoleType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    public final StringPath username = createString("username");

    public QMember(String variable) {
        this(Member.class, forVariable(variable), INITS);
    }

    public QMember(Path<? extends Member> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMember(PathMetadata metadata, PathInits inits) {
        this(Member.class, metadata, inits);
    }

    public QMember(Class<? extends Member> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.address = inits.isInitialized("address") ? new QAddress(forProperty("address")) : null;
        this.interestItem = inits.isInitialized("interestItem") ? new QInterestItem(forProperty("interestItem"), inits.get("interestItem")) : null;
    }

}

