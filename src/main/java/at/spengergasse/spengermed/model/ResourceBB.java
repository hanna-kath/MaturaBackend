package at.spengergasse.spengermed.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "rs_resource")
public class ResourceBB extends BackboneElement  {
    public enum FhirVersion{
        V1, V2, V3, V4
    }

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rs_re_reference", referencedColumnName = "id", nullable = false)
    private Reference reference;

    @Enumerated(EnumType.STRING)
    @ElementCollection
    @CollectionTable(name = "rs_fhir_version")
    @Builder.Default
    private List<FhirVersion> fhirVersion = new ArrayList<>();

    @Column(name="rs_name")
    private String name;

    @Column(name="rs_description")
    private String description;

    @Column(name="rs_is_example")
    private Boolean isExample;

    @ElementCollection
    @CollectionTable(name = "rs_profile")
    @Builder.Default
    private List<String> profile = new ArrayList<>();

    @Column(name="rs_grouping_id")
    private String groupingId;

    @Column(name="rs_relative_path")
    private String relativePath;
}
