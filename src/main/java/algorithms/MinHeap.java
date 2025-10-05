package algorithms;

import metrics.PerformanceTracker;
import java.util.Arrays;

public class MinHeap {
    private int[] heap;
    private int size;
    private final PerformanceTracker tracker;

    public MinHeap(int capacity, PerformanceTracker tracker) {
        this.heap = new int[capacity];
        this.size = 0;
        this.tracker = tracker;
        tracker.incrementMemoryAllocations();
    }

    public void insert(int value) {
        tracker.incrementMemoryAllocations();
        if (size == heap.length) {
            heap = Arrays.copyOf(heap, size * 2);
            tracker.incrementMemoryAllocations();
        }
        heap[size] = value;
        tracker.incrementArrayAccesses();
        size++;
        heapifyUp(size - 1);
    }

    public int extractMin() {
        if (size == 0) throw new IllegalStateException("Heap is empty");
        int min = heap[0];
        tracker.incrementArrayAccesses();
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

    public void merge(MinHeap other) {
        int newSize = this.size + other.size;
        int[] merged = new int[newSize];
        tracker.incrementMemoryAllocations();

        System.arraycopy(this.heap, 0, merged, 0, this.size);
        System.arraycopy(other.heap, 0, merged, this.size, other.size);
        tracker.incrementArrayAccesses();

        this.heap = merged;
        this.size = newSize;

        buildHeap();
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
