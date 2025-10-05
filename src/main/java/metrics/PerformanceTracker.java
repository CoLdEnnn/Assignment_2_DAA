package metrics;

import java.io.FileWriter;
import java.io.IOException;

public class PerformanceTracker {
    private long comparisons = 0;
    private long swaps = 0;
    private long arrayAccesses = 0;
    private long memoryAllocations = 0;

    public void incrementComparisons() {
        comparisons++;
    }

    public void incrementSwaps() {
        swaps++;
    }

    public void incrementArrayAccesses() {
        arrayAccesses++;
    }

    public void incrementMemoryAllocations() {
        memoryAllocations++;
    }

    public long getComparisons() {
        return comparisons;
    }

    public long getSwaps() {
        return swaps;
    }

    public long getArrayAccesses() {
        return arrayAccesses;
    }

    public long getMemoryAllocations() {
        return memoryAllocations;
    }

    public void reset() {
        comparisons = 0;
        swaps = 0;
        arrayAccesses = 0;
        memoryAllocations = 0;
    }

    @Override
    public String toString() {
        return String.format(
                "Comparisons: %d, Swaps: %d, Array Accesses: %d, Memory Allocations: %d",
                comparisons, swaps, arrayAccesses, memoryAllocations
        );
    }

    public void printSummary() {
        System.out.println("\n=== Performance Summary ===");
        System.out.println(toString());
    }

    public void exportToCSV(String fileName) throws IOException {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("comparisons,swaps,arrayAccesses,memoryAllocations\n");
            writer.write(
                    comparisons + "," +
                            swaps + "," +
                            arrayAccesses + "," +
                            memoryAllocations + "\n"
            );
        }
    }
}
