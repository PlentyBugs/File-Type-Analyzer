package analyzer;

import auxiliary.Callback;

import java.util.ArrayList;
import java.util.List;

public class RKAnalyzer implements AnalyzerAlgorithm {

    @Override
    public Callback analyze(String pattern, String text, String patternName) {
        if ("".equals(pattern) || text.length() < pattern.length()) return new Callback("Unknown file type", false);

        List<Integer> occurrences = analyze(text, pattern);

        return occurrences.size() == 0 ? new Callback("Unknown file type", false): new Callback(patternName, true);
    }

    private static long charToLong(char ch) {
        return ch + 1;
    }

    private static List<Integer> analyze(String text, String pattern) {
        int a = 49;
        long m = 1_000_000_000 + 9;

        long patternHash = 0;
        long currSubstrHash = 0;
        long pow = 1;

        for (int i = 0; i < pattern.length(); i++) {
            patternHash += charToLong(pattern.charAt(i)) * pow;
            patternHash %= m;

            currSubstrHash += charToLong(text.charAt(text.length() - pattern.length() + i)) * pow;
            currSubstrHash %= m;

            if (i != pattern.length() - 1) {
                pow = pow * a % m;
            }
        }

        List<Integer> occurrences = new ArrayList<>();

        for (int i = text.length(); i >= pattern.length(); i--) {
            if (patternHash == currSubstrHash) {
                boolean patternIsFound = true;

                for (int j = 0; j < pattern.length(); j++) {
                    if (text.charAt(i - pattern.length() + j) != pattern.charAt(j)) {
                        patternIsFound = false;
                        break;
                    }
                }

                if (patternIsFound) {
                    occurrences.add(i - pattern.length());
                }
            }

            if (i > pattern.length()) {
                currSubstrHash = (currSubstrHash - charToLong(text.charAt(i - 1)) * pow % m + m) * a % m;
                currSubstrHash = (currSubstrHash + charToLong(text.charAt(i - pattern.length() - 1))) % m;
            }
        }
        return occurrences;
    }
}
