package family.project.domain;

import family.project.domain.board.Board;
import family.project.domain.enums.CategoryTag;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "board_category")
@Getter
public class BoardCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_category_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private CategoryTag categoryTag;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private BoardCategory parent;

    @OneToMany(mappedBy = "parent")
    private List<BoardCategory> child = new ArrayList<>();


    @OneToMany(mappedBy = "boardCategory")
    private List<Board> boards = new ArrayList<>();



    //***** 연관관계 메소드 *****//
    public void addBoardCategory(BoardCategory child) {
        this.child.add(child);
        child.changeParent(this);
    }

    public void addBoard(Board board) {
        this.boards.add(board);
        board.addBoardCategory(this);
    }

    public void changeParent(BoardCategory parent){
        this.parent = parent;
    }

    //***** 비지니스 메소드 *****//

    public void BoardTagCheck(){



    }




}
