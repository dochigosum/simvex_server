package dochigosum.simvex.domain.drawing.repository;

import dochigosum.simvex.domain.drawing.entity.DrawingPart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DrawingPartRepository extends JpaRepository<DrawingPart, Long> {
    List<DrawingPart> findAllByIdIn(List<Long> ids);
}
