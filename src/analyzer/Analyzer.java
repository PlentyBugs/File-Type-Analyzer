package analyzer;

import auxiliary.Callback;

public class Analyzer {

    private final AnalyzerAlgorithm algorithm;

    public Analyzer(String analyzerType) {
        switch (analyzerType) {
            case "--KMP": algorithm = new KMPAnalyzer(); break;
            case "--RK": algorithm = new RKAnalyzer(); break;
            default: algorithm = new NaiveAnalyzer();
        }
    }

    public Analyzer(AnalyzerAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    public Callback analyze(String pattern, String patternName, String text) {
        return algorithm.analyze(pattern, text, patternName);
    }
}
