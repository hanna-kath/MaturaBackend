package at.spengergasse.spengermed.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "de_definition")
public class Definition extends BackboneElement {

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="gr_de_id", referencedColumnName = "id")
    private List<Grouping> grouping = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="rs_de_id", referencedColumnName = "id")
    private List<ResourceBB> resource = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="de_pa_id", referencedColumnName = "id")
    private Page page;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="par_de_id", referencedColumnName = "id")
    private List<Parameter> parameter = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="te_de_id", referencedColumnName = "id")
    private List<Template> template = new ArrayList<>();
}
