package family.project.domain.food;

import family.project.domain.Item;
import family.project.domain.enums.item.ItemTag;
import family.project.domain.file.UploadFile;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
@DiscriminatorValue("Food")
public class Food extends Item {

    private Integer calories;

    public Food(String name, Integer price, Integer stockQuantity, Integer calories, ItemTag itemTag,String information, String tradeRegion, List<UploadFile> imageFile) {
        super(name, price, stockQuantity, 0, itemTag, information, tradeRegion, imageFile);
        this.calories = calories;
    }

    protected Food() {
    }

    public static Food createFood(String name, Integer price, Integer stockQuantity, Integer calories, ItemTag itemTag,String information, String tradeRegion, List<UploadFile> imageFile) {
        Food food = new Food(name, price, stockQuantity, calories, itemTag,information, tradeRegion, imageFile);
        return food;
    }

}
