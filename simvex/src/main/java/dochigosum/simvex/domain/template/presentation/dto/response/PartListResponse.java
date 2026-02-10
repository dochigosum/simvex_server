package dochigosum.simvex.domain.template.presentation.dto.response;

import dochigosum.simvex.domain.template.entity.PartTemplate;

public record PartListResponse(
       Long id,
       String name,
       String fileName
) {
    public static PartListResponse from(PartTemplate template) {
        return new PartListResponse(
                template.getId(),
                template.getName(),
                template.getModelFileName()
        );
    }
}
