package dochigosum.simvex.domain.drawing.entity;

import dochigosum.simvex.domain.drawing.entity.Drawing;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "memo")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Memo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drawing_id", nullable = false)
    private Drawing drawing;

    @Column(length = 2000, nullable = false)
    private String detail;

    public Memo(Long drawingId, String detail) {
        this.drawingId = drawingId;
        this.detail = detail;
    }
}
