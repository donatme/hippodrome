import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HippodromeTest {

    @Test
    void constructor_NullListParamTest_ThrowsIllegalArgumentException() {
        String expectedMessage = "Horses cannot be null.";
        List<Horse> horses = null;

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(horses));
        assertEquals(expectedMessage, exception.getMessage());
    }


    @Test
    void constructor_EmptyListParamTest_ThrowsIllegalArgumentException() {
        String expectedMessage = "Horses cannot be empty.";
        List<Horse> horses = new ArrayList<>();

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(horses));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void getHorses_ReturnsListWithAllHorsesInOrder() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horses.add(new Horse("Horse" + i, i, i));
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        assertNotNull(hippodrome.getHorses());
        assertEquals(30, hippodrome.getHorses().size());
        assertEquals("Horse0", hippodrome.getHorses().get(0).getName());
        assertEquals("Horse5", hippodrome.getHorses().get(5).getName());
        assertEquals("Horse11", hippodrome.getHorses().get(11).getName());
    }

    @Test
    void move_CallsMoveMethodForAllHorses() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horses.add(Mockito.mock(Horse.class));
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        hippodrome.move();

        horses.forEach(horse -> {
            Mockito.verify(horse, Mockito.times(1)).move();
        });
    }

    @Test
    void getWinner_ReturnsCorrectWinnerHorse() {
        Hippodrome hippodrome = new Hippodrome(List.of(
                new Horse("Horse1", 1,5),
                new Horse("Horse2", 4,6),
                new Horse("Horse4", 2,9),
                new Horse("Horse7", 3,3)
        ));

        assertEquals("Horse4", hippodrome.getWinner().getName());
    }
}
