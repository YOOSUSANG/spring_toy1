package family.project.domain.groceries;


import family.project.domain.Item;
import family.project.domain.enums.item.ItemTag;
import family.project.domain.file.UploadFile;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

import java.util.List;

@Entity
@DiscriminatorValue("Health")
@Getter
public abstract class HealthGroceries extends Item {

    protected HealthGroceries() {
    }


    public HealthGroceries(String name, Integer price, Integer stockQuantity, ItemTag itemTag, String information, String tradeRegion, List<UploadFile> imageFile) {
        super(name, price, stockQuantity, 0, itemTag, information, tradeRegion, imageFile);

    }

}
