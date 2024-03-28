package family.project.domain.repository;


import family.project.domain.InterestItem;
import family.project.domain.Item;
import family.project.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InterestItemService {

    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final InterestItemRepository interestItemRepository;



    @Transactional
    public void new_register(Long memberId, Long itemId) {
        Member member = memberRepository.findById(memberId).orElse(null);
        Item item = itemRepository.findById(itemId).orElse(null);

        InterestItem interestItem = InterestItem.interestItem_create(member, item);
        interestItemRepository.save(interestItem);
    }

    @Transactional
    public void addInterestItem(Long memberId, Long itemId) {
        Member member = memberRepository.findById(memberId).orElse(null);
        Item item = itemRepository.findById(itemId).orElse(null);
        member.getInterestItem().addItem(item); //더티체킹
    }

    @Transactional
    public void removeInterestItem(Long memberId, Long itemId) {
        Member member = memberRepository.findById(memberId).orElse(null);
        Item item = itemRepository.findById(itemId).orElse(null);
        member.getInterestItem().getItems().remove(item);
    }

    @Transactional
    public void removeAllInterestItem(Long memberId) {
        Member member = memberRepository.findById(memberId).orElse(null);
        member.getInterestItem().getItems().clear();
    }

}
