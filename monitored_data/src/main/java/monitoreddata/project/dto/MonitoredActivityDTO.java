package monitoreddata.project.dto;

import java.sql.Date;
import java.sql.Time;

public class MonitoredActivityDTO {
    private Date day;
    private Time startTime;
    private Time endTime;
    private String Activity;

    public MonitoredActivityDTO() {
    }

    public MonitoredActivityDTO(Date day, Time startTime, Time endTime, String activity) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        Activity = activity;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public String getActivity() {
        return Activity;
    }

    public void setActivity(String activity) {
        Activity = activity;
    }

    @Override
    public String toString() {
        return "MonitoredActivityDTO{" +
                "day=" + day +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", Activity='" + Activity + '\'' +
                '}';
    }
}
