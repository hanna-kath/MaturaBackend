package at.spengergasse.spengermed.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "at_availabletime")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AvailableTime extends BackboneElement {

    public enum DayCode{
        mon, tue, wed, thu, fri, sat, sun
    }

    @OneToMany(cascade = CascadeType.ALL, targetEntity = AvailableTime.class)
    @JoinColumn(name = "at_dw_id")
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private List<DayCode> daysofweek = new ArrayList<DayCode>();

    @Column(name = "at_allday")
    private Boolean allDay;

    @Column(name = "at_availablestarttime")
    private LocalTime availableStartTime;

    @Column(name = "at_availableendtime")
    private LocalTime availableEndTime;

}
