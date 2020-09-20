package analyzer;

import auxiliary.Callback;

public class KMPAnalyzer implements AnalyzerAlgorithm {

    @Override
    public Callback analyze(String pattern, String text, String patternName) {
        if ("".equals(pattern) || text.length() < pattern.length()) return new Callback("Unknown file type", false);

        char[] patternCA = pattern.toCharArray();
        char[] textCA = text.toCharArray();
        int[] patternPrefix = prefixFunc(patternCA);

        for (int i = 0; i < textCA.length - patternCA.length + 1;) {
            for (int j = 0; j < patternCA.length; j++) {
                if (patternCA[j] != textCA[i + j]) {
                    i += j > 0 ? j - patternPrefix[j - 1] : 1;
                    break;
                }
                if (j == patternCA.length - 1) {
                    return new Callback(patternName, true);
                }
            }
        }

        return new Callback("Unknown file type", false);
    }

    private static int[] prefixFunc(char[] s) {
        int[] patternPrefix = new int[s.length];
        for (int i = 1; i < s.length; i++) {
            patternPrefix[i] = s[patternPrefix[i - 1]] == s[i] ? patternPrefix[i - 1] + 1: s[0] == s[i] ? 1: 0;
        }
        return patternPrefix;
    }
}
