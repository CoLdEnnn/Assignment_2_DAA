package algorithms;

import metrics.PerformanceTracker;
import java.util.Arrays;

public class MinHeap {
    private int[] heap;
    private int size;
    private final PerformanceTracker tracker;

    public MinHeap(int capacity, PerformanceTracker tracker) {
        if (capacity <= 0) throw new IllegalArgumentException();
        this.heap = new int[capacity];
        this.size = 0;
        this.tracker = tracker;
        tracker.incrementMemoryAllocations();
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void insert(int value) {
        if(isEmpty()) throw new IllegalArgumentException("Heap is empty");
        int min = heap[0];
        tracker.incrementArrayAccesses();
        size++;
        heapifyUp(size - 1);
    }

    private void heapifyUp(int i) {
        while (i > 0) {
            int parent = (i - 1) / 2;
            tracker.incrementComparisons();
            if (heap[i] < heap[parent]) {
                swap(i, parent);
                i = parent;
            } else break;
        }
    }

    private void swap(int i, int j) {
        tracker.incrementSwaps();
        tracker.incrementArrayAccesses();
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    public int extractMin() {
        if (isEmpty()) throw new IllegalStateException("Heap is empty");
        int min = heap[0];
        tracker.incrementArrayAccesses();
        heap[0] = heap[size - 1];
        tracker.incrementArrayAccesses();
        size--;
        heapifyDown(0);
        return min;
    }

    private void heapifyDown(int i) {
        int smallest = i;
        while (true) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;

            if (left < size) {
                tracker.incrementComparisons();
                if (heap[left] < heap[smallest]) smallest = left;
            }

            if (right < size) {
                tracker.incrementComparisons();
                if (heap[right] < heap[smallest]) smallest = right;
            }

            if (smallest != i) {
                swap(i, smallest);
                i = smallest;
            } else break;
        }
    }

    public void decreaseKey(int index, int newValue) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Invalid index");
        tracker.incrementComparisons();
        if (newValue > heap[index]) throw new IllegalArgumentException("New value is greater than current value");
        heap[index] = newValue;
        tracker.incrementArrayAccesses();
        heapifyUp(index);
    }

    public static MinHeap merge(MinHeap h1, MinHeap h2) {
        int newSize = h1.size + h2.size;
        PerformanceTracker tracker = h1.tracker;
        tracker.incrementMemoryAllocations();
        MinHeap merged = new MinHeap(newSize, tracker);
        merged.size = newSize;
        merged.heap = Arrays.copyOf(h1.heap, newSize);
        System.arraycopy(h2.heap, 0, merged.heap, h1.size, h2.size);
        merged.buildHeap();
        return merged;
    }

    private void buildHeap() {
        for (int i = size / 2 - 1; i >= 0; i--) {
            heapifyDown(i);
        }
    }

    private void grow() {
        heap = Arrays.copyOf(heap, heap.length * 2);
        tracker.incrementMemoryAllocations();
    }

    public void printHeap() {
        for (int i = 0; i < size; i++) {
            System.out.print(heap[i] + " ");
        }
        System.out.println();
    }
}
