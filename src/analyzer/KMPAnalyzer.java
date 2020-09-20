package analyzer;

import auxiliary.Callback;

import java.util.ArrayList;
import java.util.List;

public class KMPAnalyzer implements AnalyzerAlgorithm {

    @Override
    public Callback analyze(String pattern, String text, String patternName) {
        if ("".equals(pattern) || text.length() < pattern.length()) return new Callback("Unknown file type", false);

        char[] s = pattern.toCharArray();
        char[] t = text.toCharArray();
        int[] p = prefixFunc(s);

        List<Integer> occurrences = new ArrayList<>();

        int i;
        for (i = 0; i < t.length - s.length + 1;) {
            for (int j = 0; j < s.length; j++) {
                if (s[j] != t[i + j]) {
                    i += j > 0 ? j - p[j - 1] : 1;
                    break;
                }
                if (j == s.length - 1) {
                    occurrences.add(i++);
                }
            }
        }

        return occurrences.size() == 0 ? new Callback("Unknown file type", false): new Callback(patternName, true);
    }

    private static int[] prefixFunc(char[] s) {
        int[] p = new int[s.length];
        for (int i = 1; i < s.length; i++) {
            p[i] = s[p[i - 1]] == s[i] ? p[i - 1] + 1: s[0] == s[i] ? 1: 0;
        }
        return p;
    }
}
