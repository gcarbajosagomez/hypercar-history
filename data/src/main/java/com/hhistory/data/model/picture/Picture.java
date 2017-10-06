package com.hhistory.data.model.picture;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hhistory.data.model.GenericEntity;
import com.hhistory.data.model.car.Car;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Blob;
import java.util.Objects;

import static com.hhistory.data.model.car.Car.CAR_ID_FIELD;
import static javax.persistence.GenerationType.AUTO;
import static javax.persistence.GenerationType.IDENTITY;

/**
 * main.java.
 *
 * @author Gonzalo
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties("image")
@Table(name = Picture.PICTURE_TABLE_NAME)

public class Picture implements GenericEntity {

    public static final String PICTURE_TABLE_NAME   = "picture";
    public static final String PICTURE_ID_FIELD     = "picture_id";
    public static final String CAR_ID_PROPERTY_NAME = "carId";

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = PICTURE_ID_FIELD)
    private Long id;

    @ManyToOne
    @JoinColumn(name = CAR_ID_FIELD, nullable = false)
    private Car car;

    @Column(name = "img", nullable = false)
    @Lob
    private Blob image;

    @Column(name = "picture_gallery_position")
    private Integer galleryPosition;

    @Column(name = "picture_eligible_for_preview", columnDefinition = "tinyint(1) default 0")
    private Boolean eligibleForPreview = false;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Picture - ")
                .append("eligible for picture ")
                .append(this.eligibleForPreview)
                .append(" ");

        Car car = this.getCar();
        if (Objects.nonNull(car)) {
            builder.append(" (carId: ")
                   .append(car.getId())
                   .append(")");
        }

        return builder.toString();
    }
}
