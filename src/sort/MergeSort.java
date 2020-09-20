package sort;

public class MergeSort implements Sort {

    @Override
    public <E extends Comparable<E>> E[] sort(E[] v) {
        sort(v, 0, v.length);
        return v;
    }

    private <E extends Comparable<E>> void sort(E[] a, int left, int right) {
        if (right <= left + 1) return;
        int middle = left + (right - left) / 2;
        sort(a, left, middle);
        sort(a, middle, right);
        merge(a, left, middle, right);
    }

    private <E extends Comparable<E>> void merge(E[] a, int left, int middle, int right) {
        int i = left;
        int j = middle;
        int k = 0;
        Object[] temp = new Object[right - left];
        while (i < middle && j < right)
            if (a[i].compareTo(a[j]) >= 0) temp[k++] = a[i++];
            else temp[k++] = a[j++];
        while (i < middle) temp[k++] = a[i++];
        while (j < right) temp[k++] = a[j++];
        System.arraycopy(temp, 0, a, left, temp.length);
    }
}
