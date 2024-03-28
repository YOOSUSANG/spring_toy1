package family.project.web.controller;

import family.project.PageCustom;
import family.project.domain.*;
import family.project.domain.enums.ItemCheckTag;
import family.project.domain.enums.item.ItemTag;
import family.project.domain.file.FileStore;
import family.project.domain.security.PrincipalDetails;
import family.project.domain.service.ItemService;
import family.project.domain.service.MemberService;
import family.project.domain.service.TradeService;
import family.project.domain.statics.PageRange;
import family.project.web.dto.MemberFormDto;
import family.project.web.dto.item.*;
import family.project.web.dto.myInfo.MyInfoToHomeDto;
import family.project.web.dto.page.PageFormDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;
    private final MemberService memberService;
    private final TradeService tradeService;
    private final FileStore fileStore;

    @ModelAttribute("itemTags")
    private ItemTag[] itemTags() {
        return ItemTag.values();
    }

    @ModelAttribute("checkTags")
    private ItemCheckTag[] checkTags() {
        return ItemCheckTag.values();
    }


    @GetMapping("/new")
    public String itemRegister(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        model.addAttribute("item", new NewItemFormDto());
        model.addAttribute("principal", principalDetails);

        return "item/newItem";
    }

    @PostMapping("/new")
    public String itemRegister_post(@AuthenticationPrincipal PrincipalDetails principalDetails, @ModelAttribute("item") NewItemFormDto newItemFormDto, BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {
            log.info("bindingError = {}", bindingResult);
        }
        if (newItemFormDto.getItemTag() == null) {
            bindingResult.addError(new ObjectError("item", "태그를 선택해주세요."));
            return "item/newItem";
        }
        if (newItemFormDto.getName().isEmpty()) {
            bindingResult.addError(new ObjectError("item", "제목을 작성해주세요."));
            return "item/newItem";
        }

        if (newItemFormDto.getPrice() == null) {
            if (bindingResult.hasErrors()) { //타입 에러(필드 에러) 처리
                return "item/newItem";
            }
            bindingResult.addError(new ObjectError("item", "가격을 입력해주세요."));
            return "item/newItem";
        }
        if (newItemFormDto.getContent().isEmpty()) {
            bindingResult.addError(new ObjectError("item", "내용을 작성해주세요."));
            return "item/newItem";
        }
        if (newItemFormDto.getTradePlace().isEmpty()) {
            bindingResult.addError(new ObjectError("item", "만날 장소를 입력해주세요."));
            return "item/newItem";
        }

        Long memberId = principalDetails.getMember().getId();
        itemService.register_new(memberId, newItemFormDto.getName(), newItemFormDto.getItemTag(), newItemFormDto.getPrice(), newItemFormDto.getContent(), newItemFormDto.getTradePlace(), newItemFormDto.getImageFiles());
        return "redirect:/item";
    }


    @GetMapping({"/{categoryTag}", ""})
    public String index(@AuthenticationPrincipal PrincipalDetails principalDetails, ItemFilterDto itemFilterDto, Model model, PageCustom pageable) {

        log.info("itemFilterDtoCheck = {}", itemFilterDto);
        //초기
        if (itemFilterDto.getOrder() == null) {
            itemFilterDto.setOrder(ItemCheckTag.RECENT);
        }
        //초기
        if (itemFilterDto.getCategoryTag() == null) {
            model.addAttribute("selectedCategory", false);
        } else {
            model.addAttribute("selectedCategory", true);
        }
        model.addAttribute("filterDto", itemFilterDto);

        log.info("Item pageable null = {}", pageable);
        // 보여줄 item size
        int size = 3;
        Page<Item> PageItems = itemService.search_direct(itemFilterDto, new PageCustom(size, pageable.getPage()).getPageRequest());
        int start, end;
        int currentPage = PageItems.getNumber() + 1;
        int nextPage = currentPage + 1;
        int previousPage = currentPage - 1;
        int totalPage = PageItems.getTotalPages();

        //10 단위로 start, end 로직 %연산자 응용
        start = PageRange.startCul(currentPage);
        end = PageRange.endCul(currentPage, totalPage);
        log.info("startPage= {}", start);
        log.info("endPage = {}", end);
        List<Item> content = PageItems.getContent();
        postCheckAndViewFormItemTags(content, model, itemFilterDto.getOrder(), principalDetails);
        PageFormDto pageFormDto = new PageFormDto(start, end, totalPage, currentPage, nextPage, previousPage);
        model.addAttribute("page", pageFormDto);
        return "item/itemIndex";
    }

    // ************ 개인 상품 조회 ************

    @GetMapping("/myItem")
    public String myItem(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        Member member = principalDetails.getMember();
        MyInfoToHomeDto myInfoToHomeDto = new MyInfoToHomeDto(member.getId(), member.getNickname());
        model.addAttribute("member", myInfoToHomeDto);
        model.addAttribute("principal", principalDetails);
        return "item/myItem";
    }

    @PostMapping("/myItem")
    public String myItem_post(@RequestParam("id") Long itemId, @AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        log.info("구매 요청 완료 id = {}", itemId);
        Member member = principalDetails.getMember();
        tradeService.PurchasingRegister(itemId, member.getId());
        return "redirect:/item/myItem";
    }

    // ************ 개인 상품 -> 상품 구매 관련 ************
    @GetMapping("/myItem/purchasing")
    public String myItem_purchasing(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        Member member = principalDetails.getMember();
        List<PurchasingItem> purchasingItems = tradeService.searchMyPurchasing(member.getId());
        List<tradeFormAllDto> purchasingDto = purchasingItems.stream().map(purchasing -> new tradeFormAllDto(purchasing)).collect(Collectors.toList());
        MyInfoToHomeDto myInfoToHomeDto = new MyInfoToHomeDto(member.getId(), member.getNickname());
        model.addAttribute("member", myInfoToHomeDto);
        model.addAttribute("purchasingItem", purchasingDto);
        model.addAttribute("principal", principalDetails);
        model.addAttribute("deletePurchasing", new MyTradeDeleteDto());
        return "item/myPurchasingItem";
    }

    @PostMapping("/myItem/purchasing/delete")
    public String myItemPurchasingDelete(MyTradeDeleteDto myTradeDeleteDto) {
        myTradeDeleteDto.getTradeDeleteIds().forEach(item -> log.info("PurchasingDeleteId = {}", item));
        myTradeDeleteDto.getTradeDeleteIds().forEach(purchasingId -> tradeService.removePurchasing(purchasingId));
        return "redirect:/item/myItem/purchasing";
    }

    @PostMapping("/myItem/purchasing/success")
    public String myItemPurchasingDelete(MyTradeSuccessDto myTradeSuccessDto) {
        myTradeSuccessDto.getTradeSuccessIds().forEach(item -> log.info("PurchasingSuccessId = {}", item));
        myTradeSuccessDto.getTradeSuccessIds().forEach((purchasingId -> tradeService.PurchasingSuccess(purchasingId)));
        return "redirect:/item/myItem/purchasingRequest";
    }

    // ************ 개인 상품 -> 등록한 상품 목록 관련 ************
    @PostMapping("/myItem/delete")
    public String myItemDelete(MyItemDeleteDto myItemDeleteDto) {
        //forEach은 반복문 실행하기
        myItemDeleteDto.getItemIds().forEach(itemId -> itemService.removeItem(itemId));
        return "redirect:/item/myItem/register";

    }

    @GetMapping("/myItem/edit/{id}")
    public String myItem_edit(@PathVariable("id") Long itemId, @AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        Item item = itemService.searchMyItem(itemId, principalDetails.getMember().getId());
        NewItemFormDto itemAllFormDto = new NewItemFormDto(item);
        model.addAttribute("item", itemAllFormDto);
        return "item/myItemEdit";
    }

    @PostMapping("/myItem/edit")
    public String myItem_edit_post(@Validated @ModelAttribute("item") NewItemFormDto itemDto, @AuthenticationPrincipal PrincipalDetails principalDetails, BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {
            log.info("bindingError = {}", bindingResult);
        }
        if (itemDto.getItemTag() == null) {
            bindingResult.addError(new ObjectError("item", "태그를 선택해주세요."));
            return "item/myItemEdit";
        }
        if (itemDto.getName().isEmpty()) {
            bindingResult.addError(new ObjectError("item", "제목을 작성해주세요."));
            return "item/myItemEdit";
        }

        if (itemDto.getPrice() == null) {
            if (bindingResult.hasErrors()) { //타입 에러(필드 에러) 처리
                return "item/myItemEdit";
            }
            bindingResult.addError(new ObjectError("item", "가격을 입력해주세요."));
            return "item/myItemEdit";
        }
        if (itemDto.getContent().isEmpty()) {
            bindingResult.addError(new ObjectError("item", "내용을 작성해주세요."));
            return "item/myItemEdit";
        }
        if (itemDto.getTradePlace().isEmpty()) {
            bindingResult.addError(new ObjectError("item", "만날 장소를 입력해주세요."));
            return "item/myItemEdit";
        }
        itemService.editItem(itemDto.getId(), principalDetails.getMember().getId(), itemDto.getName(), itemDto.getPrice(), itemDto.getItemTag(), itemDto.getContent(), itemDto.getTradePlace(), itemDto.getImageFiles());
        log.info("myItem_edit_post 확인");

        return "redirect:/item/myItem/register";
    }

    // ************ 아이템 등록 ************
    @GetMapping("/myItem/register")
    public String myRegisterItem(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        Member findMember = memberService.search(principalDetails.getMember().getId());
        List<Item> items = findMember.getItems();
        MyInfoToHomeDto myInfoToHomeDto = new MyInfoToHomeDto(findMember.getId(), findMember.getNickname());
        List<ItemAllFormDto> itemsDto = items.stream().map(item -> new ItemAllFormDto(item)).collect(Collectors.toList());
        model.addAttribute("deleteItem", new MyItemDeleteDto());
        model.addAttribute("member", myInfoToHomeDto);
        model.addAttribute("items", itemsDto);
        model.addAttribute("principal", principalDetails);
        return "item/myRegisterItem";
    }

    // ************ 해당 상품 조회 관련 ************
    @GetMapping("/register/{id}")
    public String itemDetails(@PathVariable("id") Long itemId, @AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {

        Item findItem = itemService.search(itemId);
        List<Item> itemsAll = itemService.searchMyItemAll(findItem.getMember().getId());
        List<Item> currentItem = new ArrayList<>();
        List<PurchaseItem> purchaseItems = tradeService.searchAllPurchase();
        int existPurchase = purchaseItems.stream().filter(purchaseItem -> purchaseItem.getItem().getId() == itemId).collect(Collectors.toList()).size();
        currentItem.add(findItem);
        extractItemAllDto(model, currentItem);
        model.addAttribute("myAll", itemsAll);
        model.addAttribute("currentPurchase", existPurchase);
        extractItemPrincipalAndModel(principalDetails, model);
        /**
         * ***구매 요청, 찜하기***
         * 1. 내가 쓴 게시물이면 보이지 않아야함
         * 2. 로그인 안했으면 보여야함
         * 3. 다른 사용자 게시물이면 보여야 한다.
         * currentState
         * 4. 내가 구매 요청한 상품에는 구매 요청을 disable 해야한다.
         *
         */
        if (principalDetails != null) {
            Member member = principalDetails.getMember();
            List<PurchasingItem> purchasingItems = tradeService.searchMyPurchasing(member.getId());
            Item myItem = itemService.searchMyItem(itemId, member.getId());
            int existSize = purchasingItems.stream().filter(purchasingItem -> purchasingItem.getItem().getId() == itemId).collect(Collectors.toList()).size();
            model.addAttribute("currentPurchasing", existSize);
            model.addAttribute("currentState", myItem);
        } else {
            model.addAttribute("currentState", principalDetails);
            model.addAttribute("currentPurchasing", 0);
        }
        return "item/item";
    }


    // ************ 아이템 구매 요청 ************
    @PostMapping("/myItem/purchase/confirm")
    public String purchase_confirm(@RequestParam("id") Long id, @AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        log.info("idCheck = {}", id);
        Member member = principalDetails.getMember();
        Item item = itemService.search(id);

        model.addAttribute("member", new MemberFormDto(member));
        model.addAttribute("item", new ItemConfirmDto(item));
        return "item/itemConfirm";
    }

    // ************ 아이템 구매 요청 확인 ************
    @GetMapping("/myItem/purchasingRequest")
    public String confirmPurchasingRequest(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        Member member = principalDetails.getMember();
        List<PurchasingItem> purchasingItems = tradeService.confirmPurchasing(member.getId());
        List<ItemPurchasingConfirmDto> purchasingDto = purchasingItems.stream().map(purchasing -> new ItemPurchasingConfirmDto(purchasing)).collect(Collectors.toList());
        MyInfoToHomeDto myInfoToHomeDto = new MyInfoToHomeDto(member.getId(), member.getNickname());
        model.addAttribute("member", myInfoToHomeDto);
        model.addAttribute("purchasingItem", purchasingDto);
        model.addAttribute("principal", principalDetails);
        model.addAttribute("deletePurchasing", new MyTradeDeleteDto());
        model.addAttribute("successPurchasing", new MyTradeSuccessDto());
        return "item/itemPurchasingConfirm";
    }

    // ************ 구매한 상품 목록 관련 ************
    @GetMapping("/myItem/purchase")
    public String myItem_purchase(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        Member member = principalDetails.getMember();
//        List<PurchaseItem> purchaseItems = tradeService.searchMyPurchase(member.getId());
//        List<tradeFormAllDto> purchaseDto = purchaseItems.stream().map(purchase -> new tradeFormAllDto(purchase.getTransactionHistory().getPurchaseItem())).collect(Collectors.toList());

        List<TransactionHistory> transactionHistories = tradeService.searchMyTransactionHistory(member.getId());
        List<tradeFormAllDto> transHisDto = transactionHistories.stream().map(tranHis -> new tradeFormAllDto(tranHis.getPurchaseItem())).collect(Collectors.toList());
        MyInfoToHomeDto myInfoToHomeDto = new MyInfoToHomeDto(member.getId(), member.getNickname());
        model.addAttribute("member", myInfoToHomeDto);
        model.addAttribute("purchaseItem", transHisDto);
        model.addAttribute("principal", principalDetails);
        model.addAttribute("deletePurchase", new MyTradeDeleteDto());
        return "item/myPurchaseItem";
    }

    @PostMapping("/myItem/purchase/delete")
    public String MyItem_purchase_delete(MyTradeDeleteDto myTradeDeleteDto) {
        myTradeDeleteDto.getTradeDeleteIds().forEach(item -> log.info("PurchaseDeleteId = {}", item));
        myTradeDeleteDto.getTradeDeleteIds().forEach(transactionHisId -> tradeService.removeTransactionHistory(transactionHisId));
        return "redirect:/item/myItem/purchase";
    }

    // ************ 찜 상품 목록 관련 ************
    @PostMapping("/myItem/interest")
    public String myItemInterest(@RequestParam("id") Long itemId, @AuthenticationPrincipal PrincipalDetails principalDetails,Model model) {




        return "";
    }


    private void postCheckAndViewFormItemTags(List<Item> ItemService, Model model, ItemCheckTag itemCheckTag, PrincipalDetails principalDetails) {
        ItemCheckTagDto itemCheckTagDto = new ItemCheckTagDto(itemCheckTag);
        extractItemAllDto(model, ItemService);
        extractItemPrincipalAndModel(principalDetails, model);
        model.addAttribute("principal", principalDetails);
        model.addAttribute("checkTag", itemCheckTagDto);
    }

    private void extractItemPrincipalAndModel(PrincipalDetails principalDetails, Model model) {
        if (principalDetails != null) {
            Member findMember = principalDetails.getMember();
            MemberFormDto memberFormDto = new MemberFormDto(findMember.getId(), findMember.getNickname());
            model.addAttribute("member", memberFormDto);
        }
        model.addAttribute("principal", principalDetails);
    }

    private void extractItemAllDto(Model model, List<Item> items) {
        boolean check;
        List<ItemAllFormDto> itemDto = new ArrayList<>();
        List<PurchaseItem> purchaseItems = tradeService.searchAllPurchase();
        for (Item item : items) {
            check = false;
            for (PurchaseItem purchaseItem : purchaseItems) {
                if (item.getId() == purchaseItem.getItem().getId()) {
                    itemDto.add(new ItemAllFormDto(item, true));
                    check = true;
                    break;
                }
            }
            if(!check){
                itemDto.add(new ItemAllFormDto(item, false));
            }
        }
        //화면에 보여줄 item dto
//        List<ItemAllFormDto> itemDto = items.stream().map(item -> new ItemAllFormDto(item)).collect(Collectors.toList());
////        List<UploadFile> storeFiles = items.stream().map(item -> new ItemAllFormDto(item).getImgFile()).flatMap(List::stream).collect(Collectors.toList());
        model.addAttribute("product", itemDto);
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable("filename") String filename) throws MalformedURLException {
        log.info("fileName={}", filename);
        //저장된 파일을 불러온다.
        return new UrlResource("file:" + fileStore.getFullPath(filename));
    }


}
