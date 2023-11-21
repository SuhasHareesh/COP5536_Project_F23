package src.GatorLibraryProject;


class Books {

    private int             book_id;
    private String          book_name;
    private String          author_name;
    private boolean         availability_status;
    private String          borrowed_id;
    private ReservationHeap reservation_heap;

    /**
    * @brief Returns the ID of the book
    * @return the ID of the book.
    */
    public int GetBookID() {
        return book_id;
    }

    /**
    * @brief Adds a new book to the book list. This is called when we have found a new book in the book list
    * @param pBookID The ID of the book.
    * @param pBookName The name of the book.
    * @param pAuthorName The author who made the book
    * @param pAvailStat The availability status of the book
    */

    public void AddNewBook(int pBookID, String pBookName, String pAuthorName, boolean pAvailStat) {
        book_id = pBookID;
        book_name = pBookName;
        author_name = pAuthorName;
        availability_status = pAvailStat;
        borrowed_id = "None";
        reservation_heap = new ReservationHeap();
    }

    /**
    * @brief Deletes the book and all of its reservations.
    * @param pBookID The ID of the book to delete.
    */

    public void DeleteBook(int pBookID) {

        if (book_id == pBookID) {
            String reservations = reservation_heap.GetCurrentReservations();

            System.out.println("Book " + book_id + " is no longer available.");
            System.out.println("");


            // Outputs based on the number of reservations.

            switch (reservation_heap.GetReservationCount()) {
                case 0 -> {

                }
                case 1 -> {
                    System.out.println(" Reservation made by Patron " + reservations + " has been cancelled!");
                    System.out.println("");
                }

                default -> {
                    System.out.println(" Reservations made by Patrons " + reservations + " have been cancelled!");
                    System.out.println("");
                }
            }
        }
    }

    /**
    * @brief Returns true if the book is available. This is a boolean that can be used to determine if the book is in the availability state or not.
    * @return whether the book is available or not ( true / false ) based on the availability status of the
    */

    public boolean IsBookAvailable() {
        return availability_status;
    }


    /**
    * @brief Return a patron to the pool. This is called when you want to return a book and don't want to keep track of it.
    * @param pPatronID The ID of the patron that is returned.
    * @return The borrowed ID of the book or " None " if there are no reservations left to return
    */

    public String ReturnBook (int pPatronID) {
        

        // if the patient ID is not equal to theorrowed_id

        if (pPatronID != Integer.parseInt(borrowed_id)) {
            throw new IllegalStateException("Patron ID mismatch while returning");
        }


        // This method is used to check if the current reservation is available.

        if (reservation_heap.GetCurrentReservations().equals("")) {
            borrowed_id = "None";
            availability_status = true;
        } else {
            borrowed_id = reservation_heap.RemoveFirstReservation().GetPatronID();
        }

        return borrowed_id;

    }

    /**
    * @brief Borrow a book from the patient. This is called when someone borrowes a patron from the book
    * @param pPatronID The patient ID of the borrow
    */

    public void BorrowBook(int pPatronID) {
        borrowed_id = String.valueOf(pPatronID);
        availability_status = false;
    }

    /**
    * @brief Creates a reservation for the patron. This reservation is used to determine if a patient is in the list of reservations that can be redeemed at a later time
    * @param pPatronID The ID of the patron that is being reservationd. This is a field in the Reservation object and must be greater than or equal to zero.
    * @param pPatronPriority The priority of the patron
    */

    public void CreateReservation(int pPatronID, int pPatronPriority) {
        Reservation res = new Reservation(pPatronID, pPatronPriority);
        reservation_heap.InsertReservation(res);
    }

    /**
    * @brief Prints book to console as ouput.
    */

    public void PrintBook () {

        System.out.println("Book ID = " + String.valueOf(book_id));
        System.out.println("Title = \"" + book_name + "\"");
        System.out.println("Author = \"" + author_name + "\"");
        String avail = availability_status ? "Yes" : "No";
        System.out.println("Availability = \"" + avail + "\""); 
        System.out.println("Borrowed By = " + String.valueOf(borrowed_id)); 
        System.out.println("Reservations = [" + reservation_heap.GetCurrentReservations() + "]");

    }

}

