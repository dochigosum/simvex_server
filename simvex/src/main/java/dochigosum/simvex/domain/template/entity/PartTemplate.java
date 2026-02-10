package dochigosum.simvex.domain.template.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "part_template")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class PartTemplate {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 2000)
    private String detail;

    @Column(nullable = false, length = 255)
    private String modelFileName;

    private Integer xCoordinate;
    private Integer yCoordinate;
    private Integer zCoordinate;

    private Integer xRotation;
    private Integer yRotation;
    private Integer zRotation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drawing_id")
    private DrawingTemplate drawingTemplate;
}
