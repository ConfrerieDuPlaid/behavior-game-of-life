package gameoflife;

import java.util.*;
import java.util.stream.Stream;

final class Position {
    private static final Map<Integer, Position> _cache = new HashMap<>();

    private final Integer x;
    private final Integer y;

    private Position(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public Integer x() {
        return x;
    }

    public Integer y() {
        return y;
    }

    public static Position at(int x, int y) {
        final int hash = Objects.hash(x, y);
        if(!Position._cache.containsKey(hash)) Position._cache.put(hash, new Position(x,y));
        return Position._cache.get(hash);
    }

    /**
     * @return the 8 positions around this position (self not included)
     */
    public Stream<Position> positionsAround() {
        // [x-1,y-1] | [x,y-1] | [x+1,y-1]
        // [x-1,  y] | ~~~~~~~ | [x+1,  y]
        // [x-1,y+1] | [x,y+1] | [x+1,y+1]
        return Stream.of(
                Position.at(x-1, y-1), Position.at(x, y-1), Position.at(x+1, y-1),
                Position.at(x-1, y), Position.at(x+1, y),
                Position.at(x-1, y+1), Position.at(x, y+1), Position.at(x+1, y+1)
        );
    }

    @Override
    public String toString() {
        return String.format("[%d,%d]", x, y);
    }

    //<editor-fold desc="Equals & HashCode">
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x.equals(position.x) && y.equals(position.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
    //</editor-fold>
}
