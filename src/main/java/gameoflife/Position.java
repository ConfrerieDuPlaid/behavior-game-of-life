package gameoflife;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

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

    public static Optional<Position> of(Integer x, Integer y) {
        try{
            int key = Objects.hash(Objects.requireNonNull(x), Objects.requireNonNull(y));
            if(!Position._cache.containsKey(key)) Position._cache.put(key, new Position(x,y));
            return Optional.ofNullable(Position._cache.get(key));
        }
        catch (NullPointerException npe) { return Optional.empty(); }
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
