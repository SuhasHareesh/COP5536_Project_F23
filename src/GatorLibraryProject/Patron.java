package src.GatorLibraryProject;

public class Patron {
    
    private int patron_id;
    private int patron_priority;

    public int GetPatronID (){
        return patron_id;
    }

    public int GetPatronPriority() {
        return patron_priority;
    }

    public boolean SetPatronDetails(int pPatronID, int pPatronPriority) {

        patron_id = pPatronID;
        patron_priority = pPatronPriority;

        return true;

        // TODO Setup Exception Handling

    }
}
