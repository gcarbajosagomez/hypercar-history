package com.phistory.data.model.picture;

import com.phistory.data.model.GenericEntity;
import com.phistory.data.model.car.Car;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.sql.Blob;

import static com.phistory.data.model.car.Car.CAR_ID_FIELD;
import static javax.persistence.GenerationType.AUTO;
import static org.hibernate.annotations.CascadeType.SAVE_UPDATE;

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
@Table(name = Picture.PICTURE_TABLE_NAME)

public class Picture implements GenericEntity {

    public static final String PICTURE_TABLE_NAME             = "picture";
    public static final String PICTURE_ID_FIELD               = "picture_id";

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = PICTURE_ID_FIELD)
    private Long id;

    @ManyToOne
    @Cascade(value = SAVE_UPDATE)
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

        Long carId = this.getCar().getId();
        if (carId != null) {
            builder.append(" (carId: ")
                   .append(carId)
                   .append(")");
        }

        return builder.toString();
    }
}
