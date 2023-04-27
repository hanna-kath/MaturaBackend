package at.spengergasse.spengermed.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@SuperBuilder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "do_depends_on")
public class DependsOn extends BackboneElement{

    @NotNull
    @Column(name = "do_uri")
    private String uri;

    @Column(name = "do_package_id")
    private String packageId;

    @Column(name = "do_version")
    private String version;

    @Column(name = "do_reason")
    private String reason;
}
