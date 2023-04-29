package gameoflife;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static gameoflife.Position.at;
import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    private final Position position = at(4, -3);;
    @Test
    void shouldCreateNewPosition() {
        assertEquals( 4, position.x());
        assertEquals( -3, position.y());
    }

    @Test
    void shouldGetTheHeightPositionsAroundAPosition() {

        // [3,-4] | [4,-4] | [5,-4]
        // [3,-3] |  4,-3  | [5,-3]
        // [3,-2] | [4,-2] | [5,-2]
        Assertions.assertThat(position.positionsAround().toList())
                .containsExactlyInAnyOrder(
                        at(3,-4),at(4,-4),at(5,-4),
                        at(3,-3),at(5,-3),
                        at(3,-2),at(4,-2),at(5,-2)
                );
    }

    @Test
    void shouldToStringLikePoint() {
        assertEquals("[4,-3]", position.toString());
    }

    @Test
    void twoPointWithSameXAndYShouldBeEqual() {
        assertTrue(at(3,7).equals(at(3,7)));
    }
}