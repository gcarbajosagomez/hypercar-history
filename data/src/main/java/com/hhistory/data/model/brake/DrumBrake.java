package com.hhistory.data.model.brake;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import static com.hhistory.data.model.brake.BrakeType.*;

@Entity
@DiscriminatorValue(value = "drum")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class DrumBrake extends Brake {

    public DrumBrake(Long id,
                     BrakeTrain train) {
        super(id, train);
    }

    @Override
    public BrakeType getType() {
        return DRUM;
    }
}
