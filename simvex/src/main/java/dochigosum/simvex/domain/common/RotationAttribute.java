package dochigosum.simvex.domain.common;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RotationAttribute {
    private Integer xRotation;
    private Integer yRotation;
    private Integer zRotation;

    public static RotationAttribute of(Integer x, Integer y, Integer z) {
        return new RotationAttribute(x, y, z);
    }
}
