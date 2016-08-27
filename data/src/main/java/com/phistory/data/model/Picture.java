package com.phistory.data.model;

import com.phistory.data.model.car.Car;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
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
public class Picture implements Serializable, GenericObject
{	
	public enum PictureType
	{
		PREVIEW_PICTURE,
		PICTURE
	}

    private static final long serialVersionUID = -4504149271758708940L;
    //
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
		return null;
	}
}
