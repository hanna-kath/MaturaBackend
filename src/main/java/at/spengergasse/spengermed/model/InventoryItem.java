package at.spengergasse.spengermed.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "ii_inventoryitem")
public class InventoryItem extends DomainResource{

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "i_ii_id", referencedColumnName = "id")
    @Builder.Default
    private List<Identifier> identifier = new ArrayList<>();

    public enum StatusCode {
        active,
        inactive,
        entered_in_error,
        unknown
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "ii_status", nullable = false)
    private StatusCode status;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cc_ii_category_id", referencedColumnName = "id")
    private List<CodeableConcept> category = new ArrayList<CodeableConcept>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cc_ii_code_id", referencedColumnName = "id")
    private List<CodeableConcept> code = new ArrayList<CodeableConcept>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "na_ii_id", referencedColumnName = "id")
    private List<Name> name = new ArrayList<Name>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ro_ii_id", referencedColumnName = "id")
    private List<ResponsibleOrganization> responsibleOrganization = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ii_description", referencedColumnName = "id")
    private Description description;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cc_ii_iStatus_id", referencedColumnName = "id")
    private List<CodeableConcept> inventoryStatus = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cc_ii_baseUnit_id", referencedColumnName = "id")
    private CodeableConcept baseUnit;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "qu_ii_id", referencedColumnName = "id")
    private Money netContent;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "as_ii_id", referencedColumnName = "id")
    private List<Association> association = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ch_ii_id", referencedColumnName = "id")
    private List<Characteristic> characteristic = new ArrayList<>();

    @JoinColumn(name = "in_ii_id", referencedColumnName = "id")
    @OneToOne(cascade = CascadeType.ALL)
    private Instance instance;

    @JoinColumn(name = "re_ii_prod_id", referencedColumnName = "id")
    @OneToOne(cascade = CascadeType.ALL)
    private Reference productReference;

}
