import analyzer.Analyzer;
import auxiliary.Callback;
import auxiliary.PatternInfo;
import sort.MergeSort;
import sort.Sort;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        File file = new File(args.length > 0 ? args[0] : "");

        Analyzer analyzer = new Analyzer("--RK");

        List<PatternInfo> patterns = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(args.length > 1 ? args[1] : "")) {
            String text = new String(fis.readAllBytes());
            Matcher matcher = Pattern.compile("(\\d+?);\"(.+?)\";\"(.+?)\"").matcher(text);
            while (matcher.find()) {
                patterns.add(new PatternInfo(Integer.parseInt(matcher.group(1)), matcher.group(2), matcher.group(3)));
            }
        } catch (IOException ignored) {}

        PatternInfo[] pairs = new PatternInfo[patterns.size()];
        for (int i = 0; i < pairs.length; i++) {
            pairs[i] = patterns.get(i);
        }
        Sort sort = new MergeSort();
        sort.sort(pairs);
        List<PatternInfo> patternsFinal = List.of(pairs);

        Queue<File> files = new LinkedList<>();
        files.offer(file);

        ExecutorService exec = Executors.newCachedThreadPool();

        while (!files.isEmpty()) {
            File f = files.poll();
            File[] fs = f.listFiles();
            if (fs == null) continue;
            for (File f3 : fs) {
                if (f3.isDirectory()) {
                    files.offer(f3);
                } else {
                    exec.submit(() -> analyzeFile(f3, patternsFinal, analyzer));
                }
            }
        }
        exec.awaitTermination(10, TimeUnit.SECONDS);
    }

    private static void analyzeFile(File file, List<PatternInfo> patterns, Analyzer analyzer) {
        try (FileInputStream fis = new FileInputStream(file)) {
            String text = new String(fis.readAllBytes());
            String message = "Unknown file type";
            for (PatternInfo e : patterns) {
                Callback callback = analyzer.analyze(e.getPattern(), e.getName(), text);
                if (callback.isSuccess()) {
                    message = callback.getMessage();
                    break;
                }
            }
            System.out.println(file.getName() + ": " + message);
        } catch (IOException ignored) {}
    }
}
