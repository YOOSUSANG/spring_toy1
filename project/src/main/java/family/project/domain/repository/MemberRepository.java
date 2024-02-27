package family.project.domain.repository;

import family.project.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {


    Optional<Member> findByEmail(String email);

    Optional<Member> findByUsername(String username);


    @Override
    void delete(Member member);

    @Override
    void deleteAll();

    @Override
    Optional<Member> findById(Long id);

    @Override
    List<Member> findAll();
}
