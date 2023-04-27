package at.spengergasse.spengermed.model;

import jdk.jfr.Unsigned;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "im_imagingselection")
@SuperBuilder
public class ImagingSelection extends DomainResource {

    public enum code{
        available, enteredinerror, unknown
    }

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "i_im_id", referencedColumnName = "id")
    private List<Identifier> identifier = new ArrayList<Identifier>();

    @NotNull
    @Column(name="im_status", nullable = false)
    private code status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "im_ref_id", referencedColumnName = "id")
    private Reference subject;

    @Column(name="im_issued")
    private LocalDateTime issued;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "per_im_id", referencedColumnName = "id")
    private List<Performer> performer = new ArrayList<Performer>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "bas_im_id", referencedColumnName = "id")
    private List<Reference> basedon = new ArrayList<Reference>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cat_im_id", referencedColumnName = "id")
    private List<CodeableConcept> category = new ArrayList<CodeableConcept>();

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cod_im_id", referencedColumnName = "id", nullable = false)
    private CodeableConcept code;

    @Column(name="im_studyuid")
    private String studyuid;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ref_im_id", referencedColumnName = "id")
    private List<Reference> derivedfrom = new ArrayList<Reference>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ref_im_id", referencedColumnName = "id")
    private List<Reference> endpoint = new ArrayList<Reference>();

    @Column(name="im_seriesuid")
    private String seriesuid;

    @Unsigned
    @Column(name="im_seriesnumber")
    private Integer seriesnumber;

    @Column(name="im_frameofreferenceuid")
    private String frameofreferenceuid;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ref_im_idb", referencedColumnName = "id")
    private CodeableReference bodysite;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ref_im_idf", referencedColumnName = "id")
    private List<Reference> focus = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ins_im_id", referencedColumnName = "id")
    private List<Instance> instance = new ArrayList<Instance>();

}
