package family.project.domain.product;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QKeyBoard is a Querydsl query type for KeyBoard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QKeyBoard extends EntityPathBase<KeyBoard> {

    private static final long serialVersionUID = 1452514193L;

    public static final QKeyBoard keyBoard = new QKeyBoard("keyBoard");

    public final QElectronic _super = new QElectronic(this);

    //inherited
    public final ListPath<family.project.domain.CategoryItem, family.project.domain.QCategoryItem> categoryItems = _super.categoryItems;

    public final StringPath connectMethod = createString("connectMethod");

    //inherited
    public final StringPath country = _super.country;

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

    //inherited
    public final StringPath releaseDate = _super.releaseDate;

    public final StringPath shape = createString("shape");

    //inherited
    public final NumberPath<Integer> stockQuantity = _super.stockQuantity;

    public final StringPath switchType = createString("switchType");

    public QKeyBoard(String variable) {
        super(KeyBoard.class, forVariable(variable));
    }

    public QKeyBoard(Path<? extends KeyBoard> path) {
        super(path.getType(), path.getMetadata());
    }

    public QKeyBoard(PathMetadata metadata) {
        super(KeyBoard.class, metadata);
    }

}

