package at.spengergasse.spengermed.model;


import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Table(name = "at_attachment")
public class Attachment extends Element{

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "at_re_id")
    private Reference generalPractitioner;

    public enum ContentCode{
        application, image, multipart, message, audio, video, text, unknown, other;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "at_contenttype")
    private ContentCode contenttype;

    public enum LanguageCode{
        //https://www.hl7.org/fhir/valueset-languages.html folgt noch
        german, english, spanish
    }

    @Enumerated(EnumType.STRING)
    @Column (name = "at_language")
    private LanguageCode language;

    @Column(name="at_data")
    @Lob
    private String data;

    @Column (name = "at_title")
    private String title;

    @Column(name = "at_creation")
    private LocalDateTime creation;

}
