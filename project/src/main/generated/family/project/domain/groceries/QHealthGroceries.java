package family.project.domain.groceries;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QHealthGroceries is a Querydsl query type for HealthGroceries
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHealthGroceries extends EntityPathBase<HealthGroceries> {

    private static final long serialVersionUID = 1280578721L;

    public static final QHealthGroceries healthGroceries = new QHealthGroceries("healthGroceries");

    public final family.project.domain.QItem _super = new family.project.domain.QItem(this);

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

    public final StringPath shape = createString("shape");

    //inherited
    public final NumberPath<Integer> stockQuantity = _super.stockQuantity;

    public final StringPath supplementFunction = createString("supplementFunction");

    public QHealthGroceries(String variable) {
        super(HealthGroceries.class, forVariable(variable));
    }

    public QHealthGroceries(Path<? extends HealthGroceries> path) {
        super(path.getType(), path.getMetadata());
    }

    public QHealthGroceries(PathMetadata metadata) {
        super(HealthGroceries.class, metadata);
    }

}

