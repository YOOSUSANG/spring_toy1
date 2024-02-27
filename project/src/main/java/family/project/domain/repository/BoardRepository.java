package family.project.domain.repository;

import family.project.domain.board.Board;
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

    List<Board> findByOrderByLikesCountDesc();

    List<Board> findByOrderByCommentsCountDesc();

    //주어진 문자열을 포함하는 데이터를 반환한다.
    List<Board> findByTitleContaining(String title);


    //join fetch시 db에 없는 거를 fetch x
    @Query("select b from Board b" +
            " join fetch b.member")
    List<Board> findAll();

    @Query("select b from Board b" +
            " join fetch b.member" +
            " where b.member.email = :email")
    List<Board> findByMyBoards(@Param("email") String email);

}
