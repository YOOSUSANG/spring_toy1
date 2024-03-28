package family.project.domain.repository;

import com.querydsl.core.types.NullExpression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import family.project.domain.Item;
import family.project.domain.enums.BoardCheckTag;
import family.project.domain.enums.ItemCheckTag;
import family.project.domain.enums.item.ItemTag;
import family.project.web.dto.item.ItemFilterDto;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

import static family.project.domain.QItem.*;

@Component
public class ItemRepositorySearchCustomImpl implements ItemRepositorySearchCustom {

    private final EntityManager em;
    private final JPAQueryFactory query;

    @Autowired
    public ItemRepositorySearchCustomImpl(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }


    @Override
    public Page<Item> searchItem(ItemFilterDto itemFilterDto, Pageable pageable) {

        List<Item> content = query
                .selectFrom(item)
                .where(nameEq(itemFilterDto.getTitle()), categoryEq(itemFilterDto.getCategoryTag()))
                .orderBy(orderEq(itemFilterDto.getOrder()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()).fetch();

        JPAQuery<Long> countQuery = query
                .select(item.count())
                .from(item)
                .where(nameEq(itemFilterDto.getTitle()), categoryEq(itemFilterDto.getCategoryTag()));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private OrderSpecifier<?> orderEq(ItemCheckTag itemCheckTag) {
        Order desc = Order.DESC;
        Order asc = Order.ASC;
        if (itemCheckTag == ItemCheckTag.RECENT) {
            return new OrderSpecifier<>(desc, item.createDate);
        }
        if (itemCheckTag == ItemCheckTag.LIKE) {
            return new OrderSpecifier<>(desc, item.likesCount);
        }
        if (itemCheckTag == ItemCheckTag.LOW_PRICE) {
            return new OrderSpecifier<>(asc, item.price);
        }
        if (itemCheckTag == ItemCheckTag.HIGH_PRICE) {
            return new OrderSpecifier<>(desc, item.price);
        }
        return new OrderSpecifier(asc, NullExpression.DEFAULT, OrderSpecifier.NullHandling.Default);
    }

    private BooleanExpression categoryEq(ItemTag itemTag) {
        return itemTag != null ? item.itemTag.eq(itemTag) : null;
    }

    private BooleanExpression nameEq(String name) {
        return StringUtils.hasText(name) ? item.name.contains(name) : null;
    }

}
