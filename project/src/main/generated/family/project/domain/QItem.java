package family.project.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QItem is a Querydsl query type for Item
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QItem extends EntityPathBase<Item> {

    private static final long serialVersionUID = 173152574L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QItem item = new QItem("item");

    public final family.project.domain.mapped.QBasicEntity _super = new family.project.domain.mapped.QBasicEntity(this);

    public final ListPath<CategoryItem, QCategoryItem> categoryItems = this.<CategoryItem, QCategoryItem>createList("categoryItems", CategoryItem.class, QCategoryItem.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<family.project.domain.file.UploadFile, family.project.domain.file.QUploadFile> imageFiles = this.<family.project.domain.file.UploadFile, family.project.domain.file.QUploadFile>createList("imageFiles", family.project.domain.file.UploadFile.class, family.project.domain.file.QUploadFile.class, PathInits.DIRECT2);

    public final StringPath information = createString("information");

    public final EnumPath<family.project.domain.enums.item.ItemTag> itemTag = createEnum("itemTag", family.project.domain.enums.item.ItemTag.class);

    public final NumberPath<Integer> likesCount = createNumber("likesCount", Integer.class);

    public final QMember member;

    public final StringPath name = createString("name");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final NumberPath<Integer> stockQuantity = createNumber("stockQuantity", Integer.class);

    public final StringPath tradeRegion = createString("tradeRegion");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    public QItem(String variable) {
        this(Item.class, forVariable(variable), INITS);
    }

    public QItem(Path<? extends Item> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QItem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QItem(PathMetadata metadata, PathInits inits) {
        this(Item.class, metadata, inits);
    }

    public QItem(Class<? extends Item> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
    }

}

