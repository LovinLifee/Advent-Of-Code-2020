package net.avuna.aoc.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@Getter
public class Matrix2D<T> {

    private final int width;
    private final int height;
    private final T[][] matrix;

    public Matrix2D(int width, int height) {
        this.width = width;
        this.height = height;
        this.matrix = (T[][]) new Object[height][width];
    }
    public Matrix2D(T[][] matrix) {
        this.matrix = matrix;
        this.width = matrix[0].length;
        this.height = matrix.length;
    }

    public static Matrix2D empty() {
        return new Matrix2D(0, 0);
    }

    public void forEach(MatrixCoordinateConsumer<T> consumer, Runnable onRowEnd) {
        for(int y = 0; y < matrix.length; y++) {
            for(int x = 0; x < matrix[y].length; x++) {
                T entry = matrix[y][x];
                consumer.accept(x, y, entry);
            }
            onRowEnd.run();
        }
    }

    public void set(int x, int y, T value) {
        matrix[y][x] = value;
    }

    public T get(int x, int y) {
        return matrix[y][x];
    }
    public T get(Coordinate coordinate) {
        return get(coordinate.getX(), coordinate.getY());
    }


    public void forEach(MatrixCoordinateConsumer<T> consumer) {
        forEach(consumer, () -> {});
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matrix2D<?> matrix2D = (Matrix2D<?>) o;
        return Arrays.deepEquals(getMatrix(), matrix2D.getMatrix());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getWidth(), getHeight());
        result = 31 * result + Arrays.hashCode(getMatrix());
        return result;
    }

    public boolean isInBounds(Coordinate coordinate) {
        return isInBounds(coordinate.getX(), coordinate.getY());
    }

    public boolean isInBounds(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public List<T> getAdjacentElements(int x, int y) {
        List<T> result = new ArrayList<>(8);
        for(Direction direction : Direction.values()) {
            Coordinate position = new Coordinate(x, y);
            position.move(direction);
            if(isInBounds(position)) {
                result.add(matrix[position.getY()][position.getX()]);
            }
        }
        return result;
    }

    public T castRayUntil(int startingX, int startingY, Direction direction, Predicate<IndicieContainer> until) {
        IndicieContainer placeHolder = new IndicieContainer(matrix[startingY][startingX], new Coordinate(startingX, startingY));
        while(!until.test(placeHolder)) {
            placeHolder.setValue(matrix[placeHolder.getCoordinate().getY()][placeHolder.getCoordinate().getX()]);
            placeHolder.getCoordinate().move(direction);
        }
        return placeHolder.getValue();
    }


    @FunctionalInterface
    public interface MatrixCoordinateConsumer<T> {
        void accept(int x, int y, T t);
    }

    @ToString
    @Getter
    @AllArgsConstructor
    public class IndicieContainer {

        @Setter
        private T value;
        private Coordinate coordinate;

    }
}
