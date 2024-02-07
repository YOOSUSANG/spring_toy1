package family.project.service;

import family.project.domain.Member;
import family.project.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true) // 단순 조회만 하므로 1차 캐시 x
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

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

        return memberRepository.save(member);
    }

    public Member search(Long id) {
        Optional<Member> searchMember = memberRepository.findById(id);
        return searchMember.orElse(null);
    }

    public Member search(String username) {
        Optional<Member> searchMember = memberRepository.findByUsername(username);
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
