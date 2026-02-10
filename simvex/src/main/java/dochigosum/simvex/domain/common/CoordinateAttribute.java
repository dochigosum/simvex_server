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
    private Integer xCoordinate;
    private Integer yCoordinate;
    private Integer zCoordinate;

    public static CoordinateAttribute of(Integer x, Integer y, Integer z) {
        return new CoordinateAttribute(x, y, z);
    }
}
