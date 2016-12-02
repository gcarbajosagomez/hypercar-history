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
       uniqueConstraints = @UniqueConstraint(columnNames = {CAR_ID_FIELD,
                                                            Picture.PICTURE_TYPE_FIELD,
                                                            Picture.PICTURE_GALLERY_POSITION_FIELD}))

public class Picture implements GenericEntity {

    public static final String PICTURE_ID_FIELD = "picture_id";
    public static final String PICTURE_TYPE_FIELD = "picture_type";
    public static final String PICTURE_GALLERY_POSITION_FIELD = "picture_gallery_position";

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

    @Column(name = "picture_type", nullable = false)
    @Enumerated(ORDINAL)
    private PictureType type = PICTURE;

    @Column(name = "picture_gallery_position")
    private Integer galleryPosition;

    @Column(name = "picture_eligible_for_preview", columnDefinition = "tinyint(1) default 0")
    private Boolean eligibleForPreview = false;

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
