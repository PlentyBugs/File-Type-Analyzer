import analyzer.Analyzer;
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

        File sourceFolder = new File(args.length > 0 ? args[0] : "");
        Queue<File> files = new LinkedList<>();
        files.offer(sourceFolder);

        ExecutorService exec = Executors.newCachedThreadPool();

        while (!files.isEmpty()) {
            File fileFromQueue = files.poll();
            File[] listFiles = fileFromQueue.listFiles();
            if (listFiles == null) continue;
            for (File file : listFiles) {
                if (file.isDirectory()) {
                    files.offer(file);
                } else {
                    exec.submit(() -> analyzer.analyzeFile(file, patternsFinal));
                }
            }
        }

        exec.shutdown();
        exec.awaitTermination(10, TimeUnit.SECONDS);
    }
}
