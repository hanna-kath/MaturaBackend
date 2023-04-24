package at.spengergasse.spengermed.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "m_money")
public class Money extends Element {
    public enum CurrencyCode {
        USD,
        EUR,
        HKD,
        CHF,
        AED
    }

    @Column(name = "m_value")
    private float value;

    @Enumerated(EnumType.STRING)
    @Column(name="m_currency")
    private CurrencyCode currency;
}
