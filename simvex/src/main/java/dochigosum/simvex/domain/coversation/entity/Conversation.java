package dochigosum.simvex.domain.coversation.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Conversation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @OneToOne
//    private Long drawingId;

    @Column(length = 2000)
    private String message;

    public void retouchMessage(String message) {
        this.message = message;
    }
}
