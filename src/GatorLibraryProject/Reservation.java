package src.GatorLibraryProject;

import src.GatorLibraryProject.Patron;

import java.time.LocalTime;

public class Reservation {
    
    private Patron      patron;
    private LocalTime   reservation_time;


    Reservation(Patron pPatron) {
        
        patron = pPatron;
        reservation_time = LocalTime.now();
    }

    public int GetPatronPriority () {
        return patron.GetPatronPriority();
    }

    public String GetPatronID() {
        return String.valueOf(patron.GetPatronID());
    }

    public LocalTime GetReservationTime() {
        return reservation_time;
    }


}
