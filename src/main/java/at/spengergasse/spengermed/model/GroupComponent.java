package at.spengergasse.spengermed.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Table(name = "gc_groupcomponent")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class GroupComponent extends BackboneElement{

    public enum Code {
        grouped,
        excluded,
        involved
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "gc_type")
    private GroupComponent.Code type;


}
