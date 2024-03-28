package family.project.domain.service;

import family.project.domain.*;
import family.project.domain.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TradeService {

    private final PurchasingRepository purchasingRepository;
    private final PurchaseRepository purchaseRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final TransactionHisRepository transactionHisRepository;

    @Transactional
    public void PurchasingRegister(Long itemId, Long memberId) {
        Item item = itemRepository.findById(itemId).orElse(null);
        Member member = memberRepository.findById(memberId).orElse(null);
        PurchasingItem purchasingItem = PurchasingItem.createPurchasingItem(item, member);
        purchasingRepository.save(purchasingItem);
    }

    @Transactional
    public void PurchasingSuccess(Long purchasingId) {
        PurchasingItem purchasingItem = purchasingRepository.findById(purchasingId).orElse(null);
        Item item = purchasingItem.getItem();
        Member member = purchasingItem.getMember();
        PurchaseItem purchaseItem = family.project.domain.PurchaseItem.createPurchaseItem(item, member);
        TransactionHistory transactionHistory = TransactionHistory.transactionHistory_create(purchaseItem, member);
        purchaseRepository.save(purchaseItem);
        transactionHisRepository.save(transactionHistory);
        purchasingRepository.deleteByItemId(item.getId());
    }


    public List<PurchasingItem> searchMyPurchasing(Long memberId) {
        return purchasingRepository.findAllByMemberId(memberId);
    }

    public List<PurchaseItem> searchAllPurchase(){
        return purchaseRepository.findAll();
    }
    public List<PurchaseItem> searchMyPurchase(Long memberId) {
        return purchaseRepository.findAllByMemberId(memberId);
    }
    public List<TransactionHistory> searchMyTransactionHistory(Long memberId){
        return transactionHisRepository.findAllByMemberId(memberId);

    }

    public List<PurchasingItem> confirmPurchasing(Long memberId) {
        List<PurchasingItem> all = purchasingRepository.findAll();
        List<PurchasingItem> result = all.stream().filter(pi -> pi.getItem() == itemRepository.findByIdAndMemberId(pi.getItem().getId(), memberId)).collect(Collectors.toList());
        return result;
    }

    @Transactional
    public void removePurchasing(Long purchasingId) {
        purchasingRepository.deleteById(purchasingId);
    }


    @Transactional
    public void removeTransactionHistory(Long historyId) {
        transactionHisRepository.deleteById(historyId);

    }

    @Transactional
    public void removePurchase(Long purchaseId) {
        purchaseRepository.deleteById(purchaseId);
    }

    @Transactional
    public void removeAllPurchasing(Long memberId) {
        purchasingRepository.deleteAllByMemberId(memberId);

    }
    @Transactional
    public void removeAllPurchase(Long memberId) {
        purchaseRepository.deleteAllByMemberId(memberId);
    }
}
