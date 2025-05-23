package sorts;

import java.util.Comparator;

public interface ISorter<T extends Comparable<T>> {

    T[] sort(T[] array);
    T[] sort(T[] array, Comparator<T> comparator);
}