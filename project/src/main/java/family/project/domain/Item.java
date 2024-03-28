package family.project.domain;

import family.project.domain.enums.item.ItemTag;
import family.project.domain.exception.NotEnoughStockException;
import family.project.domain.file.UploadFile;
import family.project.domain.mapped.BasicEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
@Getter
public abstract class Item extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    private String name;
    @ElementCollection
    @CollectionTable(name = "item_img",
            joinColumns = @JoinColumn(name = "item_id"))
    private List<UploadFile> imageFiles = new ArrayList<>();
    private Integer price;
    private Integer stockQuantity;
    private Integer likesCount;
    private String tradeRegion;
    private String information;
    @Enumerated(EnumType.STRING)
    private ItemTag itemTag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "item")
    private List<CategoryItem> categoryItems = new ArrayList<>();


    //    @OneToMany(mappedBy = "item")
//    private List<ItemSaveMember> ItemSaveMembers = new ArrayList<>();
    protected Item() {


    }

    public Item(String name, Integer price, Integer stockQuantity, Integer likesCount, ItemTag itemTag, String tradeRegion, List<UploadFile> imageFiles) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.likesCount = likesCount;
        this.itemTag = itemTag;
        this.imageFiles = imageFiles;
        this.tradeRegion = tradeRegion;
    }

    public Item(String name, Integer price, Integer stockQuantity, Integer likesCount, ItemTag itemTag, String information,String tradeRegion, List<UploadFile> imageFiles) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.likesCount = likesCount;
        this.information = information;
        this.itemTag = itemTag;
        this.imageFiles = imageFiles;
        this.tradeRegion = tradeRegion;
    }
    public void changeAll(String name, Integer price, ItemTag itemTag, String information, String tradeRegion, List<UploadFile> imageFiles) {
        this.name = name;
        this.price = price;
        this.itemTag = itemTag;
        this.imageFiles = imageFiles;
        this.tradeRegion = tradeRegion;
        this.information = information;
    }

    //*****연관 메소드*****//
    public void addCategoryItems(CategoryItem categoryItem) {
        this.categoryItems.add(categoryItem);
        categoryItem.changeItem(this);
    }

    public void addMember(Member member) {
        this.member = member;
        member.getItems().add(this);
    }

    //*****비지니스 로직*****//
    public void removeStock(Integer count) {
        int restStock = this.stockQuantity - count;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        //오류가 안나면 남은 수량 저장
        this.stockQuantity = restStock;
    }

    public void addStock(Integer quantity) {
        this.stockQuantity += quantity;
    }

    public void addLikeCount() {
        this.likesCount += 1;
    }
}
