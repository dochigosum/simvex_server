package dochigosum.simvex.domain.template.repository;

import dochigosum.simvex.domain.template.entity.PartTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartTemplateRepository extends JpaRepository<PartTemplate, Long> {
    List<PartTemplate> findAllByDrawingTemplate_Id(Long drawingTemplateId);
}