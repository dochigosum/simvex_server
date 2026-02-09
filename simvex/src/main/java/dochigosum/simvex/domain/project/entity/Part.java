package dochigosum.simvex.domain.project.entity;

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

    @Column(name = "project_id", nullable = false)
    private Long projectId;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(name = "file_name", length = 50)
    private String fileName;

    @Column(name = "x_coordinate")
    private Long xCoordinate;

    @Column(name = "y_coordinate")
    private Long yCoordinate;

    @Column(name = "z_coordinate")
    private Long zCoordinate;

    @Column(name = "x_rotation")
    private Long xRotation;

    @Column(name = "y_rotation")
    private Long yRotation;

    @Column(name = "z_rotation")
    private Long zRotation;

    @Builder
    public Part(Long projectId, String name, String fileName,
                Long xCoordinate, Long yCoordinate, Long zCoordinate,
                Long xRotation, Long yRotation, Long zRotation) {
        this.projectId = projectId;
        this.name = name;
        this.fileName = fileName;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.zCoordinate = zCoordinate;
        this.xRotation = xRotation;
        this.yRotation = yRotation;
        this.zRotation = zRotation;
    }

    public void updatePosition(Long x, Long y, Long z) {
        this.xCoordinate = x;
        this.yCoordinate = y;
        this.zCoordinate = z;
    }

    public void updateRotation(Long x, Long y, Long z) {
        this.xRotation = x;
        this.yRotation = y;
        this.zRotation = z;
    }
}