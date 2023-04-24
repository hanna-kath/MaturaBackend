package at.spengergasse.spengermed.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vsd_virtualServiceDetail")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class VirtualServiceDetail extends Element {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vsd_co_fk")
    private Coding channelType;

    @Column(name = "vsd_addressUrl")
    private String addressUrl;

    @Column(name = "vsd_addressString")
    private String addressString;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cp_vsd_id", nullable = true, referencedColumnName = "id")
    @Builder.Default
    private List<ContactPoint> addressContactPoint = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cp_vsd_id", nullable = true, referencedColumnName = "id")
    @Builder.Default
    private List<ExtendedContactDetail> adressExtendedContactDetail = new ArrayList<>();

    @ElementCollection
    @Column(name = "vsd_additionalInfo")
    private List<URL> additionalInfo = new ArrayList<>();

    @Positive
    @Column(name = "vsd_maxParticipants")
    private Integer maxParticipants;

    @Column(name = "vsd_sessionKey")
    private String sessionKey;

}
