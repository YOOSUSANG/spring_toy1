package family.project.domain.groceries;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHealthGroceries is a Querydsl query type for HealthGroceries
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHealthGroceries extends EntityPathBase<HealthGroceries> {

    private static final long serialVersionUID = 1280578721L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHealthGroceries healthGroceries = new QHealthGroceries("healthGroceries");

    public final family.project.domain.QItem _super;

    //inherited
    public final ListPath<family.project.domain.CategoryItem, family.project.domain.QCategoryItem> categoryItems;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate;

    //inherited
    public final NumberPath<Long> id;

    //inherited
    public final ListPath<family.project.domain.file.UploadFile, family.project.domain.file.QUploadFile> imageFiles;

    //inherited
    public final StringPath information;

    //inherited
    public final EnumPath<family.project.domain.enums.item.ItemTag> itemTag;

    //inherited
    public final NumberPath<Integer> likesCount;

    // inherited
    public final family.project.domain.QMember member;

    //inherited
    public final StringPath name;

    //inherited
    public final NumberPath<Integer> price;

    //inherited
    public final NumberPath<Integer> stockQuantity;

    //inherited
    public final StringPath tradeRegion;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate;

    public QHealthGroceries(String variable) {
        this(HealthGroceries.class, forVariable(variable), INITS);
    }

    public QHealthGroceries(Path<? extends HealthGroceries> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHealthGroceries(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHealthGroceries(PathMetadata metadata, PathInits inits) {
        this(HealthGroceries.class, metadata, inits);
    }

    public QHealthGroceries(Class<? extends HealthGroceries> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new family.project.domain.QItem(type, metadata, inits);
        this.categoryItems = _super.categoryItems;
        this.createDate = _super.createDate;
        this.id = _super.id;
        this.imageFiles = _super.imageFiles;
        this.information = _super.information;
        this.itemTag = _super.itemTag;
        this.likesCount = _super.likesCount;
        this.member = _super.member;
        this.name = _super.name;
        this.price = _super.price;
        this.stockQuantity = _super.stockQuantity;
        this.tradeRegion = _super.tradeRegion;
        this.updateDate = _super.updateDate;
    }

}

