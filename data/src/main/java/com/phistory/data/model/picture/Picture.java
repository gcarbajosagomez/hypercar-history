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

import static com.phistory.data.model.picture.PictureType.*;
import static com.phistory.data.model.car.Car.*;
import static com.phistory.data.model.picture.PictureType.*;
import static javax.persistence.EnumType.ORDINAL;
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
@Table(name = "picture",
        uniqueConstraints = @UniqueConstraint(columnNames = {"picture_id", CAR_ID_FIELD, "picture_type"}))

public class Picture implements GenericEntity {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "picture_id")
    private Long id;

    @ManyToOne
    @Cascade(value = SAVE_UPDATE)
    @JoinColumn(name = CAR_ID_FIELD, nullable = false)
    private Car car;

    @Column(name = "img", nullable = false)
    @Lob
    private Blob image;

    @Column(name = "picture_type", nullable = false)
    @Enumerated(ORDINAL)
    private PictureType type = PICTURE;

    @Column(name = "picture_gallery_position", nullable = false, unique = true)
    private Integer galleryPosition;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getFriendlyName() {
        StringBuilder builder = new StringBuilder("Picture - ")
                .append(this.type)
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
