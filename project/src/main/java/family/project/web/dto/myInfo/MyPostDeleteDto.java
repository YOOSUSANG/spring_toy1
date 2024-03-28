package family.project.web.dto.myInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyPostDeleteDto {
    private List<Long> boardDeleteId; // 삭제 체크 --> DTO에 효과!!!, 직접 Entity를 수정하지 않아도 됨 우리가 원하는 정보만 DTO에 담아서 보내기
}
