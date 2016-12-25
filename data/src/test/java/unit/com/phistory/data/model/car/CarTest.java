package unit.com.phistory.data.model.car;

import com.phistory.data.model.car.Car;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by Gonzalo Carbajosa on 25/12/16.
 */
public class CarTest {
    private static final String IRRELEVANT_MODEL_NAME_WITH_WHITE_SPACES = "irrelevant Model Name";
    private static final String IRRELEVANT_MODEL_NAME_WITHOUT_WHITE_SPACES = " irrelevantModelName ";
    private static final String IRRELEVANT_ALREADY_NORMALIZED_MODEL_NAME = "irrelevant-model-name";

    @DataProvider(name = "cars")
    public Object[][] getCars() {
        Car irrelevantCar = new Car()
                .builder()
                .model(IRRELEVANT_MODEL_NAME_WITH_WHITE_SPACES)
                .build();

        Car irrelevantCar2 = new Car()
                .builder()
                .model(IRRELEVANT_MODEL_NAME_WITHOUT_WHITE_SPACES)
                .build();

        Car irrelevantCar3 = new Car()
                .builder()
                .model(IRRELEVANT_ALREADY_NORMALIZED_MODEL_NAME)
                .build();

        return new Object[][]{
                {irrelevantCar, "irrelevant-model-name"},
                {irrelevantCar2, "irrelevantmodelname"},
                {irrelevantCar3, IRRELEVANT_ALREADY_NORMALIZED_MODEL_NAME}
        };
    }

    @Test(dataProvider = "cars")
    public void testGetNormalizedModelString(Car car, String expectedNormalizedModelName) {
        assertThat(car.getNormalizedModelName(), is(expectedNormalizedModelName));
    }
}
