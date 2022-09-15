package com.example.petfeeder;
//TimerModal is the getter/setter class of all of the elements in the Arraylist that makes up the SQlite database
public class TimerModal {
    private String ID;
    private String timerName;
    private String date;
    private String time;
    private String amount;
    private String notes;

    public String getID() {
        return ID;
    }
    public void setID(String ID) { this.ID = ID; }

    public String getTimerName() {
        return timerName;
    }
    public void setTimerName(String timerName) {
        this.timerName = timerName;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

    public String getAmount() {
        return amount;
    }
    public void setAmount(String notes) {
        this.amount = amount;
    }

    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }

    public TimerModal(String ID, String timerName, String date, String time, String amount, String notes) {
        this.ID = ID;
        this.timerName = timerName;
        this.date = date;
        this.time = time;
        this.amount = amount;
        this.notes = notes;
    }
}

