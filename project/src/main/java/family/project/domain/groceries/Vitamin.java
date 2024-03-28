package family.project.domain.groceries;

import family.project.domain.enums.item.ItemTag;
import family.project.domain.file.UploadFile;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

import java.util.List;

@Entity
@DiscriminatorValue("vitamin")
@Getter
public class Vitamin extends HealthGroceries {



    public Vitamin(String name, Integer price, Integer stockQuantity, ItemTag itemTag,String information, String tradeRegion, List<UploadFile> imageFile) {
        super(name, price, stockQuantity, itemTag,information,tradeRegion, imageFile);
    }

    protected Vitamin() {


    }

    public static Vitamin createVitamin(String name, Integer price, Integer stockQuantity, ItemTag itemTag,String information,String tradeRegion, List<UploadFile> imageFile) {
        Vitamin vitamin = new Vitamin(name, price, stockQuantity,itemTag,information, tradeRegion, imageFile);
        return vitamin;
    }
}
