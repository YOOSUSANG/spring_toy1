package family.project.domain.groceries;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@DiscriminatorValue("vitamin")
@Getter
public class Vitamin extends HealthGroceries{

    public Vitamin(String name, Integer price, Integer stockQuantity, String shape, String supplementFunction) {
        super(name, price, stockQuantity, shape, supplementFunction);
    }

    protected Vitamin() {

    }
}
