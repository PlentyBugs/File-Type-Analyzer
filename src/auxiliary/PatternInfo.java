package auxiliary;

public class PatternInfo implements Comparable<PatternInfo> {
    private final int index;
    private final String pattern;
    private final String name;

    public PatternInfo(int index, String pattern, String name) {
        this.index = index;
        this.pattern = pattern;
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public String getPattern() {
        return pattern;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(PatternInfo o) {
        return Integer.compare(index, o.index);
    }
}
