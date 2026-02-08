package dochigosum.simvex.domain.drawing.repository;

import dochigosum.simvex.domain.drawing.entity.Drawing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DrawingRepository extends JpaRepository<Drawing, Long> {
    List<Drawing> findByNameContaining(String query);

    List<Drawing> findByNameContainingIgnoreCase(String query);
}
