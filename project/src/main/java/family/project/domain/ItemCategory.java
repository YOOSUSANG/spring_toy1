package family.project.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "item_category")
@Getter
public class ItemCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_category_id")
    private Long id;

    private String name;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private ItemCategory parent;

    @OneToMany(mappedBy = "parent")
    private List<ItemCategory> child = new ArrayList<>();

    @OneToMany(mappedBy = "itemCategory")
    private List<CategoryItem> categoryItems = new ArrayList<>();






    //***** 연관관계 메소드 *****//
    public void addChildCategory(ItemCategory child){
        this.child.add(child);
        child.changeParent(this);

    }
    public void changeParent(ItemCategory parent){
        this.parent = parent;
    }



}
