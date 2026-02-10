package dochigosum.simvex.domain.drawing.entity;

import dochigosum.simvex.domain.common.CoordinateAttribute;
import dochigosum.simvex.domain.common.RotationAttribute;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DrawingPart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "drawing_id")
    private Drawing drawing;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 2000)
    private String detail;

    @Column(nullable = false, length = 50)
    private String modelFileName;

    @Embedded
    private CoordinateAttribute coordinate;

    @Embedded
    private RotationAttribute rotation;

    @Builder
    private DrawingPart(String name, String detail, String modelFileName,
                        CoordinateAttribute coordinate, RotationAttribute rotation) {
        this.name = name;
        this.detail = detail;
        this.modelFileName = modelFileName;
        this.coordinate = coordinate;
        this.rotation = rotation;
    }

    // 위치/회전 변경
    public void updateTransform(CoordinateAttribute coordinate, RotationAttribute rotation) {
        this.coordinate = coordinate;
        this.rotation = rotation;
    }

    // 연관관계 설정 전용 (패키지/도메인 내부용)
    void setDrawing(Drawing drawing) {
        this.drawing = drawing;
    }
}
