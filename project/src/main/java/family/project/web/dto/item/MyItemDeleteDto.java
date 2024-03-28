package family.project.web.dto.item;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyItemDeleteDto {
    private List<Long> itemIds = new ArrayList<>();

}
