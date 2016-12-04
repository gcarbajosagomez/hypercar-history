package com.phistory.data.model.car;

import com.phistory.data.model.GenericEntity;
import com.phistory.data.model.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

import static javax.persistence.EnumType.ORDINAL;
import static javax.persistence.FetchType.LAZY;

/**
 * Represents a link to an internet content
 * <p>
 * E.G: A link to a Youtube video, a review article, etc
 * </p> 
 * 
 * @author gonzalo
 *main.java.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = CarInternetContent.CAR_INTERNET_CONTENT_TABLE_NAME)
public class CarInternetContent implements GenericEntity
{
	public static final String CAR_INTERNET_CONTENT_TABLE_NAME = "car_internet_content";
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "content_id")
    private Long id;
	
	@Column(name = "content_link", nullable = false, unique = true)
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

	@ManyToOne(fetch = LAZY)
    @JoinColumn(name = "content_car_id", nullable = false)
    private Car car;

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public String getFriendlyName() {
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
