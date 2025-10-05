package cli;

import algorithms.MinHeap;
import metrics.PerformanceTracker;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class BenchmarkRunner {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== MinHeap Benchmark Runner ===");
        System.out.print("Введите размер входных данных (например 10000, 50000, 100000): ");
        int size = scanner.nextInt();

        runBenchmark(size);
    }

    private static void runBenchmark(int size) throws IOException {
        PerformanceTracker tracker = new PerformanceTracker();
        MinHeap heap = new MinHeap(size, tracker);
        Random random = new Random(42);

        System.out.println("▶ Запуск вставки " + size + " элементов...");
        long start = System.currentTimeMillis();

        for (int i = 0; i < size; i++) {
            heap.insert(random.nextInt(size));
        }

        long insertTime = System.currentTimeMillis() - start;
        System.out.println("Вставка завершена за " + insertTime + " мс");

        System.out.println("▶ Извлечение всех элементов...");
        start = System.currentTimeMillis();

        while (!heap.isEmpty()) {
            heap.extractMin();
        }

        long extractTime = System.currentTimeMillis() - start;
        System.out.println("Извлечение завершено за " + extractTime + " мс");

        tracker.printSummary();
        tracker.exportToCSV("benchmark_results.csv");
        System.out.println("Результаты сохранены в benchmark_results.csv");
    }
}
