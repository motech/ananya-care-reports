package org.motechproject.carereporting.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "cron_task")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "cron_task_id"))
})
public class CronTaskEntity extends AbstractEntity {

    @OneToOne
    @NotNull
    @JoinColumn(name = "frequency_id", nullable = false)
    private FrequencyEntity frequency;

    @NotNull
    @Column(name = "second", nullable = false)
    private String second;

    @NotNull
    @Column(name = "minute", nullable = false)
    private String minute;

    @NotNull
    @Column(name = "hour", nullable = false)
    private String hour;

    @NotNull
    @Column(name = "day", nullable = false)
    private String day;

    @NotNull
    @Column(name = "month", nullable = false)
    private String month;

    @NotNull
    @Column(name = "week_day", nullable = false)
    private String weekDay;

    @NotNull
    @Column(name = "year", nullable = false)
    private String year;

    public CronTaskEntity() {
        year = "";
    }

    public void setTime(String time) {
        String[] timeArray = time.split(":");

        hour = timeArray[0];
        minute = timeArray[1];
    }

    public String toString() {
        return (second + " " + minute + " " + hour + " " + day + " " + month + " " + weekDay + " " + year).trim();
    }

    public FrequencyEntity getFrequency() {
        return frequency;
    }

    public void setFrequency(FrequencyEntity frequency) {
        this.frequency = frequency;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTime() {
        return hour + ":" + minute;
    }
}
