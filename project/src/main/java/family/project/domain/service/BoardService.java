package family.project.domain.service;

import family.project.domain.board.Board;
import family.project.domain.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Transactional
    public void register(Board board) {
        boardRepository.save(board);
    }
    public List<Board> orderByLikeCountDesc() {
        return boardRepository.findByOrderByLikesCountDesc();
    }

    public List<Board> orderByCommentCountDesc() {
        return boardRepository.findByOrderByCommentsCountDesc();
    }

    public List<Board> findTitle(String title) {
        return boardRepository.findByTitleContaining(title);
    }

    public List<Board> viewAll() {
        return boardRepository.findAll();
    }

    public List<Board> viewMyBoard(String email) {
        return boardRepository.findByMyBoards(email);
    }

    public Board selectBoard(Long id) {
        return boardRepository.findById(id).orElse(null);
    }

    @Transactional
    public void removeBoard(Long id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    public void clear() {
        boardRepository.deleteAll();
    }

}
