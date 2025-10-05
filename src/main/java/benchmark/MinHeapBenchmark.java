package benchmark;

import algorithms.MinHeap;
import metrics.PerformanceTracker;
import org.openjdk.jmh.annotations.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class MinHeapBenchmark {

    @Param({"1000", "10000", "50000"})
    private int size;

    private int[] data;
    private PerformanceTracker tracker;

    @Setup(Level.Trial)
    public void setup() {
        tracker = new PerformanceTracker();
        data = new int[size];
        Random random = new Random(42);
        for (int i = 0; i < size; i++) {
            data[i] = random.nextInt(size);
        }
    }

    @Benchmark
    public void benchmarkInsert() {
        MinHeap heap = new MinHeap(size, tracker);
        for (int value : data) {
            heap.insert(value);
        }
    }

    @Benchmark
    public void benchmarkExtractMin() {
        MinHeap heap = new MinHeap(size, tracker);
        for (int value : data) {
            heap.insert(value);
        }
        while (!heap.isEmpty()) {
            heap.extractMin();
        }
    }

    @TearDown(Level.Trial)
    public void tearDown() {
        System.out.println("=== Tracker Summary ===");
        System.out.println(tracker.toString());
    }
}
