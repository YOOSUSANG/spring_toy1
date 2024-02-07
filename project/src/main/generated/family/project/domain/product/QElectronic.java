package family.project.domain.product;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QElectronic is a Querydsl query type for Electronic
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QElectronic extends EntityPathBase<Electronic> {

    private static final long serialVersionUID = 298721222L;

    public static final QElectronic electronic = new QElectronic("electronic");

    public final family.project.domain.QItem _super = new family.project.domain.QItem(this);

    //inherited
    public final ListPath<family.project.domain.CategoryItem, family.project.domain.QCategoryItem> categoryItems = _super.categoryItems;

    public final StringPath country = createString("country");

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final StringPath img = _super.img;

    public final StringPath manufacturer = createString("manufacturer");

    //inherited
    public final StringPath name = _super.name;

    //inherited
    public final NumberPath<Integer> price = _super.price;

    public final StringPath releaseDate = createString("releaseDate");

    //inherited
    public final NumberPath<Integer> stockQuantity = _super.stockQuantity;

    public QElectronic(String variable) {
        super(Electronic.class, forVariable(variable));
    }

    public QElectronic(Path<? extends Electronic> path) {
        super(path.getType(), path.getMetadata());
    }

    public QElectronic(PathMetadata metadata) {
        super(Electronic.class, metadata);
    }

}

