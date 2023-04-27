package at.spengergasse.spengermed.model;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@SuperBuilder
@Table(name = "ima2d_imageregion2d")
public class ImageRegion2D extends  BackboneElement {
    public enum code {
        point, polyline, interpolated, circle, ellipse
    }

    @NotNull
    @Column(name="ima2d_regiontype", nullable = false)
    private code regiontype;

    @NotNull
    @ElementCollection
    @CollectionTable(name = "ima2d_coordinate")
    @Builder.Default
    private List<Double> coordinate = new ArrayList<Double>();

}
