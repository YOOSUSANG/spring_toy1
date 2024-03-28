package family.project.domain.repository;

import family.project.domain.board.Board;
import family.project.domain.enums.BoardTag;
import family.project.web.dto.community.BoardFilterDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositorySearchCustom {


    @Override
    void deleteById(Long id);

    @Override
    void deleteAll();

    @Override
    Optional<Board> findById(Long id);

    Optional<Board> findByIdAndMemberId(Long BoardId, Long MemberId);

    //주어진 문자열을 포함하는 데이터를 반환한다.
    List<Board> findByTitleContaining(String title);


    //join fetch시 db에 없는 거를 fetch x join fetch하면 lazy 발생 안하고 다 불러온다.
    @Query("select b from Board b" +
            " join fetch b.member" +
            " order by b.createDate desc")
    List<Board> findAll();

    @Query("select b from Board b" +
            " join fetch b.member" +
            " where b.boardTag = :boardTag" +
            " and b.title = :title" +
            " order by b.createDate desc")
    List<Board> findByBoardTagsDefault(@Param("boardTag") BoardTag boardTag, @Param("title")String title);

    List<Board> findByBoardTagOrderByViewsDesc(BoardTag boardTag);

    List<Board> findByBoardTagOrderByLikesCountDesc(BoardTag boardTag);

    List<Board> findByBoardTagOrderByCommentsCountDesc(BoardTag boardTag);




    @Query("select b from Board b" +
            " join fetch b.member" +
            " where b.member.email = :email")
    List<Board> findByMyBoards(@Param("email") String email);

}
