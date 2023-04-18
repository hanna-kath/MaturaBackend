package at.spengergasse.spengermed.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "ls_lensspecification")
@SuperBuilder
@AllArgsConstructor
public class LenseSpecification extends BackboneElement{

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ls_cc_product")
    private CodeableConcept product;

    public enum EyeCode {
       left,
        right
    }
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "ls_eye")
    private EyeCode eye;

    @Column(name = "ls_sphere")
    private Float sphere;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "pr_ls_prism", nullable = true, referencedColumnName = "id")
    @Builder.Default
    private List<Prism> prism = new ArrayList<>();

    @Column(name = "ls_add")
    private Float add;

    @Column(name = "ls_cylinder")
    private Float cylinder;

    @Column(name = "ls_axis")
    private Integer axis;

}
