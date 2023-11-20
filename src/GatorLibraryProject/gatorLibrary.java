package src.GatorLibraryProject;

import java.io.File;
import java.util.Scanner;


public class gatorLibrary {

    private static final String INS_BOOK       = "InsertBook";
    private static final String PRNT_BOOK      = "PrintBook";
    private static final String PRNT_BOOKS     = "PrintBooks";
    private static final String BRW_BOOK       = "BorrowBook";
    private static final String DEL_BOOK       = "DeleteBook";
    private static final String FND_CL_BOOK    = "FindClosestBook";
    private static final String COL_FLIP       = "ColorFlipCount";


    private static void PrintBook(int pBookID) {

    }

    private static void PrintBooks(int pBookIDStart, int pBookIDEnd) {

    }

    private static void InsertBook(int pBookID, String pBookName, String pAuthName, boolean pAvailStat) {

    }

    private static void BorrowBook(int pPatronID, int pBookID, int pPatronPriority) {

    }

    private static void DeleteBook(int pBookID) {

    }

    private static void FindClosestBook (int pBookID) {

    }

    private static void PrintColorFlipCount () {
        System.out.println("Color Flip Count: ");
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

            switch(function) {
                case INS_BOOK -> {

                }

                case PRNT_BOOK -> {

                }

                case PRNT_BOOKS -> {

                }

                case BRW_BOOK -> {

                }

                case DEL_BOOK -> {

                }

                case FND_CL_BOOK -> {

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

    }
}



