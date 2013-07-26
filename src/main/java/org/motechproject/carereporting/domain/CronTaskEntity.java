package org.motechproject.carereporting.domain;

import org.quartz.CronExpression;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.text.ParseException;

@Entity
@Table(name = "cron_task")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "cron_task_id"))
})
public class CronTaskEntity extends AbstractEntity {

    private static final String PARSE_ERROR = "Invalid expression. See reference.";

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

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

    public void setExpression(String expression) throws ParseException {
        if (!CronExpression.isValidExpression(expression)) {
            throw new ParseException(PARSE_ERROR, 0);
        }

        String[] fields = expression.split(" ");
        second = fields[0];
        minute = fields[1];
        hour = fields[2];
        day = fields[3];
        month = fields[4];
        weekDay = fields[5];

        if (fields.length > 6) {
            year = fields[6];
        }
    }

    public String toCronExpression() {
        return (second + " " + minute + " " + hour + " " + day + " " + month + " " + weekDay + " " + year).trim();
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
