package family.project.domain.groceries;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QVitamin is a Querydsl query type for Vitamin
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVitamin extends EntityPathBase<Vitamin> {

    private static final long serialVersionUID = 1172251940L;

    public static final QVitamin vitamin = new QVitamin("vitamin");

    public final QHealthGroceries _super = new QHealthGroceries(this);

    //inherited
    public final ListPath<family.project.domain.CategoryItem, family.project.domain.QCategoryItem> categoryItems = _super.categoryItems;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final StringPath img = _super.img;

    //inherited
    public final StringPath name = _super.name;

    //inherited
    public final NumberPath<Integer> price = _super.price;

    //inherited
    public final StringPath shape = _super.shape;

    //inherited
    public final NumberPath<Integer> stockQuantity = _super.stockQuantity;

    //inherited
    public final StringPath supplementFunction = _super.supplementFunction;

    public QVitamin(String variable) {
        super(Vitamin.class, forVariable(variable));
    }

    public QVitamin(Path<? extends Vitamin> path) {
        super(path.getType(), path.getMetadata());
    }

    public QVitamin(PathMetadata metadata) {
        super(Vitamin.class, metadata);
    }

}

