package algorithms;

import metrics.PerformanceTracker;
import java.util.Arrays;

public class MinHeap {
    private int[] heap;
    private int size;
    private final PerformanceTracker tracker;

    public MinHeap(int capacity, PerformanceTracker tracker) {
        if (capacity <= 0) {
            capacity = 1;
        }
        this.heap = new int[capacity];
        this.size = 0;
        this.tracker = tracker;
        tracker.incrementMemoryAllocations();
    }

    public void insert(int value) {
        tracker.incrementMemoryAllocations();
        if (size == heap.length) {
            heap = Arrays.copyOf(heap, Math.max(1, size * 2));
            tracker.incrementMemoryAllocations();
        }
        heap[size] = value;
        tracker.incrementArrayAccesses();
        size++;
        heapifyUp(size - 1);
    }

    public int extractMin() {
        if (size == 0) {
            throw new IllegalStateException("Heap is empty");
        }

        int min = heap[0];
        tracker.incrementArrayAccesses();

        if (size == 1) {
            size = 0;
            return min;
        }

        heap[0] = heap[size - 1];
        tracker.incrementArrayAccesses();
        size--;
        heapifyDown(0);
        return min;
    }

    public void decreaseKey(int index, int newValue) {
        if (index < 0 || index >= size)
            throw new IllegalArgumentException("Invalid index");
        tracker.incrementComparisons();
        if (newValue > heap[index])
            throw new IllegalArgumentException("New value is greater than current value");

        heap[index] = newValue;
        tracker.incrementArrayAccesses();
        heapifyUp(index);
    }

    public static MinHeap merge(MinHeap h1, MinHeap h2) {
        if (h1 == null || h2 == null) {
            throw new IllegalArgumentException("Heaps to merge cannot be null");
        }

        PerformanceTracker tracker = h1.tracker;
        int newSize = h1.size + h2.size;

        if (newSize == 0) {
            return new MinHeap(1, tracker);
        }

        int[] mergedArray = new int[newSize];
        tracker.incrementMemoryAllocations();

        System.arraycopy(h1.heap, 0, mergedArray, 0, h1.size);
        System.arraycopy(h2.heap, 0, mergedArray, h1.size, h2.size);
        tracker.incrementArrayAccesses();

        MinHeap merged = new MinHeap(newSize, tracker);
        merged.heap = mergedArray;
        merged.size = newSize;
        merged.buildHeap();

        return merged;
    }

    private void buildHeap() {
        for (int i = size / 2 - 1; i >= 0; i--) {
            heapifyDown(i);
        }
    }

    private void heapifyUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            tracker.incrementComparisons();
            if (heap[index] < heap[parent]) {
                swap(index, parent);
                index = parent;
            } else {
                break;
            }
        }
    }

    private void heapifyDown(int index) {
        while (true) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int smallest = index;

            tracker.incrementComparisons();
            if (left < size && heap[left] < heap[smallest]) smallest = left;

            tracker.incrementComparisons();
            if (right < size && heap[right] < heap[smallest]) smallest = right;

            if (smallest != index) {
                swap(index, smallest);
                index = smallest;
            } else {
                break;
            }
        }
    }

    private void swap(int i, int j) {
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
        tracker.incrementSwaps();
        tracker.incrementArrayAccesses();
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int getSize() {
        return size;
    }
}
