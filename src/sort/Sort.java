package sort;

@FunctionalInterface
public interface Sort {
    <E extends Comparable<E>> E[] sort(E[] v);
}
