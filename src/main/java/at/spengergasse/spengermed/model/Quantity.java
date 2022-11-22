package at.spengergasse.spengermed.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.net.URI;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "qu_quantity")
@SuperBuilder
@AllArgsConstructor
public class Quantity extends Element {

    @Column(name = "qu_decimal")
    private double decimal;

    public enum Code {
        smaller  ("<"),
        equallsmall  ("<="),
        equallbig   (">="),
        bigger (">");
        private String value;
        private Code(String value) {
            this.value = value;
        }
        public String toString() {
            return this.value;
        }
    }


    @Column(name = "qu_comperator")
    private Code comperator;

    @Column(name = "qu_unit")
    private String unit;

    @Column(name = "qu_sytem")
    private URI system;

    @Column(name = "qu_code")
    private String code;
}
