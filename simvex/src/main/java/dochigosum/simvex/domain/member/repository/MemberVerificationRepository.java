package dochigosum.simvex.domain.member.repository;

import dochigosum.simvex.domain.member.entity.Member;
import dochigosum.simvex.domain.member.entity.MemberVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberVerificationRepository extends JpaRepository<MemberVerification, Long> {
    Optional<MemberVerification> findTopByMemberOrderByIdDesc(Member member);
    void deleteAllByMember(Member member);
}
