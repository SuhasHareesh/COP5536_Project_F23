package src.GatorLibraryProject;

import src.GatorLibraryProject.Patron;

import java.time.Duration;
import java.time.LocalTime;

public class Reservation {
    
    private int         patron_id;
    private int         patron_priority;
    private LocalTime   reservation_time;


    Reservation(int pPatronId, int pPatronPrior) {
        
        patron_id = pPatronId;
        patron_priority = pPatronPrior;
        reservation_time = LocalTime.now();
    }

    public int GetPatronPriority () {
        return patron_priority;
    }

    public String GetPatronID() {
        return String.valueOf(patron_id);
    }

    public LocalTime GetReservationTime() {
        return reservation_time;
    }

    public int CompareReservations(Reservation pCompRes) {
        if (this.patron_priority != pCompRes.patron_priority) {
            return this.patron_priority - pCompRes.patron_priority;
          } else {
            Duration duration = Duration.between(reservation_time, pCompRes.reservation_time);
            long diff_in_milsecs = duration.toMillis();

            return (int) diff_in_milsecs;
        }
    }
}
