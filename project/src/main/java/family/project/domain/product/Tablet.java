package family.project.domain.product;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Tablet")
public class Tablet extends Electronic{
    private String display;
    private String ram;
    private String core;

    public Tablet(String manufacturer, String country, String releaseDate, String display, String ram, String core) {
        super(manufacturer, country, releaseDate);
        this.display = display;
        this.ram = ram;
        this.core = core;
    }

    protected Tablet() {

    }
}
