package family.project.domain.product;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("keyBoard")
public class KeyBoard extends Electronic{
    private String connectMethod;
    private String switchType;
    private String shape;

    public KeyBoard(String name, Integer price, Integer stockQuantity, String manufacturer, String country, String releaseDate, String connectMethod, String switchType, String shape) {
        super(name, price, stockQuantity, manufacturer, country, releaseDate);
        this.connectMethod = connectMethod;
        this.switchType = switchType;
        this.shape = shape;
    }

    protected KeyBoard() {

    }
}
