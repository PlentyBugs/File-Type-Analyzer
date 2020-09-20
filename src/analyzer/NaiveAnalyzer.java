package analyzer;

import auxiliary.Callback;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NaiveAnalyzer implements AnalyzerAlgorithm {
    @Override
    public Callback analyze(String pattern, String text, String patternName) {
        Matcher matcher = Pattern.compile(pattern).matcher(text);
        if (matcher.find()) {
            return new Callback(patternName, true);
        }
        return new Callback("Unknown file type", false);
    }
}
