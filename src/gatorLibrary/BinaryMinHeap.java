package src.GatorLibrary;

// public class BinaryMinHeap {
    
// }


// import java.util.Arrays;

public class BinaryMinHeap {

  private int[] heap;
  private int size;

  public BinaryMinHeap(int capacity) {
    heap = new int[capacity];
  }

  public void insert(int value) {
    if (isFull()) {
      throw new IllegalStateException("Heap is full");
    }

    heap[size] = value;
    size++;
    bubbleUp(size - 1);
  }

  public int removeMin() {
    if (isEmpty()) {
      throw new IllegalStateException("Heap is empty");
    }

    int min = heap[0];
    heap[0] = heap[--size];
    bubbleDown(0);
    return min; 
  }

  private void bubbleUp(int index) {
    int parent = (index - 1) / 2;
    while (index > 0 && heap[index] < heap[parent]) {
      swap(parent, index);
      index = parent;
      parent = (index - 1) / 2;
    }
  }

  private void bubbleDown(int index) {
    int left = 2 * index + 1;
    int right = 2 * index + 2;
    int smallest = index;
    if (left < size && heap[left] < heap[smallest]) {
      smallest = left;
    }
    if (right < size && heap[right] < heap[smallest]) {
      smallest = right;
    }
    if (smallest != index) {
      swap(index, smallest);
      bubbleDown(smallest);
    }
  }

  private void swap(int i, int j) {
    int temp = heap[i];
    heap[i] = heap[j];
    heap[j] = temp;
  }

  private boolean isFull() {
    return size == heap.length;
  }

  private boolean isEmpty() {
    return size == 0;
  }
}