package at.spengergasse.spengermed.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "ma_manifest")
public class Manifest extends BackboneElement {

    @Column(name = "ma_rendering")
    private String rendering;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="rs_ma_id", referencedColumnName = "id")
    private List<ResourceBB> resource = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="pa_ma_id", referencedColumnName = "id")
    private List<Page> page = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "ma_image")
    @Builder.Default
    private List<String> image = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "ma_other")
    @Builder.Default
    private List<String> other = new ArrayList<>();
}
