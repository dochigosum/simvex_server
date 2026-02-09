package dochigosum.simvex.domain.drawing.repository;

import dochigosum.simvex.domain.drawing.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoRepository extends JpaRepository<Memo, Long> {
}
