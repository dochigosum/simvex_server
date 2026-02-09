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

    @Column(nullable = false, length = 255)
    private String fileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drawing_id")
    private DrawingTemplate drawingTemplate;
}
