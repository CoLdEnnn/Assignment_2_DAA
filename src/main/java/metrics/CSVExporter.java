package metrics;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CSVExporter {

    public static void export(String filePath, PerformanceTracker tracker, int inputSize, long timeNano) {
        File file = new File(filePath);
        boolean writeHeader = !file.exists();

        try (FileWriter writer = new FileWriter(file, true)) {
            if (writeHeader) {
                writer.append("InputSize,Time(ns),Comparisons,Swaps,ArrayAccesses,MemoryAllocations\n");
            }

            writer.append(String.format("%d,%d,%d,%d,%d,%d\n",
                    inputSize,
                    timeNano,
                    tracker.getComparisons(),
                    tracker.getSwaps(),
                    tracker.getArrayAccesses(),
                    tracker.getMemoryAllocations()
            ));
        } catch (IOException e) {
            System.err.println("Error writing CSV: " + e.getMessage());
        }
    }
}
