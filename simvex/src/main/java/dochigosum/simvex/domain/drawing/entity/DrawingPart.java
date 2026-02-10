package dochigosum.simvex.domain.drawing.entity;

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

    private Integer xCoordinate;
    private Integer yCoordinate;
    private Integer zCoordinate;

    private Integer xRotation;
    private Integer yRotation;
    private Integer zRotation;

    @Builder
    private DrawingPart(
            String name,
            String detail,
            String modelFileName,
            Integer xCoordinate,
            Integer yCoordinate,
            Integer zCoordinate,
            Integer xRotation,
            Integer yRotation,
            Integer zRotation
    ) {
        this.name = name;
        this.detail = detail;
        this.modelFileName = modelFileName;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.zCoordinate = zCoordinate;
        this.xRotation = xRotation;
        this.yRotation = yRotation;
        this.zRotation = zRotation;
    }

    // 연관관계 설정 전용 (패키지/도메인 내부용)
    void setDrawing(Drawing drawing) {
        this.drawing = drawing;
    }

    public void updateTransform(
            Integer xCoordinate,
            Integer yCoordinate,
            Integer zCoordinate,
            Integer xRotation,
            Integer yRotation,
            Integer zRotation
    ) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.zCoordinate = zCoordinate;
        this.xRotation = xRotation;
        this.yRotation = yRotation;
        this.zRotation = zRotation;
    }
}
