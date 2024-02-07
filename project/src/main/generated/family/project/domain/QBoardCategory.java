package family.project.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoardCategory is a Querydsl query type for BoardCategory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoardCategory extends EntityPathBase<BoardCategory> {

    private static final long serialVersionUID = 2133519417L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBoardCategory boardCategory = new QBoardCategory("boardCategory");

    public final ListPath<family.project.domain.board.Board, family.project.domain.board.QBoard> boards = this.<family.project.domain.board.Board, family.project.domain.board.QBoard>createList("boards", family.project.domain.board.Board.class, family.project.domain.board.QBoard.class, PathInits.DIRECT2);

    public final EnumPath<family.project.domain.enums.CategoryTag> categoryTag = createEnum("categoryTag", family.project.domain.enums.CategoryTag.class);

    public final ListPath<BoardCategory, QBoardCategory> child = this.<BoardCategory, QBoardCategory>createList("child", BoardCategory.class, QBoardCategory.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QBoardCategory parent;

    public QBoardCategory(String variable) {
        this(BoardCategory.class, forVariable(variable), INITS);
    }

    public QBoardCategory(Path<? extends BoardCategory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBoardCategory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBoardCategory(PathMetadata metadata, PathInits inits) {
        this(BoardCategory.class, metadata, inits);
    }

    public QBoardCategory(Class<? extends BoardCategory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.parent = inits.isInitialized("parent") ? new QBoardCategory(forProperty("parent"), inits.get("parent")) : null;
    }

}

