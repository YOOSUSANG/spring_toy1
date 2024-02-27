package family.project.domain.service;

import family.project.domain.Member;
import family.project.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true) // 단순 조회만 하므로 1차 캐시 x
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Transactional
    public Member register(Member member) {
        Optional<Member> findMember = memberRepository.findByUsername(member.getUsername());
        return usernameValidation(member, findMember);
    }

    @Transactional
    public Member usernameValidation(Member member, Optional<Member> findMember) {
        if(findMember.isPresent()){
            throw new DataIntegrityViolationException("이미 회원이 존재합니다.");
        }
        String password = member.getPassword();
        String encodePassword = bCryptPasswordEncoder.encode(password);
        //진가발휘 -> 메소드에 @Transaction이 붙였으니까. -> 변경 감지
        member.changePassword(encodePassword);
        return memberRepository.save(member);
    }

    //해당 메소드가 끝나면 영속성 컨텍스트 종료 -> 트랜젝션 종료
    @Transactional
    public Member editNickName(Long id, String nickname) {
        Member findMember = memberRepository.findById(id).orElse(null);
        findMember.changeNickname(nickname); // 변경감지 진가 발휘하기 --> 이 메소드는 @Transactional로 영속성 컨텍스트 사용
        return findMember;
    }

    @Transactional
    public Member editPassword(Long id, String password) {
        Member findMember = memberRepository.findById(id).orElse(null);
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        findMember.changePassword(encodedPassword);
        return findMember;

    }

    public Member search(Long id) {
        Optional<Member> searchMember = memberRepository.findById(id);
        return searchMember.orElse(null);
    }
    //이메일로 찾기
    public Member search(String email) {
        Optional<Member> searchMember = memberRepository.findByEmail(email);
        return searchMember.orElse(null);
    }

    public List<Member> search() {
        return memberRepository.findAll();
    }

    @Transactional
    public void remove(Member member) {
        memberRepository.delete(member);
    }


    @Transactional
    public void clear() {
        memberRepository.deleteAll();
    }


}
