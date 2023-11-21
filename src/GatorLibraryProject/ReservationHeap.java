package src.GatorLibraryProject;


public class ReservationHeap {

    private int SIZE = 20;

    private Reservation[]   res_heap;
    private int             size;

    ReservationHeap() {
        res_heap = new Reservation[SIZE];
    }

    ReservationHeap (int pCapacity) {
        res_heap = new Reservation[pCapacity];
    }

    private boolean IsHeapFull() {
        return size == res_heap.length;
    }

    private boolean IsHeapEmpty() {
        return size == 0;
    }

    private void Swap(int i, int j) {
        Reservation temp = res_heap[i];
        res_heap[i] = res_heap[j];
        res_heap[j] = temp;
    }

    private void BubbleUp(int idx) {
        int parent = (idx - 1) / 2;
        
        while (idx > 0 && (res_heap[idx].CompareReservations(res_heap[parent]) < 0)) {
            Swap(parent, idx);
            idx = parent;
            parent = (idx - 1) / 2;
        }
    }

    private void BubbleDown(int idx) {
        int left = 2 * idx + 1;
        int right = 2 * idx + 2;
        int smallest = idx;

        if (left < size && res_heap[left].CompareReservations(res_heap[smallest]) < 0) {
            smallest = left;
        }

        if (right < size && res_heap[right].CompareReservations(res_heap[smallest]) < 0) {
            smallest = right;
        }

        if (smallest != idx) {
            Swap(idx, smallest);
            BubbleDown(smallest);
        }
    }


    public boolean InsertReservation(Reservation pRes) {

        if (IsHeapFull()) {
            System.out.println("The waitlist is full.");
            return false;
        }

        res_heap[size++] = pRes;
        BubbleUp(size - 1);


        return true;
    }

    public void RemoveFirstReservation() {

        if (IsHeapEmpty()) {
            System.out.println("There are no more reservations for this book.");  
        }

        Reservation min = res_heap[0];
        res_heap[0] = res_heap[--size];
        BubbleDown(0);
    }

    public String GetCurrentReservations() {
        if (!IsHeapEmpty()) {
            StringBuilder reservations = new StringBuilder();

            for (int i = 0; i < size; i++){
                if (i != 0) {
                    reservations.append(", ");
                }
                reservations.append(res_heap[i].GetPatronID());
            }
            return reservations.toString();
        }
        return "";
    }

    public int GetReservationCount() {
        return size;
    }
    
}
