package analyzer;

import auxiliary.Callback;

public interface AnalyzerAlgorithm {
    Callback analyze(String pattern, String text, String patternName);
}
