package net.avuna.aoc;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Getter
public class Matrix2D<T> implements Cloneable {

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

    @Override
    public Matrix2D<T> clone() {
        return new Matrix2D<T>(copy2D(matrix));
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

    public void forEach(MatrixCoordinateConsumer<T> consumer) {
        forEach(consumer, () -> {});
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matrix2D<?> matrix2D = (Matrix2D<?>) o;
        return getWidth() == matrix2D.getWidth() && getHeight() == matrix2D.getHeight() && Arrays.deepEquals(getMatrix(), matrix2D.getMatrix());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getWidth(), getHeight());
        result = 31 * result + Arrays.hashCode(getMatrix());
        return result;
    }

    private static final <T> T[][] copy2D(T[][] source) {
        T[][] destination = (T[][]) new Object[source.length][];
        for (int i = 0; i < source.length; ++i) {
            destination[i] = (T[]) new Object[source[i].length];
            System.arraycopy(source[i], 0, destination[i], 0, destination[i].length);
        }
        return destination;
    }

    public List<T> getAdjacentElements(int x, int y) {
        List<T> result = new ArrayList<>(8);
        for (int dx = -1; dx <= 1; ++dx) {
            for(int dy = -1; dy <= 1; ++dy) {
                if (dx != 0 || dy != 0) {
                    try {
                        result.add(this.matrix[y + dy][x + dx]);
                    } catch(IndexOutOfBoundsException e) {

                    }
                }
            }
        }
        return result;
    }

    @FunctionalInterface
    public static interface MatrixCoordinateConsumer<T> {
        void accept(int x, int y, T t);
    }
}
