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

    public HealthGroceries(String shape, String supplementFunction) {
        this.shape = shape;
        this.supplementFunction = supplementFunction;
    }
}
