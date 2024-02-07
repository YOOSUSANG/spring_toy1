package family.project.domain.board;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoard is a Querydsl query type for Board
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoard extends EntityPathBase<Board> {

    private static final long serialVersionUID = -1294526125L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBoard board = new QBoard("board");

    public final family.project.domain.mapped.QBasicEntity _super = new family.project.domain.mapped.QBasicEntity(this);

    public final family.project.domain.QBoardCategory boardCategory;

    public final EnumPath<family.project.domain.enums.BoardTag> boardTag = createEnum("boardTag", family.project.domain.enums.BoardTag.class);

    public final NumberPath<Integer> commentsCount = createNumber("commentsCount", Integer.class);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<String, StringPath> imgs = this.<String, StringPath>createList("imgs", String.class, StringPath.class, PathInits.DIRECT2);

    public final NumberPath<Integer> likesCount = createNumber("likesCount", Integer.class);

    public final family.project.domain.QMember member;

    public final BooleanPath publicIsPrivate = createBoolean("publicIsPrivate");

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    public final NumberPath<Integer> views = createNumber("views", Integer.class);

    public QBoard(String variable) {
        this(Board.class, forVariable(variable), INITS);
    }

    public QBoard(Path<? extends Board> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBoard(PathMetadata metadata, PathInits inits) {
        this(Board.class, metadata, inits);
    }

    public QBoard(Class<? extends Board> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.boardCategory = inits.isInitialized("boardCategory") ? new family.project.domain.QBoardCategory(forProperty("boardCategory"), inits.get("boardCategory")) : null;
        this.member = inits.isInitialized("member") ? new family.project.domain.QMember(forProperty("member"), inits.get("member")) : null;
    }

}

