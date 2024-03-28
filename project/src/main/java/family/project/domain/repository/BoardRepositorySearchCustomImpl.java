package family.project.domain.repository;

import com.querydsl.core.types.NullExpression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import family.project.domain.board.Board;
import family.project.domain.enums.BoardCheckTag;
import family.project.domain.enums.BoardTag;
import family.project.web.dto.community.BoardFilterDto;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

import static family.project.domain.QMember.member;
import static family.project.domain.board.QBoard.board;

@Component
public class BoardRepositorySearchCustomImpl implements BoardRepositorySearchCustom {


    EntityManager em;
    JPAQueryFactory query;

    @Autowired
    public BoardRepositorySearchCustomImpl(EntityManager em) {
        this.em = em;
        query = new JPAQueryFactory(em);
    }

    @Override
    public Page<Board> searchTitleAndBoardTagAndCheckTag(BoardFilterDto boardFilterDto, Pageable pageable) {
        List<Board> content = query
                .selectFrom(board)
                .join(board.member, member)
                .where(titleEq(boardFilterDto.getTitle()), categoryEq(boardFilterDto.getCategoryTag()))
                .orderBy(checkTagOrder(boardFilterDto.getOrder()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = query
                .select(board.count())
                .from(board)
                .join(board.member, member)
                .where(titleEq(boardFilterDto.getTitle()), categoryEq(boardFilterDto.getCategoryTag()));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression titleEq(String title) {
        return StringUtils.hasText(title) ? board.title.contains(title) : null;
    }

    private BooleanExpression categoryEq(BoardTag boardTag) {
        return boardTag != null ? board.boardTag.eq(boardTag) : null;

    }

    private OrderSpecifier<?> checkTagOrder(BoardCheckTag boardCheckTag) {
        Order order = Order.DESC;
        if (boardCheckTag == BoardCheckTag.RECENT) {
            return new OrderSpecifier<>(order, board.createDate);
        }
        if (boardCheckTag == BoardCheckTag.VIEW) {
            return new OrderSpecifier<>(order, board.views);

        }
        if (boardCheckTag == BoardCheckTag.COMMENT) {
            return new OrderSpecifier<>(order, board.commentsCount);

        }
        if (boardCheckTag == BoardCheckTag.LIKE) {
            return new OrderSpecifier<>(order, board.likesCount);
        }
        return new OrderSpecifier(Order.ASC, NullExpression.DEFAULT, OrderSpecifier.NullHandling.Default);
    }
}
