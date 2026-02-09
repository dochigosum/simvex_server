package dochigosum.simvex.domain.drawing.entity;

import dochigosum.simvex.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Drawing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id")
    private Member member;


    @Column(nullable = false, length=50)
    private String name;

    @Column(nullable = false, length = 2000)
    private String detail;

    @OneToMany(mappedBy = "drawing", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DrawingPart> parts = new ArrayList<>();

    public void addPart(DrawingPart part) {
        parts.add(part);
        part.setDrawing(this);
    }

    public void removePart(DrawingPart part) {
        parts.remove(part);
        part.setDrawing(null);
    }
}
