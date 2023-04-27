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
@Table(name = "gl_global")
public class Global extends BackboneElement {

    public enum Type{
        account, appointment, claim, code
    }

    @NotNull
    @Column(name = "gl_profile")
    private String profile;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "gl_type", nullable = false)
    private Type type;
}
