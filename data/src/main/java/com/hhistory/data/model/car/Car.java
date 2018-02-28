package com.hhistory.data.model.car;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hhistory.data.model.GenericEntity;
import com.hhistory.data.model.Manufacturer;
import com.hhistory.data.model.brake.BrakeSet;
import com.hhistory.data.model.engine.Engine;
import com.hhistory.data.model.transmission.DriveWheelType;
import com.hhistory.data.model.transmission.Transmission;
import com.hhistory.data.model.tyre.TyreSet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.lucene.analysis.core.KeywordTokenizerFactory;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.es.SpanishAnalyzer;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilterFactory;
import org.apache.lucene.analysis.ngram.NGramFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Parameter;

import javax.persistence.*;
import java.text.Normalizer;
import java.util.Calendar;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.EnumType.ORDINAL;
import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.TemporalType.DATE;

/**
 * @author Gonzalo
 */
@Entity
@Table(name = Car.CAR_TABLE_NAME,
        uniqueConstraints = @UniqueConstraint(columnNames =
                {
                        Car.MANUFACTURER_ID_FIELD,
                        Car.MODEL_FIELD,
                        Car.ENGINE_ID_FIELD
                }))
@Indexed
@AnalyzerDefs({
        @AnalyzerDef(name = Car.CAR_MODEL_ANALYZER_NAME,
                tokenizer = @TokenizerDef(factory = KeywordTokenizerFactory.class),
                filters = {
                        // Normalize token text to lowercase, as the user is unlikely to care about casing when searching for matches
                        @TokenFilterDef(factory = LowerCaseFilterFactory.class),
                        // Accent insensitive
                        @TokenFilterDef(factory = ASCIIFoldingFilterFactory.class),
                        // Index partial words starting at the front, so we can provide Autocomplete functionality
                        @TokenFilterDef(factory = NGramFilterFactory.class, params = {
                                @Parameter(name = "minGramSize", value = "3"),
                                @Parameter(name = "maxGramSize", value = "100")
                        })
                }),
        @AnalyzerDef(name = Car.CAR_DESCRIPTION_ANALYZER_NAME,
                tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
                filters = {
                        // Normalize token text to lowercase, as the user is unlikely to care about casing when searching for matches
                        @TokenFilterDef(factory = LowerCaseFilterFactory.class),
                        // Accent insensitive
                        @TokenFilterDef(factory = ASCIIFoldingFilterFactory.class)
                })})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Car implements GenericEntity {

    public static final String CAR_TABLE_NAME                      = "car";
    public static final String CAR_ID_FIELD                        = "car_id";
    public static final String MODEL_FIELD                         = "car_model";
    public static final String MANUFACTURER_ID_FIELD               = "car_manufacturer_id";
    public static final String ENGINE_ID_FIELD                     = "car_engine_id";
    public static final String CAR_VISIBLE_PROPERTY_NAME           = "visible";
    public static final String MODEL_PROPERTY_NAME                 = "model";
    public static final String DESCRIPTION_ES_PROPERTY_NAME        = "descriptionES";
    public static final String DESCRIPTION_EN_PROPERTY_NAME        = "descriptionEN";
    public static final String PRODUCTION_START_DATE_PROPERTY_NAME = "productionStartDate";
    public static final String CAR_MODEL_ANALYZER_NAME             = "carModelAnalyzer";
    public static final String CAR_DESCRIPTION_ANALYZER_NAME       = "carDescriptionAnalyzer";

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = CAR_ID_FIELD)
    private Long id;

    @Column(name = "car_visible", nullable = false, columnDefinition = "tinyint(1) default 1")
    private Boolean visible;

    @ManyToOne(cascade = PERSIST)
    @JoinColumn(name = MANUFACTURER_ID_FIELD, nullable = false)
    private Manufacturer manufacturer;

    @Column(name = MODEL_FIELD, nullable = false)
    @Field
    @Analyzer(definition = CAR_MODEL_ANALYZER_NAME)
    private String model;

    @Column(name = "car_engine_layout", nullable = false)
    private CarEngineLayout engineLayout;

    @Column(name = "car_engine_disposition", nullable = false)
    private CarEngineDisposition engineDisposition;

    @ManyToOne(cascade = PERSIST)
    @JoinColumn(name = ENGINE_ID_FIELD)
    //Do not change nullable to false as otherwise Hibernate won't be able to persist the entity
    //since right before persisting it the engine is null
    private Engine engine;

    @Column(name = "car_chassis_materials")
    private String chassisMaterials;

    @Column(name = "car_body_materials")
    private String bodyMaterials;

    @Enumerated(ORDINAL)
    @Column(name = "car_body_shape", nullable = false)
    private CarBodyShape bodyShape;

    @Enumerated(ORDINAL)
    @Column(name = "car_seats_config", nullable = false)
    private CarSeatsConfig carSeatsConfig;

    @Column(name = "car_top_speed")
    private Integer topSpeed;

    @Column(name = "car_acceleration")
    private Float acceleration;

    @Column(name = "car_fuel_consumption")
    private Float fuelConsumption;

    @Enumerated(ORDINAL)
    @Column(name = "car_production_type", nullable = false)
    private CarProductionType productionType;

    @Column(name = "car_production_start_date", nullable = false)
    @Temporal(DATE)
    @Field(name = "productionStartDate", index = Index.YES, analyze = Analyze.NO, store = Store.YES)
    @SortableField(forField = "productionStartDate")
    private Calendar productionStartDate;

    @Column(name = "car_production_end_date")
    @Temporal(DATE)
    private Calendar productionEndDate;

    @Column(name = "car_weight")
    private Long weight;

    @Column(name = "car_length")
    private Long length;

    @Column(name = "car_width")
    private Long width;

    @Column(name = "car_height")
    private Long height;

    @Column(name = "car_wheelbase")
    private Long wheelbase;

    @OneToOne(orphanRemoval = true, cascade = ALL)
    @JoinColumn(name = "car_brake_set_id", unique = true)
    private BrakeSet brakeSet;

    @OneToOne(cascade = ALL)
    @JoinColumn(name = "car_transmission_id", unique = true)
    private Transmission transmission;

    @Column(name = "car_fuel_tank_capacity")
    private Long fuelTankCapacity;

    @OneToOne(cascade = ALL)
    @JoinColumn(name = "car_tyre_set_id", unique = true)
    private TyreSet tyreSet;

    @Enumerated(ORDINAL)
    @Column(name = "car_drive_wheel_type", nullable = false)
    private DriveWheelType driveWheelType;

    @Column(name = "car_road_legal", nullable = false, columnDefinition = "tinyint(1) default 0")
    private Boolean roadLegal;

    @Field
    @Analyzer(definition = CAR_DESCRIPTION_ANALYZER_NAME, impl = SpanishAnalyzer.class)
    @Column(name = "car_description_es", columnDefinition = "LONGTEXT")
    private String descriptionES;

    @Field
    @Analyzer(definition = CAR_DESCRIPTION_ANALYZER_NAME, impl = EnglishAnalyzer.class)
    @Column(name = "car_description_en", columnDefinition = "LONGTEXT")
    private String descriptionEN;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        if (this.getManufacturer() != null) {
            return new StringBuilder().append(this.getManufacturer().getName())
                                      .append(" ")
                                      .append(this.getModel())
                                      .append(" (id: ")
                                      .append(this.id)
                                      .append(")")
                                      .toString();
        }

        return null;
    }

    @JsonProperty
    public String getNormalizedModelName() {
        return Car.normalizeModelName(this.getModel());
    }

    public static String normalizeModelName(String modelName) {
        modelName = Normalizer.normalize(modelName, Normalizer.Form.NFD);
        modelName = modelName.toLowerCase()
                             .trim()
                             .replaceAll("\\s", "-")
                             .replaceAll("\\p{M}", "");
        return modelName;
    }
}
