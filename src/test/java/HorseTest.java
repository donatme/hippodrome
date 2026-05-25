import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mockStatic;

class HorseTest {
    @Test
    void constructor_NullNameParamPassed_ThrowsIllegalArgumentException() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1, 2));
        assertEquals("Name cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "  ", "\n", "\n\n", "\n \n", "\t", "\t\t", "\t \t"})
    void constructor_EmptyNameParamPassed_ThrowsIllegalArgumentException(String name) {
        String expected = "Name cannot be blank.";
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse(name, 1, 2));
        assertEquals(expected, exception.getMessage());
    }

    @Test
    void constructor_NegativeSpeedParamPassed_ThrowsIllegalArgumentException() {
        String expected = "Speed cannot be negative.";
        String testName = "Test Name";
        double speed = -5;
        double distance = 1;

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse(testName, speed, distance));
        assertEquals(expected, exception.getMessage());
    }

    @Test
    void constructor_NegativeDistanceParamPassed_ThrowsIllegalArgumentException() {
        String expected = "Distance cannot be negative.";
        String testName = "Test Name";
        double speed = 5;
        double distance = -1;

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse(testName, speed, distance));
        assertEquals(expected, exception.getMessage());
    }

    @Test
    void getName_ReturnsCorrectName() {
        String testName = "Test Name";
        double speed = 5;
        double distance = 1;
        Horse horse = new Horse(testName, speed, distance);
        String actualName = horse.getName();
        assertEquals(testName, actualName);
    }

    @Test
    void getSpeed_ReturnsCorrectSpeed() {
        String testName = "Test Name";
        double speed = 1;
        double distance = 8;
        Horse horse = new Horse(testName, speed, distance);
        double actualSpeed = horse.getSpeed();
        assertEquals(speed, actualSpeed);
    }

    @Test
    void getDistance_returnsCorrectDistance() {
        String testName = "Test Name";
        double speed = 3;
        double distance = 7;
        Horse horse = new Horse(testName, speed, distance);
        double actualDistance = horse.getDistance();
        assertEquals(distance, actualDistance);
    }

    @Test
    void move_CallsGetRandomDoubleMethodWithCorrectsParams() {
        try (MockedStatic<Horse> horseMockedStatic = mockStatic(Horse.class);) {
            Horse horse = new Horse("TestName", 1, 2);
            horse.move();
            horseMockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.2, 0.3, 0.5, 0.8, 0.9, 0, 150})
    void move_FormulaIsCorrect(double fakeRandomValue) {
        String name = "TestName";
        double min = 0.2;
        double max = 0.9;
        double speed = 2.5;
        double distance = 230;
        Horse horse = new Horse(name, speed, distance);
        double expectedDistance = distance + speed * fakeRandomValue;
        try (MockedStatic<Horse> horseMockedStatic = mockStatic(Horse.class)) {
            horseMockedStatic.when(() -> Horse.getRandomDouble(min, max)).thenReturn(fakeRandomValue);
            horse.move();
            assertEquals(expectedDistance, horse.getDistance());
        }
    }
}
