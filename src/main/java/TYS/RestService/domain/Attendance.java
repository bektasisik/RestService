package TYS.RestService.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Attendance {
    private final int id;
    private final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
    private final String date = formatter.format(new Date());
    private String prayerTime;

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getPrayerTime() {
        return prayerTime;
    }

    public void setPrayerTime(String prayerTime) {
        this.prayerTime = prayerTime;
    }

    public Attendance(int id, String prayerTime) {
        this.id = id;
        this.prayerTime = prayerTime;
    }
}
