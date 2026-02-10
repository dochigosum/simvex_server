package dochigosum.simvex.domain.project.entity;

import dochigosum.simvex.domain.common.CoordinateAttribute;
import dochigosum.simvex.domain.common.RotationAttribute;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "part")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Part {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 50)
    private String modelFileName;

    @Embedded
    private CoordinateAttribute coordinate;

    @Embedded
    private RotationAttribute rotation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @Builder
    public Part(String name, String modelFileName, Project project,
                CoordinateAttribute coordinate, RotationAttribute rotation) {
        this.name = name;
        this.modelFileName = modelFileName;
        this.project = project;
        this.coordinate = coordinate;
        this.rotation = rotation;
    }

    public void updateTransform(CoordinateAttribute coordinate, RotationAttribute rotation) {
        this.coordinate = coordinate;
        this.rotation = rotation;
    }
}