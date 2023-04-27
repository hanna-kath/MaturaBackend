package at.spengergasse.spengermed.model;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@SuperBuilder
@Table(name = "ima3d_imageregion3d")
public class ImageRegion3D extends BackboneElement{

    public enum code {
        point, multipoint, polyline, polygon, ellipse, ellipsoid
    }

    @NotNull
    @Column(name="ima3d_regiontype")
    private code regiontype;

    @NotNull
    @ElementCollection
    @CollectionTable(name = "ima3d_coordinate")
    @Builder.Default
    private List<Double> coordinate = new ArrayList<Double>();

}
