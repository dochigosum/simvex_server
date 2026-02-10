package dochigosum.simvex.domain.template.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "drawing_template")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class DrawingTemplate {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 2000)
    private String detail;

    @Column(nullable = false, length = 255)
    private String previewImg;

    @OneToMany(mappedBy = "drawingTemplate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PartTemplate> partTemplates = new ArrayList<>();
}
