package gameoflife;

import java.util.*;

public class Position {

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

    public static Position of(Integer x, Integer y) {
        final int hash = Objects.hash(x, y);
        if(!Position._cache.containsKey(hash)) Position._cache.put(hash, new Position(x,y));
        return Position._cache.get(hash);
    }

    /**
     * @return the 8 positions around this position (self not included)
     */
    public List<Position> positionsAround() {
        // [-1,-1] | [ x,-1] | [+1,-1]
        // [-1, y] | ~~~~~~~ | [+1, y]
        // [-1,+1] | [ x,+1] | [+1,+1]
        return List.of(
                Position.of(x-1, y-1), Position.of(x, y-1), Position.of(x+1, y-1),
                Position.of(x-1, y), Position.of(x+1, y),
                Position.of(x-1, y+1), Position.of(x, y+1), Position.of(x+1, y+1)
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
