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


    /**
    * @brief Checks if the heap is full.
    * @return true if the heap is
    */

    private boolean IsHeapFull() {
        return size == res_heap.length;
    }


    /**
    * @brief Returns true if the heap is empty.
    * @return whether the heap is
    */

    private boolean IsHeapEmpty() {
        return size == 0;
    }


    /**
    * @brief Swap two reservations in the heap
    * @param i index of the first reservation
    * @param j index of the second
    */

    private void Swap(int i, int j) {
        Reservation temp = res_heap[i];
        res_heap[i] = res_heap[j];
        res_heap[j] = temp;
    }


    /**
    * @brief Bubble up the element at the given index.
    * @param idx Index of element to bubble up
    */

    private void BubbleUp(int idx) {
        int parent = (idx - 1) / 2;
        

        // Swap idx to the parent of the res_heap

        while (idx > 0 && (res_heap[idx].CompareReservations(res_heap[parent]) < 0)) {
            Swap(parent, idx);
            idx = parent;
            parent = (idx - 1) / 2;
        }
    }


    /**
    * @brief Bubble down the element at the given index.
    * @param idx Index of element to bubble down
    */

    private void BubbleDown(int idx) {
        int left = 2 * idx + 1;
        int right = 2 * idx + 2;
        int smallest = idx;


        // Set smallest element to left based on lower the priority number higher the priority.

        if (left < size && res_heap[left].CompareReservations(res_heap[smallest]) < 0) {
            smallest = left;
        }


        // Set the smallest reservations to the right of the reservations array.

        if (right < size && res_heap[right].CompareReservations(res_heap[smallest]) < 0) {
            smallest = right;
        }

        // Swap the smallest element and bubble down.

        if (smallest != idx) {
            Swap(idx, smallest);
            BubbleDown(smallest);
        }
    }



    /**
    * @brief Inserts a reservation into the waitlist
    * @param pRes Reservation to be inserted into the waitlist
    * @return true if the operation succeeded false
    */

    public boolean InsertReservation(Reservation pRes) {

        // Returns false if the waitlist is full.
        if (IsHeapFull()) {
            System.out.println("The waitlist is full.");
            return false;
        }

        res_heap[size++] = pRes;
        BubbleUp(size - 1);


        return true;
    }


    /**
    * @brief Removes and retreives the first reservation from the BinaryMinHeap.
    * @return the Reservation that was removed
    */

    public Reservation RemoveFirstReservation() {

        Reservation min = res_heap[0];

        // Set the first element to the next element in the heap

        if (size > 1) {
            res_heap[0] = res_heap[--size];
            BubbleDown(0);
        }
        else
            res_heap[0] = null;
        return min;
    }


    /**
    * @brief Returns a comma separated list of patron IDs that are currently in the reservations.
    * @return a comma separated list of patron
    */

    public String GetCurrentReservations() {

        // Returns a string containing all patron IDs of all patrons in the heap.

        if (!IsHeapEmpty()) {
            StringBuilder reservations = new StringBuilder();


            // Append the patron ID to the reservations list

            for (int i = 0; i < size; i++){

                // Append to reservations list.

                if (i != 0) {
                    reservations.append(", ");
                }
                reservations.append(res_heap[i].GetPatronID());
            }

            return reservations.toString();
        }
        return "";
    }

    /**
    * @brief Returns the number of reservations.
    * @return the number of reservations
    */

    public int GetReservationCount() {
        return size;
    }
    
}
