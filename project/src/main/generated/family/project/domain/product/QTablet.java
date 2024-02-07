package family.project.domain.product;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTablet is a Querydsl query type for Tablet
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTablet extends EntityPathBase<Tablet> {

    private static final long serialVersionUID = -241254224L;

    public static final QTablet tablet = new QTablet("tablet");

    public final QElectronic _super = new QElectronic(this);

    //inherited
    public final ListPath<family.project.domain.CategoryItem, family.project.domain.QCategoryItem> categoryItems = _super.categoryItems;

    public final StringPath core = createString("core");

    //inherited
    public final StringPath country = _super.country;

    public final StringPath display = createString("display");

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final StringPath img = _super.img;

    //inherited
    public final StringPath manufacturer = _super.manufacturer;

    //inherited
    public final StringPath name = _super.name;

    //inherited
    public final NumberPath<Integer> price = _super.price;

    public final StringPath ram = createString("ram");

    //inherited
    public final StringPath releaseDate = _super.releaseDate;

    //inherited
    public final NumberPath<Integer> stockQuantity = _super.stockQuantity;

    public QTablet(String variable) {
        super(Tablet.class, forVariable(variable));
    }

    public QTablet(Path<? extends Tablet> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTablet(PathMetadata metadata) {
        super(Tablet.class, metadata);
    }

}

