package analyzer;

import auxiliary.Callback;
import auxiliary.PatternInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class Analyzer {

    private final AnalyzerAlgorithm algorithm;

    public Analyzer(String analyzerType) {
        switch (analyzerType) {
            case "--KMP" -> algorithm = new KMPAnalyzer();
            case "--RK" -> algorithm = new RKAnalyzer();
            default -> algorithm = new NaiveAnalyzer();
        }
    }

    public Analyzer(AnalyzerAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    public Callback analyze(String pattern, String patternName, String text) {
        return algorithm.analyze(pattern, text, patternName);
    }

    public void analyzeFile(File file, List<PatternInfo> patterns) {
        try (FileInputStream fis = new FileInputStream(file)) {
            String text = new String(fis.readAllBytes());
            String message = "Unknown file type";
            for (PatternInfo e : patterns) {
                Callback callback = analyze(e.getPattern(), e.getName(), text);
                if (callback.isSuccess()) {
                    message = callback.getMessage();
                    break;
                }
            }
            System.out.println(file.getName() + ": " + message);
        } catch (IOException ignored) {}
    }
}
