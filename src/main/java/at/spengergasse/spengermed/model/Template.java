package at.spengergasse.spengermed.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@SuperBuilder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "te_template")
public class Template extends BackboneElement{

    public enum Code{
        template1, template2, template3, template4
    }

    @NotNull
    @Column(name = "te_source")
    private String source;

    @Column(name = "te_scope")
    private String scope;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name="te_type", nullable = false)
    private Code code;
}
