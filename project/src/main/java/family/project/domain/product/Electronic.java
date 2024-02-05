package family.project.domain.product;

import family.project.domain.Item;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Electronic")
public class Electronic extends Item {

    private String manufacturer;
    private String country;
    private String releaseDate;

    public Electronic(String manufacturer, String country, String releaseDate) {
        this.manufacturer = manufacturer;
        this.country = country;
        this.releaseDate = releaseDate;
    }

    protected Electronic() {

    }

}
