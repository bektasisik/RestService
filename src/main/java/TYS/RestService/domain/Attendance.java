package TYS.RestService.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Attendance {
    private final int id;
    private final String prayerTime;
    private final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    private final String date = formatter.format(new Date());

    public int getId() {
        return id;
    }

    public String getPrayerTime() {
        return prayerTime;
    }

    public String getDate() {
        return date;
    }

    public Attendance(int id, String prayerTime) {
        this.id = id;
        this.prayerTime = prayerTime;
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "id=" + id +
                ", prayerTime='" + prayerTime + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
