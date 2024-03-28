package family.project.domain.service;

import family.project.domain.Item;
import family.project.domain.ItemSaveMember;
import family.project.domain.Member;
import family.project.domain.enums.item.ItemTag;
import family.project.domain.file.FileStore;
import family.project.domain.file.UploadFile;
import family.project.domain.food.Food;
import family.project.domain.groceries.HealthGroceries;
import family.project.domain.groceries.Vitamin;
import family.project.domain.product.Electronic;
import family.project.domain.product.KeyBoard;
import family.project.domain.product.Tablet;
import family.project.domain.repository.ItemRepository;
import family.project.domain.repository.MemberRepository;
import family.project.web.dto.item.ItemFilterDto;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final FileStore fileStore;


    @Transactional
    public void register(Item item) {
        itemRepository.save(item);
    }

    public Item search(Long id) {
        return itemRepository.findById(id).orElse(null);
    }

    public List<Item> itemsAll() {
        return itemRepository.findAll();
    }

    public Page<Item> search_direct(ItemFilterDto itemFilterDto, Pageable pageable) {
        return itemRepository.searchItem(itemFilterDto, pageable);

    }

    @Transactional
    public void register_new(Long memberId, String name, ItemTag itemTag, Integer price, String content, String tradePlace, List<MultipartFile> imageFiles) throws IOException {
        Member member = memberRepository.findById(memberId).orElse(null);
        List<UploadFile> imgFiles = fileStore.storeFiles(imageFiles);
        Item item = null;
        if (itemTag == ItemTag.KOREA || itemTag == ItemTag.JAPAN || itemTag == ItemTag.AMERICA) {
            item = Food.createFood(name, price, 1, 0, itemTag, content, tradePlace, imgFiles);
            item.addMember(member);
        }
        if (itemTag == ItemTag.VITAMIN) {
            item = Vitamin.createVitamin(name, price, 1, itemTag, content, tradePlace, imgFiles);
            item.addMember(member);
        }
        if (itemTag == ItemTag.KEYBOARD) {
            item = KeyBoard.createKeyBoard(name, price, 1, itemTag, content, tradePlace, imgFiles);
            item.addMember(member);
        }
        if (itemTag == ItemTag.TABLET) {
            item = Tablet.createTablet(name, price, 1, itemTag, content, tradePlace, imgFiles);
            item.addMember(member);
        }
        itemRepository.save(item);
    }


    public Item searchMyItem(Long itemId, Long memberId) {
        return itemRepository.findByIdAndMemberId(itemId, memberId);
    }

    public List<Item> searchMyItemAll(Long memberId) {
        return itemRepository.findAllByMemberId(memberId);
    }

    //같은 트랜젝션에서 처리하므로 더티체킹이 된다. -> 데이터 값 변경은 @Transactional 필요
    //영속상태에서 변경해주면 더티체킹이 된다.
    @Transactional
    public void editItem(Long itemId, Long memberId, String name, Integer price, ItemTag itemTag, String content, String tradeRegion, List<MultipartFile> imageFiles) throws IOException {
        Item editItem = itemRepository.findByIdAndMemberId(itemId, memberId);
        List<UploadFile> uploadFiles = fileStore.storeFiles(imageFiles);
        editItem.changeAll(name, price, itemTag, content, tradeRegion, uploadFiles);

    }

    @Transactional
    public void interestedItemCancel(Long memberId, Long itemId) {
        Member member = memberRepository.findById(memberId).orElse(null);
        member.ItemCancel(itemId);
    }

    @Transactional
    public void interestedItem(Long memberId, Long itemId) {
        Member member = memberRepository.findById(memberId).orElse(null);
        Item item = itemRepository.findById(itemId).orElse(null);
        ItemSaveMember itemSaveMember = ItemSaveMember.createItemSaveMember(member, item);
        memberRepository.save(member);
    }

    @Transactional
    public void removeItem(Long id) {
        itemRepository.deleteById(id);
    }

    @Transactional
    public void clear() {
        itemRepository.deleteAll();
    }
}
