package family.project.domain.repository;

import family.project.domain.Item;
import family.project.web.dto.item.ItemFilterDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;


@Repository
public interface ItemRepositorySearchCustom {


    Page<Item> searchItem(ItemFilterDto itemFilterDto, Pageable pageable);


}
