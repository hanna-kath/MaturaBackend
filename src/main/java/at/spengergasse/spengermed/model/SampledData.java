package at.spengergasse.spengermed.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Table(name = "sd_sampleddata")
public class SampledData extends Element{

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sd_m_id", nullable = false)
    @NotNull
    private Money origin;

    @Column(name = "sd_interval")
    private double interval;

    @Column(name = "sd_intervalunit", nullable = false)
    @NotNull
    private String intervalUnit;

    @Column(name = "sd_factor")
    private double factor;

    @Column(name = "sd_lowerlimit")
    private double lowerLimit;

    @Column(name = "sd_upperlimit")
    private double upperLimit;

    @Min(1)
    @Column(name = "sd_dimensions")
    private int dimensions;

    // codeMap as a canonicel

    @Column(name = "sd_offset")
    private String offset;

    @Column(name = "sd_data")
    private String data;
}
