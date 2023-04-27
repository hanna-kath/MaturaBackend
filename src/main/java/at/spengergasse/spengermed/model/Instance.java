package at.spengergasse.spengermed.model;

import jdk.jfr.Unsigned;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
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
@SuperBuilder
@Table(name = "ins_instance")
public class Instance extends BackboneElement{

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "i_ins_id", referencedColumnName = "id")
    @Builder.Default
    private List<Identifier> identifier = new ArrayList<>();

    @Column(name = "in_expiry")
    private LocalDateTime expiry;

    @NotNull
    @Column(name="ins_uid", nullable = false)
    private String uid;

    @Unsigned
    @Column(name="ins_number")
    private Integer number;

    @JoinColumn(name = "re_in_su_id", referencedColumnName = "id")
    @OneToOne(cascade = CascadeType.ALL)
    private Reference subject;

    @JoinColumn(name = "re_in_lo_id", referencedColumnName = "id")
    @OneToOne(cascade = CascadeType.ALL)
    private Reference location;

//    @NotNull  //je nach angabe
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ins_sopClass")
    private Coding sopClass;

    @ElementCollection
    @CollectionTable(name = "ins_subset")
    @Builder.Default
    private List<String> subset = new ArrayList<String>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ins_ima2d_id", referencedColumnName = "id")
    private List<ImageRegion2D> imageregion2d = new ArrayList<ImageRegion2D>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ins_ima3d_id", referencedColumnName = "id")
    private List<ImageRegion3D> imageregion3d = new ArrayList<ImageRegion3D>();

    @Column(name="ins_title")
    private String title;

}
