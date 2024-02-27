package family.project.domain.board;

import family.project.domain.BoardCategory;
import family.project.domain.Member;
import family.project.domain.enums.BoardTag;
import family.project.domain.mapped.BasicEntity;
import jakarta.persistence.*;
import lombok.Getter;

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

    @Enumerated(EnumType.STRING)
    private BoardTag boardTag;


    //값 컬력션 저장 방법
    @ElementCollection
    @CollectionTable(name = "board_img",
            joinColumns = @JoinColumn(name = "board_member_id"))
    @Column(name = "board_img_name")
    private List<String> imgs = new ArrayList<>();

    private Integer views; //조회수
    private Integer commentsCount;//댓글수
    private Integer likesCount;//좋아요수
    private Boolean publicIsPrivate;//비밀글 여부
    //Attachments(파일 첨부),


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

    public void addImg(String... imgs) {
        for (String img : imgs) {
            getImgs().add(img);
        }
    }

    public void changePuIsPri(Boolean publicIsPrivate) {
        this.publicIsPrivate = publicIsPrivate;
    }

    public void changeBoardTag(BoardTag boardTag) {
        this.boardTag = boardTag;
    }


    //***** 생성 메소드 *****//
    public static Board createBoard(Member member, String title, String content, BoardTag boardTag, Boolean publicIsPrivate, String... imgs) {
        Board newBoard = new Board(0, 0, 0);
        newBoard.changeMember(member);
//        newBoard.changeBoardCategory(boardCategory);
        newBoard.changeTitle(title);
        newBoard.changeContent(content);
        newBoard.changeBoardTag(boardTag);
        newBoard.changePuIsPri(publicIsPrivate);
        newBoard.addImg(imgs);

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
