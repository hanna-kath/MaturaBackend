package at.spengergasse.spengermed.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "pa_page")
public class Page extends BackboneElement{

    public enum Generation{
        html, markdown, xml, generated
    }

    @Column(name = "pa_source_url")
    private String sourceUrl;

    @Column(name = "pa_source_string")
    private String sourceString;

    @Column(name = "pa_source_markdown")
    private String sourceMarkdown;

    @NotNull
    @Column(name = "pa_name")
    private String name;

    @NotNull
    @Column(name = "pa_title")
    private String title;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "pa_generation", nullable = false)
    private Generation generation;

    //page?
    @ElementCollection
    @CollectionTable(name = "pas_pages")
    @Builder.Default
    private List<String> page = new ArrayList<>();

    //anchor?
    @ElementCollection
    @CollectionTable(name = "pas_anchor")
    @Builder.Default
    private List<String> anchor = new ArrayList<>();

}
