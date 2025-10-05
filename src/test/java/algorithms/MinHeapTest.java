package algorithms;

import metrics.PerformanceTracker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MinHeapTest {

    private PerformanceTracker tracker;

    @BeforeEach
    void setup() {
        tracker = new PerformanceTracker();
    }

    @Test
    void testInsertAndExtractMin() {
        MinHeap heap = new MinHeap(5, tracker);
        heap.insert(5);
        heap.insert(2);
        heap.insert(9);
        heap.insert(1);

        assertEquals(4, heap.getSize());
        assertEquals(1, heap.extractMin());
        assertEquals(2, heap.extractMin());
        assertEquals(5, heap.extractMin());
        assertEquals(9, heap.extractMin());
        assertTrue(heap.isEmpty());
    }

    @Test
    void testDecreaseKey() {
        MinHeap heap = new MinHeap(5, tracker);
        heap.insert(10);
        heap.insert(20);
        heap.insert(30);
        heap.decreaseKey(2, 5);
        assertEquals(5, heap.extractMin());
        assertEquals(10, heap.extractMin());
        assertEquals(20, heap.extractMin());
    }

    @Test
    void testMerge() {
        MinHeap h1 = new MinHeap(5, tracker);
        h1.insert(3);
        h1.insert(8);
        h1.insert(1);

        MinHeap h2 = new MinHeap(5, tracker);
        h2.insert(7);
        h2.insert(2);
        h2.insert(9);

        MinHeap merged = MinHeap.merge(h1, h2);
        assertEquals(6, merged.getSize());
        assertEquals(1, merged.extractMin());
        assertEquals(2, merged.extractMin());
        assertEquals(3, merged.extractMin());
    }

    @Test
    void testEmptyExtractThrows() {
        MinHeap heap = new MinHeap(3, tracker);
        assertThrows(IllegalStateException.class, heap::extractMin);
    }

    @Test
    void testInvalidDecreaseKeyThrows() {
        MinHeap heap = new MinHeap(3, tracker);
        heap.insert(10);
        assertThrows(IllegalArgumentException.class, () -> heap.decreaseKey(0, 20));
    }

    @Test
    void testGrowFunctionality() {
        MinHeap heap = new MinHeap(2, tracker);
        heap.insert(5);
        heap.insert(3);
        heap.insert(1);
        assertEquals(3, heap.getSize());
        assertEquals(1, heap.extractMin());
    }

    @Test
    void testExtractFromEmptyHeap() {
        MinHeap heap = new MinHeap(5, tracker);
        assertThrows(IllegalStateException.class, heap::extractMin);
    }

    @Test
    void testExtractFromSingleElementHeap() {
        MinHeap heap = new MinHeap(5, tracker);
        heap.insert(10);
        assertEquals(10, heap.extractMin());
        assertTrue(heap.isEmpty());
    }

    @Test
    void testMergeWithEmptyHeaps() {
        MinHeap h1 = new MinHeap(5, tracker);
        MinHeap h2 = new MinHeap(5, tracker);
        MinHeap merged = MinHeap.merge(h1, h2);
        assertEquals(0, merged.getSize());
    }
}
