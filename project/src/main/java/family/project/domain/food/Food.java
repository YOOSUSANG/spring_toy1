package family.project.domain.food;

import family.project.domain.Item;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Food")
public class Food extends Item {

    private Integer calories;
    private String foodType;
    private String information;

    public Food(Integer calories, String foodType, String information) {
        this.calories = calories;
        this.foodType = foodType;
        this.information = information;
    }


    protected Food() {
    }
}
