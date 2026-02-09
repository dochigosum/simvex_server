package dochigosum.simvex.domain.template.repository;

import dochigosum.simvex.domain.template.entity.DrawingTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DrawingTemplateRepository extends JpaRepository<DrawingTemplate, Long> {

    List<DrawingTemplate> findByNameContainsIgnoreCase(String name);
}
