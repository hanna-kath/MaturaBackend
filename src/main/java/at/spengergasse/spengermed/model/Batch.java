package at.spengergasse.spengermed.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.lang.Nullable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "b_batch")
@SuperBuilder
public class Batch extends BackboneElement{

    @Nullable
    @Column(name = "b_lotnumber")
    private String lotNumber;

    @Nullable
    @Column(name = "b_expirationdate", nullable = true)
    private LocalDateTime expirationDate;
}
