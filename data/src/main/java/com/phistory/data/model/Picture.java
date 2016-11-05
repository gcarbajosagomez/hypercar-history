package com.phistory.data.model;

import com.phistory.data.model.car.Car;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.sql.Blob;

import static javax.persistence.EnumType.ORDINAL;
import static javax.persistence.GenerationType.AUTO;
import static org.hibernate.annotations.CascadeType.SAVE_UPDATE;

/**
 *main.java.
 * @author Gonzalo
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "picture",
       uniqueConstraints = @UniqueConstraint(columnNames = {"picture_id", "car_id", "picture_type"}))
public class Picture implements GenericObject
{	
	public enum PictureType
	{
		PREVIEW_PICTURE,
		PICTURE
	}

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "picture_id")
    private Long id;

    @ManyToOne
    @Cascade(value = SAVE_UPDATE)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @Column(name = "img", nullable = false)
    @Lob
    private Blob image;

    @Column(name = "picture_type", nullable = false)
    @Enumerated(ORDINAL)
    private PictureType type = PictureType.PICTURE;

    @Override
    public Long getId() {
        return id;
    }

	@Override
	public String getFriendlyName() {
		return new StringBuilder("Picture - ").append(this.type.toString()).append(" ").append(" (carId: ").append(this.car.getId()).append(")").toString();
	}
}
