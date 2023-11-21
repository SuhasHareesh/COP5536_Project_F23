package src.GatorLibraryProject;


class Books {

    private int             book_id;
    private String          book_name;
    private String          author_name;
    private boolean         availability_status;
    private int             borrowed_id;
    private ReservationHeap reservation_heap;

    public int GetBookID() {
        return book_id;
    }

    public void AddNewBook(int pBookID, String pBookName, String pAuthorName, boolean pAvailStat) {
        book_id = pBookID;
        book_name = pBookName;
        author_name = pAuthorName;
        availability_status = pAvailStat;
        borrowed_id = 0;
    }

    public void DeleteBook(int pBookID) {
        if (book_id == pBookID) {
            String reservations = reservation_heap.GetCurrentReservations();

            System.out.print("Book " + book_id + " is no longer available.");

            switch (reservation_heap.GetReservationCount()) {
                case 0 -> {

                }
                case 1 -> {
                    System.out.print("Reservation made by Patron " + reservations + " has been cancelled!");
                }

                default -> {
                    System.out.print("Reservations made by Patrons " + reservations + " have been cancelled!");
                }
            }
        }
    }

    public boolean IsBookAvailable() {
        return availability_status;
    }

    public void CreateReservation(int pPatronID, int pPatronPriority) {
        Reservation res = new Reservation(pPatronID, pPatronPriority);
        reservation_heap.InsertReservation(res);
    }

    public void PrintBook () {

        System.out.println("Book ID = " + String.valueOf(book_id));
        System.out.println("Title = " + book_name);
        System.out.println("Author = " + author_name);
        System.out.println("Availability = " + String.valueOf(book_id)); // Change this to boolean eval
        System.out.println("Borrowed By = " + String.valueOf(book_id)); // PatronID/ None
        System.out.println("Reservations = " + reservation_heap.GetCurrentReservations());

    }


}

