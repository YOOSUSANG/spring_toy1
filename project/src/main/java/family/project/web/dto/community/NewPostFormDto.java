package family.project.web.dto.community;

import family.project.domain.board.Board;
import family.project.domain.enums.BoardTag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewPostFormDto {

    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    private BoardTag boardTag;

    //첨부 파일
    private MultipartFile attachFile;

    //이미지 파일
    private List<MultipartFile> imageFiles;

    public NewPostFormDto(Long id) {
        this.id = id;
    }

    public NewPostFormDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.boardTag = board.getBoardTag();
    }
}
