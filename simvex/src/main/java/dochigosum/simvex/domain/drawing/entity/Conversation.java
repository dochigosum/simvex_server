package dochigosum.simvex.domain.drawing.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "conversation")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "conversation")
    private Drawing drawing;

    @Column(length = 2000)
    private String message;

    public void retouchMessage(String message) {
        this.message = message;
    }
}