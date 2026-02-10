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
    private Double xRotation;
    private Double yRotation;
    private Double zRotation;

    public static RotationAttribute of(Double x, Double y, Double z) {
        return new RotationAttribute(x, y, z);
    }
}
