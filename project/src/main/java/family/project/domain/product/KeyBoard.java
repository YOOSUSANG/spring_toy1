package family.project.domain.product;


import family.project.domain.enums.item.ItemTag;
import family.project.domain.file.UploadFile;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
@DiscriminatorValue("keyBoard")
public class KeyBoard extends Electronic {


    public KeyBoard(String name, Integer price, Integer stockQuantity, ItemTag itemTag, String content, String tradeRegion, List<UploadFile> imageFile) {
        super(name, price, stockQuantity, itemTag, content, tradeRegion, imageFile);

    }

    protected KeyBoard() {

    }

    public static KeyBoard createKeyBoard(String name, Integer price, Integer stockQuantity, ItemTag itemTag, String content, String tradeRegion, List<UploadFile> imageFile) {
        KeyBoard keyBoard = new KeyBoard(name, price, stockQuantity, itemTag, content, tradeRegion, imageFile);
        return keyBoard;
    }
}
