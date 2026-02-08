package dochigosum.simvex.domain.template.service;

import dochigosum.simvex.domain.template.entity.DrawingTemplate;
import dochigosum.simvex.domain.template.presentation.dto.response.DrawingListResponse;
import dochigosum.simvex.domain.template.presentation.dto.response.DrawingSearchResponse;
import dochigosum.simvex.domain.template.presentation.dto.response.PartListResponse;
import dochigosum.simvex.domain.template.repository.DrawingTemplateRepository;
import dochigosum.simvex.domain.template.repository.PartTemplateRepository;
import dochigosum.simvex.global.error.GlobalErrorCode;
import dochigosum.simvex.global.error.exception.SimvexException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TemplateService {

    private final DrawingTemplateRepository drRepo;
    private final PartTemplateRepository partRepo;

    // 조립도 목록 조회
    @Transactional(readOnly = true)
    public List<DrawingListResponse> getDrawings() {
        return drRepo.findAll()
                .stream()
                .map(DrawingListResponse::from)
                .toList();
    }

    // 조립도 검색
    @Transactional(readOnly = true)
    public List<DrawingSearchResponse> searchDrawings(String query) {

        List<DrawingTemplate> drawings = drRepo.findByNameContainsIgnoreCase(query);
        if (drawings.isEmpty()) {
            throw new SimvexException(GlobalErrorCode.NOT_FOUND, "검색 결과가 없습니다: " + query);
        }

        return drawings.stream()
                .map(DrawingSearchResponse::from)
                .toList();
    }

    // 조립도 별 부품 목록 조회
    @Transactional(readOnly = true)
    public List<PartListResponse> getParts(Long id) {
        return partRepo.findAllByDrawingTemplate_Id(id)
                .stream()
                .map(PartListResponse::from)
                .toList();
    }
}
