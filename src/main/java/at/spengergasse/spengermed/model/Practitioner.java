package at.spengergasse.spengermed.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pr_practitioner")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Practitioner extends DomainResource {

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="i_pr_id", referencedColumnName = "id")
    @Builder.Default
    private List<Identifier> identifier = new ArrayList<Identifier>();

    @Column(name = "pr_active")
    private boolean active;

    @OneToMany(cascade = CascadeType.ALL)   //FK bei HumanName; 1:n-Beziehung
    @JoinColumn(name = "hn_pr_id", nullable = true, referencedColumnName = "id")
    @Builder.Default
    private List<HumanName> name = new ArrayList<HumanName>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cp_pr_id", referencedColumnName = "id")
    @Builder.Default
    private List<ContactPoint> telecom = new ArrayList<ContactPoint>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "a_pr_id", referencedColumnName = "id")
    @Builder.Default
    private List<Address> address = new ArrayList<Address>();

    public enum GenderCode{ //Enum ist ein eigener Datentyp
        male, female, other, unknown
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "pr_gender")
    private Patient.GenderCode gender;

    @Column(name = "pr_birthdate")
    private LocalDate birthDate;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "at_practitioner_fk", referencedColumnName = "id")
    @Builder.Default
    private List<Attachment> photo = new ArrayList<Attachment>();

    //Qualification
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "q_practitioner_fk")
    @Builder.Default
    private List<Qualification> qualification = new ArrayList<Qualification>();

    //Communication
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cc_practitioner_fk")
    @Builder.Default
    private List<CodeableConcept> communication = new ArrayList<CodeableConcept>();


}
