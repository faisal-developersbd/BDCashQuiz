package faisal.com.bdcashquiz.model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Sk Faisal
 */
public class Schedule {
    private String id;
    private String title;
    private String start_time;
    private String end_time;
    private String date;
    private String max_life;
    private String amount;
    public Schedule() {
    }

    public Schedule(String id, String title, String start_time, String end_time, String date, String max_life, String amount) {
        this.id = id;
        this.title = title;
        this.start_time = start_time;
        this.end_time = end_time;
        this.date = date;
        this.max_life = max_life;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMax_life() {
        return max_life;
    }

    public void setMax_life(String max_life) {
        this.max_life = max_life;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Schedule{" + "id=" + id + ", title=" + title + ", start_time=" + start_time + ", end_time=" + end_time + ", date=" + date + ", max_life=" + max_life + ", amount=" + amount + '}';
    }



}

