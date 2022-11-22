package at.spengergasse.spengermed.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "no_nutritionorder")
@SuperBuilder
@AllArgsConstructor
public class NutritionOrder extends DomainResource{

    public enum IntentCode {
        proposal, plan, directive, order, originalorder,reflexorder, fillerorder, instanceorder, option
    }

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "i_no_id", referencedColumnName = "id")
    @Builder.Default
    private List<Identifier> identifier = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "no_instantiates")
    private List<String> instantiates = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "no_intent", nullable = false)
    @NotNull
    private NutritionOrder.IntentCode intent;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "no_re_patient")
    private Reference patient;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "no_od_oraldiet")
    private OralDiet oralDiet;



}
