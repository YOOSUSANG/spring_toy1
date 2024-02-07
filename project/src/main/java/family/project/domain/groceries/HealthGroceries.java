package family.project.domain.groceries;


import family.project.domain.Item;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@DiscriminatorValue("Health")
@Getter
public class HealthGroceries extends Item {
    private String shape;
    private String supplementFunction;

    protected HealthGroceries() {
    }



    public HealthGroceries(String name, Integer price, Integer stockQuantity, String shape, String supplementFunction) {
        super(name, price, stockQuantity);
        this.shape = shape;
        this.supplementFunction = supplementFunction;
    }
}
