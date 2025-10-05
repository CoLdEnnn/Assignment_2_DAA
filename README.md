# Assignment_2_DAA
## Overview
This repository implements a **Min-Heap** data structure in Java with full **performance tracking** and **benchmarking** support.  

## Features
**Core Operations**
- `insert(value)` – Inserts a new element into the heap.
- `extractMin()` – Removes and returns the smallest element.
- `decreaseKey(index, newValue)` – Decreases value at given index.
- `merge(otherHeap)` – Efficiently merges two heaps in **O(n)** using `buildHeap`.

**Performance Metrics**
- Tracks **comparisons**, **swaps**, **array accesses**, and **memory allocations** via `PerformanceTracker`.

**Benchmarking**
- Includes a CLI benchmark runner (`BenchmarkRunner`) to test heap performance on large datasets.
- Results are exported as CSV for analysis.
