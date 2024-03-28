package family.project.domain.product;

import family.project.domain.enums.item.ItemTag;
import family.project.domain.file.UploadFile;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
@DiscriminatorValue("Tablet")
public class Tablet extends Electronic {

    public Tablet(String name, Integer price, Integer stockQuantity, ItemTag itemTag, String content, String tradeRegion, List<UploadFile> imageFile) {
        super(name, price, stockQuantity, itemTag, content, tradeRegion, imageFile);

    }

    protected Tablet() {
    }

    public static Tablet createTablet(String name, Integer price, Integer stockQuantity, ItemTag itemTag, String content, String tradeRegion, List<UploadFile> imageFile) {
        Tablet tablet = new Tablet(name, price, stockQuantity, itemTag, content, tradeRegion, imageFile);
        return tablet;
    }
}
