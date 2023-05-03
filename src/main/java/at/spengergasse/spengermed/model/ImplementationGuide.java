package at.spengergasse.spengermed.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "ig_implementation_guide")
public class ImplementationGuide extends DomainResource{

    public enum Status{
        draft, active, retired, unknown
    }

    public enum License{
        OBSD, AAL, AML, Borceux
    }

    public enum FhirVersion{
        V1, V2, V3, V4
    }

    @NotNull
    @Column(name = "ig_url")
    private String url;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="i_ig_id", referencedColumnName = "id")
    private List<Identifier> identifier = new ArrayList<>();

    @Column(name = "ig_version")
    private String version;

    @Column(name = "ig_version_algorithm_string")
    private String versionAlgorithmString;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ig_co_version_algorithm_coding")
    private Coding versionAlgorithmCoding;

    @NotNull
    @Column(name = "ig_name")
    private String name;

    @Column(name = "ig_title")
    private String title;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "ig_status", nullable = false)
    private Status status;

    @Column(name="ig_experimental")
    private Boolean experimental;

    @Column(name = "ig_date")
    private LocalDate date;

    @Column(name = "ig_publisher")
    private String publisher;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="cd_ig_id", referencedColumnName = "id")
    private List<ContactDetail> contact = new ArrayList<>();

    @Column(name = "ig_description")
    private String description;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="uc_ig_id", referencedColumnName = "id")
    private List<UsageContext> useContext = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="cc_ig_id", referencedColumnName = "id")
    private List<CodeableConcept> jurisdication = new ArrayList<>();

    @Column(name = "ig_purpose")
    private String purpose;

    @Column(name = "ig_copyright")
    private String copyright;

    @Column(name = "ig_copyright_label")
    private String copyrightLabel;

    @Column(name = "ig_package_id")
    private String packageId;

    @Enumerated(EnumType.STRING)
    @Column(name = "ig_license")
    private License license;

    @NotNull
    @Enumerated(EnumType.STRING)
    @ElementCollection
    @CollectionTable(name = "ig_fhir_version")
    @Builder.Default
    private List<FhirVersion> fhirVersion = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="do_ig_id", referencedColumnName = "id")
    private List<DependsOn> dependsOn = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="gl_ig_id", referencedColumnName = "id")
    private List<Global> global = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="ig_df_id", referencedColumnName = "id")
    private Definition definition;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ig_ma_manifest")
    private Manifest manifest;


}
