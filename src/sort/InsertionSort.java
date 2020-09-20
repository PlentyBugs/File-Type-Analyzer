package sort;

public class InsertionSort implements Sort {

    @Override
    public <E extends Comparable<E>> E[] sort(E[] v) {
        for (int i = 1; i < v.length; i++) {
            E elem = v[i];
            int j = i - 1;
            while (j >= 0 && v[j].compareTo(elem) < 0)
                v[j + 1] = v[j--];
            v[j + 1] = elem;
        }
        return v;
    }
}
