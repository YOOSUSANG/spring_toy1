package family.project.domain.groceries;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@DiscriminatorValue("vitamin")
@Getter
public class Vitamin extends HealthGroceries{

    public Vitamin(String shape, String supplementFunction) {
        super(shape, supplementFunction);
    }
    protected Vitamin() {

    }
}
