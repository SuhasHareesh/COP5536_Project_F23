package src.GatorLibrary;

public class Patron {
    
    private int patron_id;
    private int patron_priority;

    public  int GetPatronID (){
        return 0;
    }

    public  int GetPatronPriority() {
        return 0;
    }

    public  boolean SetPatronDetails(int pPatronID, int pPatronPriority) {

        patron_id = pPatronID;
        patron_priority = pPatronPriority;

        return true;

        // TODO Setup Exception Handling

    }
}
