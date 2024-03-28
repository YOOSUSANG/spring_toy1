package family.project.domain.repository;

import family.project.domain.board.Board;
import family.project.web.dto.community.BoardFilterDto;
import family.project.web.dto.community.TitleSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepositorySearchCustom {


    //paging : title, boardTag, checkTag 동적 쿼리
    Page<Board> searchTitleAndBoardTagAndCheckTag(BoardFilterDto boardFilterDto, Pageable pageable);


}
