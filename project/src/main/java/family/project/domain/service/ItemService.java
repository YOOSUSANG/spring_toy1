package family.project.domain.service;

import family.project.domain.Item;
import family.project.domain.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ItemService {
    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Transactional
    public void register(Item item) {
        itemRepository.save(item);
    }

    public Item search(Long id) {
        return itemRepository.findById(id).orElse(null);
    }

    public List<Item> search() {
        return itemRepository.findAll();
    }

    @Transactional
    public void clear() {
        itemRepository.deleteAll();
    }
}
