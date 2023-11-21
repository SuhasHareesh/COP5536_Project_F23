package src.GatorLibraryProject;

import java.io.File;
import java.util.Scanner;


public class gatorLibrary {

    private static BooksRBTree library_tree;

    private static final String INS_BOOK       = "InsertBook";
    private static final String PRNT_BOOK      = "PrintBook";
    private static final String PRNT_BOOKS     = "PrintBooks";
    private static final String BRW_BOOK       = "BorrowBook";
    private static final String DEL_BOOK       = "DeleteBook";
    private static final String FND_CL_BOOK    = "FindClosestBook";
    private static final String COL_FLIP       = "ColorFlipCount";


    private static void PrintBook(int pBookID) {
        Books book = library_tree.GetBook(pBookID);
        book.PrintBook();
    }

    private static void PrintBooks(int pBookIDStart, int pBookIDEnd) {
        Books[] books = library_tree.GetBooksInRange(pBookIDStart, pBookIDEnd);
        for (Books singlebooks : books) {
            singlebooks.PrintBook();
            System.out.println("");
        }
    }

    private static void InsertBook(int pBookID, String pBookName, String pAuthName, boolean pAvailStat) {

        Books new_book =  new Books();
        new_book.AddNewBook(pBookID, pBookName, pAuthName, pAvailStat);
        library_tree.InsertBook(new_book);

    }

    private static void BorrowBook(int pPatronID, int pBookID, int pPatronPriority) {

        Books book = library_tree.GetBook(pBookID);
        if (!book.IsBookAvailable()) {
            book.CreateReservation(pPatronID, pPatronPriority);
        }
    }

    private static void DeleteBook(int pBookID) {

        Books book = library_tree.GetBook(pBookID);
        library_tree.DeleteBook(book);

    }

    private static void FindClosestBook (int pBookID) {
        Books[] books = library_tree.FindClosestBooks(pBookID);

        for (Books singlebooks : books) {
            singlebooks.PrintBook();
            System.out.println("");
        }
    }

    private static void PrintColorFlipCount () {
        System.out.println("Color Flip Count: " + library_tree.GetColorFlipCount());
    }


    public static void main(String[] args) throws Exception {

        File file = new File(args[0]);
        Scanner sc = new Scanner(file);

        while (sc.hasNextLine()) {
            String line = sc.nextLine();

            // Extract function name and arguments
            String[] parts = line.split("\\(");
            String function = parts[0];
            String inp_args = parts[1].replace(")", "");
            String[] inp_arg_arr = inp_args.split(",");

            switch(function) {
                case INS_BOOK -> {
                    InsertBook(Integer.parseInt(inp_arg_arr[0]), inp_arg_arr[1], inp_arg_arr[2], inp_arg_arr[3] == "Yes" ? true : false);
                }

                case PRNT_BOOK -> {
                    PrintBook(Integer.parseInt(inp_arg_arr[0]));
                }

                case PRNT_BOOKS -> {
                    PrintBooks(Integer.parseInt(inp_arg_arr[0]), Integer.parseInt(inp_arg_arr[1]));
                }

                case BRW_BOOK -> {
                    BorrowBook(Integer.parseInt(inp_arg_arr[0]), Integer.parseInt(inp_arg_arr[1]), Integer.parseInt(inp_arg_arr[2]));
                }

                case DEL_BOOK -> {
                    DeleteBook(Integer.parseInt(inp_arg_arr[0]));
                }

                case FND_CL_BOOK -> {
                    FindClosestBook(Integer.parseInt(inp_arg_arr[0]));
                }

                case COL_FLIP -> {
                    PrintColorFlipCount();
                }

                default -> {
                    System.out.println("There seems to be a problem with the input. Please check it again");
                }

            }

        }

        sc.close();

        System.out.println("Program Terminated");

    }
}



