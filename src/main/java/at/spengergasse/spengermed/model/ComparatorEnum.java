package at.spengergasse.spengermed.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ComparatorEnum {

    LESS("<"), LESS_EQUAL("<="), GREATER_EQUAL(">="), GREATER("<");

    void fn(){
        LESS.getStringValue();
    }

    String stringValue;

}
