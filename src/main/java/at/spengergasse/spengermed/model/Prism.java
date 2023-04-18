package at.spengergasse.spengermed.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "pr_prism")
@SuperBuilder
@AllArgsConstructor
public class Prism  extends BackboneElement{

    @NotNull
    @Column(name = "pr_amount")
    private Float amount;

    public enum BaseCode {
        up,down,in,out
    }
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "pr_base")
    private Prism.BaseCode base;
}

