package family.project.domain.product;

import family.project.domain.Item;
import family.project.domain.enums.item.ItemTag;
import family.project.domain.file.UploadFile;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

import java.util.List;

@Entity
@DiscriminatorValue("Electronic")
@Getter
public abstract class Electronic extends Item {


    public Electronic(String name, Integer price, Integer stockQuantity, ItemTag itemTag, String content, String tradeRegion, List<UploadFile> imageFile) {
        super(name, price, stockQuantity, 0, itemTag, content, tradeRegion, imageFile);

    }


    protected Electronic() {

    }


}
