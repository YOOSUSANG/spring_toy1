package family.project.domain;

import family.project.exception.NotEnoughStockException;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorColumn
@Getter
public abstract class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;
    private String name;
    private String img;
    private Integer price;
    private Integer stockQuantity;

    @OneToMany(mappedBy = "item")
    private List<CategoryItem> categoryItems = new ArrayList<>();


    public void addStock(Integer quantity) {
        this.stockQuantity += quantity;

    }

    public void removeStock(Integer count) {
        int restStock = this.stockQuantity - count;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        //오류가 안나면 남은 수량 저장
        this.stockQuantity = restStock;
    }
}
