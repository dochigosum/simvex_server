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
public class CoordinateAttribute {
    private Double xCoordinate;
    private Double yCoordinate;
    private Double zCoordinate;

    public static CoordinateAttribute of(Double x, Double y, Double z) {
        return new CoordinateAttribute(x, y, z);
    }
}
