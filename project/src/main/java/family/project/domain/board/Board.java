package family.project.domain.board;

import family.project.domain.BoardCategory;
import family.project.domain.Member;
import family.project.domain.enums.BoardTag;
import family.project.domain.file.UploadFile;
import family.project.domain.mapped.BasicEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
public class Board extends BasicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_category_id")
    private BoardCategory boardCategory;

    private String title;

    @Lob
    private String content;
    //첨부 파일 (일단 하나로 설정), attachments(파일 첨부)
    @Embedded
    private UploadFile attachFile;
    //값 컬력션 저장 방법 -> board_id로 join 할거다.
    @ElementCollection
    @CollectionTable(name = "board_img",
            joinColumns = @JoinColumn(name = "board_id"))
    private List<UploadFile> imgFiles = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private BoardTag boardTag;

    private Integer views; //조회수
    private Integer commentsCount;//댓글수
    private Integer likesCount;//좋아요수
    private Boolean publicIsPrivate;//비밀글 여부

    protected Board() {
    }

    public Board(Integer views, Integer commentsCount, Integer likesCount) {
        this.views = views;
        this.commentsCount = commentsCount;
        this.likesCount = likesCount;
    }

    //***** 연관 메소드 *****//
    public void changeMember(Member member) {
        this.member = member;
        member.getBoards().add(this);
    }

    public void removeMember(Member member) {
        this.member = null;
        member.getBoards().remove(this);
    }

    public void changeBoardCategory(BoardCategory boardCategory) {
        this.boardCategory = boardCategory;
        boardCategory.getBoards().add(this);
    }

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeContent(String content) {
        this.content = content;
    }


    public void changePuIsPri(Boolean publicIsPrivate) {
        this.publicIsPrivate = publicIsPrivate;
    }

    public void changeBoardTag(BoardTag boardTag) {
        this.boardTag = boardTag;
    }

    public void changeAttachFile(UploadFile attachFile) {
        this.attachFile = attachFile;
    }

    public void changeImageFile(List<UploadFile> storeFiles) {
        this.imgFiles.addAll(storeFiles);
    }

    public void changeAll(String title, String content, BoardTag boardTag, UploadFile attachFile, List<UploadFile> storeFiles) {
        changeTitle(title);
        changeContent(content);
        changeBoardTag(boardTag);
        changeAttachFile(attachFile);
        changeImageFile(storeFiles);

    }


    //***** 생성 메소드 *****//
    public static Board createBoard(Member member, String title, String content, BoardTag boardTag, Boolean publicIsPrivate, String... imgs) {
        Board newBoard = new Board(0, 0, 0);
        newBoard.changeMember(member);
        newBoard.changeTitle(title);
        newBoard.changeContent(content);
        newBoard.changeBoardTag(boardTag);
        newBoard.changePuIsPri(publicIsPrivate);
        return newBoard;
    }

    public static Board createBoard_real(Member member, String title, String content, BoardTag boardTag, Boolean publicIsPrivate, UploadFile attachFile, List<UploadFile> storeImgs) {
        Board newBoard = new Board(0, 0, 0);
        newBoard.changeMember(member);
        newBoard.changeTitle(title);
        newBoard.changeContent(content);
        newBoard.changeBoardTag(boardTag);
        newBoard.changePuIsPri(publicIsPrivate);
        newBoard.changeAttachFile(attachFile);
        newBoard.changeImageFile(storeImgs);
        return newBoard;
    }

    //***** 비지니스 메소드 *****//
    public void addViews() {
        this.views += 1;
    }

    public void addCommentsCount() {
        this.commentsCount += 1;
    }

    public void removeCommentsCount() {
        this.commentsCount -= 1;
    }

    public void addLikesCount() {
        this.likesCount += 1;
    }

    public void removeLikesCount() {
        this.likesCount -= 1;
    }


}
