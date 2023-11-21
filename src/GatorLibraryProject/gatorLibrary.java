package src.GatorLibraryProject;

import java.io.File;
import java.util.Scanner;


public class gatorLibrary {

    private static BooksRBTree library_tree;

    private static final String INS_BOOK       = "InsertBook";
    private static final String PRNT_BOOK      = "PrintBook";
    private static final String PRNT_BOOKS     = "PrintBooks";
    private static final String BRW_BOOK       = "BorrowBook";
    private static final String RET_BOOK       = "ReturnBook";
    private static final String DEL_BOOK       = "DeleteBook";
    private static final String FND_CL_BOOK    = "FindClosestBook";
    private static final String COL_FLIP       = "ColorFlipCount";
    private static final String QUIT           = "Quit";



    /**
    * @brief Prints the contents of a book
    * @param pBookID The ID of the
    */

    private static void PrintBook(int pBookID) {
        Books book = library_tree.GetBook(pBookID);

        // Prints the book in the library.

        if (book == null) {
            System.out.println("Book " + String.valueOf(pBookID) + " not found in the Library.");
        } else {
            book.PrintBook();
        }
    }


    /**
    * @brief Prints all books in the range of book IDs
    * @param pBookIDStart Start book ID to print
    * @param pBookIDEnd End book ID to print
    */

    private static void PrintBooks(int pBookIDStart, int pBookIDEnd) {
        Books[] books = library_tree.GetBooksInRange(pBookIDStart, pBookIDEnd);
        for (Books singlebooks : books) {
            singlebooks.PrintBook();
            System.out.println("");
        }
    }


    /**
    * @brief Adds a new book to the library
    * @param pBookID The ID of the book to be added. Must be > 0 and < MAX_BLOGS. If this is less than 0 it is assumed that the book is already in the library
    * @param pBookName The name of the book to be added. Must be <0 and < MAX_BLOGS. If this is less than 0 it
    * @param pAuthName
    * @param pAvailStat
    */

    private static void InsertBook(int pBookID, String pBookName, String pAuthName, String pAvailStat) {

        boolean avail = false;
        Books new_book =  new Books();

        // If the availStat is Yes then set the avail flag to true.

        if (pAvailStat.equals("Yes")) {
            avail = true;
        }
        new_book.AddNewBook(pBookID, pBookName, pAuthName, avail);
        library_tree.InsertBook(new_book);

    }


    /**
    * @brief Borrow a book from the library
    * @param pPatronID The ID of the patron borrowing the book
    * @param pBookID The book to borrow.
    * @param pPatronPriority The priority of the borrow
    */

    private static void BorrowBook(int pPatronID, int pBookID, int pPatronPriority) {

        Books book = library_tree.GetBook(pBookID);

        // Create a new reservation for the given patron

        if (!book.IsBookAvailable()) {
            book.CreateReservation(pPatronID, pPatronPriority);
            System.out.println("Book " + String.valueOf(pBookID) + " Reserved by Patron " + String.valueOf(pPatronID));
            System.out.println("");

        } else {
            book.BorrowBook(pPatronID);
            System.out.println("Book " + String.valueOf(pBookID) + " Borrowed by Patron " + String.valueOf(pPatronID));
            System.out.println("");
        }
    }


    /**
    * @brief This method returns a book to the patron
    * @param pPatronID The ID of the patient to retire from. Not used in this method but used in the code below to print what is returned.
    * @param pBookID The ID of the book
    */

    private static void ReturnBook(int pPatronID, int pBookID) {
        Books book = library_tree.GetBook(pBookID);
        String next_patron = book.ReturnBook(pPatronID);
    
        System.out.println("Book " + String.valueOf(pBookID) + " Returned by Patron " + String.valueOf(pPatronID));
        System.out.println("");


        // This prints the patron who was in the waitlist and got the book next.

        if (!next_patron.equals("None")) {
            System.out.println("Book " + String.valueOf(pBookID) + " Allotted to Patron " + String.valueOf(next_patron));
            System.out.println("");
        }
    }


    /**
    * @brief Deletes a book from the library
    * @param pBookID The ID of the book to
    */

    private static void DeleteBook(int pBookID) {

        Books book = library_tree.GetBook(pBookID);
        book.DeleteBook(pBookID);
        library_tree.DeleteBook(book);

    }


    /**
    * @brief FindClosestBook searches the library tree for the closest book
    * @param pBookID The ID of the
    */

    private static void FindClosestBook (int pBookID) {
        Books is_this_book = library_tree.GetBook(pBookID);


        // Print the books that are closest to the book.

        if (is_this_book != null) {
            PrintBook(pBookID);
            System.out.println("");

        } else {

            Books[] books = library_tree.FindClosestBooks(pBookID);

            for (Books singlebooks : books) {
                singlebooks.PrintBook();
                System.out.println("");
            }
        }
    }


    /**
    * @brief Prints the number of color flip
    */

    private static void PrintColorFlipCount () {
        System.out.println("Color Flip Count: " + library_tree.GetColorFlipCount());
        System.out.println("");
    }



    /**
    * @brief Main method for the library
    * @param args Command line arguments
    */

    public static void main(String[] args) throws Exception {

        library_tree = new BooksRBTree();
        
        boolean terminate = false;

        File file = new File(args[0]);
        Scanner sc = new Scanner(file);


        // Returns the next line of the current line.

        while (sc.hasNextLine() && !terminate) {
            String line = sc.nextLine();

            // Extract function name and arguments
            String[] parts = line.split("\\(");
            String function = parts[0];
            String params = parts[1].replace(")", "");

            parts = params.split(",\\s*");


            // Removes all leading and trailing whitespace from parts.

            for (int i = 0; i < parts.length; i++) {
                parts[i] = parts[i].trim().replaceAll("^\"|\"$", "");
            }


            // The function to be called based on the input.

            switch(function) {
                case INS_BOOK -> {
                    InsertBook(Integer.parseInt(parts[0]), parts[1], parts[2], parts[3]);
                }

                case PRNT_BOOK -> {
                    PrintBook(Integer.parseInt(parts[0]));
                }

                case PRNT_BOOKS -> {
                    PrintBooks(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
                }

                case BRW_BOOK -> {
                    BorrowBook(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
                }

                case RET_BOOK -> {
                    ReturnBook(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
                }

                case DEL_BOOK -> {
                    DeleteBook(Integer.parseInt(parts[0]));
                }

                case FND_CL_BOOK -> {
                    FindClosestBook(Integer.parseInt(parts[0]));
                }

                case COL_FLIP -> {
                    PrintColorFlipCount();
                }

                case QUIT -> {
                    terminate = true;
                }

            }

        }

        sc.close();

        System.out.println("Program Terminated!!");

    }
}



