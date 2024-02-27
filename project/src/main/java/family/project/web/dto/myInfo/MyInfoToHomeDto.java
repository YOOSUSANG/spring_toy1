package family.project.web.dto.myInfo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MyInfoToHomeDto {
    private Long id;
    private String nickname;

    public MyInfoToHomeDto() {
    }
}
