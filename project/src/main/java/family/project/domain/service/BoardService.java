package family.project.domain.service;

import family.project.domain.Member;
import family.project.domain.board.Board;
import family.project.domain.enums.BoardTag;
import family.project.domain.file.FileStore;
import family.project.domain.file.UploadFile;
import family.project.domain.repository.BoardRepository;
import family.project.domain.repository.MemberRepository;
import family.project.web.dto.community.BoardFilterDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final FileStore fileStore;

    @Transactional
    public void register(Board board) {
        boardRepository.save(board);
    }

    @Transactional
    public void BoardRegister(Long memberId, String title, String content, BoardTag boardTag, boolean publicIsPrivate, UploadFile attachFile, List<UploadFile> storeImgs){
        Member member = memberRepository.findById(memberId).orElse(null);
        Board board = Board.createBoard_real(member, title, content, boardTag, publicIsPrivate, attachFile, storeImgs);
        boardRepository.save(board);
    }

    public List<Board> findTitle(String title) {
        return boardRepository.findByTitleContaining(title);
    }

    public List<Board> postAll() {
        return boardRepository.findAll();
    }

    public Page<Board> postSearch_dynamic(BoardFilterDto boardFilterDto, Pageable pageable) {
        return boardRepository.searchTitleAndBoardTagAndCheckTag(boardFilterDto,pageable);
    }
    public List<Board> MyPostAll(String email) {
        return boardRepository.findByMyBoards(email);
    }


    //edit할 떄 자기 자신만의 board만 가져와야 한다.
    public Board selectPost(Long BoardId) {
        return boardRepository.findById(BoardId).orElse(null);
    }
    public Board editPost(Long BoardId, Long MemberId){
        return boardRepository.findByIdAndMemberId(BoardId, MemberId).orElse(null);
    }
    @Transactional
    public void removePost(Long id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    public void clear() {
        boardRepository.deleteAll();
    }

    @Transactional
    public void editPost(Long id, String title, String content, BoardTag boardTag, MultipartFile attachFile, List<MultipartFile> imageFiles) throws IOException {
        Board board = boardRepository.findById(id).orElse(null);
        //바뀐 첨부파일, 사진으로 변경
        UploadFile uploadFile = fileStore.storeFile(attachFile);
        List<UploadFile> storeFiles = fileStore.storeFiles(imageFiles);

        //트랜젝션 내에서 수정해야지 더티체킹이 체크된다.
        board.changeAll(title,content,boardTag,uploadFile,storeFiles);
    }


}
