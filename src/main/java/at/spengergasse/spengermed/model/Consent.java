package at.spengergasse.spengermed.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "con_consent")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Consent extends DomainResource{

    public enum StatusCode{
        draft, proposed, active, rejected, inactive, enteredinerror
    }

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="i_con_id", referencedColumnName = "id")
    private List<Identifier> identifier = new ArrayList<Identifier>();

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "con_status", nullable = false)
    private StatusCode status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "at_con_fk")
    private Attachment sourceAttachment;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "re_con_fk")
    private Reference sourceReference;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "po_con_fk", referencedColumnName = "id")
    private List<Policy> policy = new ArrayList<Policy>();

}
