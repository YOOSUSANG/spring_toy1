package family.project.web.dto.community;

import family.project.domain.board.Board;
import family.project.domain.enums.BoardTag;
import family.project.domain.file.UploadFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostAllFormDto {
    private Long id;
    private String title;
    private String content;
    private BoardTag boardTag;
    private LocalDateTime createTime; // 작성일
    private Integer views; //조회수
    private Integer commentsCount;//댓글수
    private Integer likesCount;//좋아요수
    private Boolean publicIsPrivate;//비밀글 여부
    private String username;
    private String nickname;
    private UploadFile attachFile;
    private List<UploadFile> imgFiles;

    public PostAllFormDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.boardTag = board.getBoardTag();
        this.createTime = board.getCreateDate();
        this.views = board.getViews();
        this.commentsCount = board.getCommentsCount();
        this.likesCount = board.getLikesCount();
        this.publicIsPrivate = board.getPublicIsPrivate();
        this.username = board.getMember().getUsername();
        this.nickname = board.getMember().getNickname();
        this.attachFile = board.getAttachFile();
        this.imgFiles = board.getImgFiles();

    }
}
