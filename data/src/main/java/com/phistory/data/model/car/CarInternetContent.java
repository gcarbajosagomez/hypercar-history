package com.phistory.data.model.car;

import com.phistory.data.model.GenericEntity;
import com.phistory.data.model.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

import static com.phistory.data.model.car.Car.CAR_ID_FIELD;
import static javax.persistence.EnumType.ORDINAL;

/**
 * Represents a link to an internet content
 * <p>
 * E.G: A link to a Youtube video, a review article, etc
 * </p> 
 * 
 * @author gonzalo
 */
@Entity
@Table(name = CarInternetContent.CAR_INTERNET_CONTENT_TABLE_NAME,
	   uniqueConstraints = @UniqueConstraint(columnNames = {CAR_ID_FIELD, CarInternetContent.CAR_INTERNET_CONTENT_LINK_FIELD}))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarInternetContent implements GenericEntity
{
	public static final String CAR_INTERNET_CONTENT_TABLE_NAME = "car_internet_content";
	public static final String CAR_INTERNET_CONTENT_LINK_FIELD = "content_link";

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "content_id")
    private Long id;
	
	@Column(name = "content_link", nullable = false)
	private String link;
	
	@Enumerated(ORDINAL)
    @Column(name = "content_type", nullable = false)
	private CarInternetContentType type;
	
	@Column(name = "content_added_date")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime addedDate;
	
	@Enumerated(ORDINAL)
    @Column(name = "content_language", nullable = false)
    private Language contentLanguage;

	@ManyToOne
    @JoinColumn(name = CAR_ID_FIELD, nullable = false)
    private Car car;

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder(this.link).append(" ")
																  .append(this.type)
																  .append(" (")
																  .append(this.contentLanguage.getName())
																  .append(")");
		return stringBuilder.toString();
	}

	//method needed for SQL projected query
	public void setCarId(Long carId) {
		if (this.car == null) {
            this.car = new Car();
        }
        this.car.setId(carId);
	}
}
