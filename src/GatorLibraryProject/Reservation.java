package src.GatorLibraryProject;

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


    /**
    * @brief Returns the patron priority.
    * @return a value of patron priority.
    */

    public int GetPatronPriority () {
        return patron_priority;
    }


    /**
    * @brief Returns the patron ID associated with this patron.
    * @return the patron ID associated with this
    */

    public String GetPatronID() {
        return String.valueOf(patron_id);
    }


    /**
    * @brief Returns the reservation time.
    * @return the reservation time or null if not
    */

    public LocalTime GetReservationTime() {
        return reservation_time;
    }


    /**
    * @brief Compares this reservation with another to determine which is earlier.
    * @param pCompRes The reservation to compare with.
    * @return A negative integer zero or a positive integer as this reservation is earlier than equal to or later than the specified reservation
    */

    public int CompareReservations(Reservation pCompRes) {

        // the patron priority of the patron

        if (this.patron_priority != pCompRes.patron_priority) {
            return this.patron_priority - pCompRes.patron_priority;
          } else {
            // Compares time if the patron priority is the same
            
            Duration duration = Duration.between(reservation_time, pCompRes.reservation_time);
            long diff_in_milsecs = duration.toMillis();

            return (int) diff_in_milsecs;
        }
    }
}
