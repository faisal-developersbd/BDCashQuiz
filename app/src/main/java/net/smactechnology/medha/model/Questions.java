package net.smactechnology.medha.model;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Sk Faisal
 */
public class Questions {
    private String id;
    private String q;
    private String o1;
    private String o2;
    private String o3;
    private String o4;
    private String title;
    private String ans;
    private String no;


    public Questions() {
    }

//    public Questions(String id, String q, String o1, String o2, String o3, String o4, String title, String ans) {
//        this.id = id;
//        this.q = q;
//        this.o1 = o1;
//        this.o2 = o2;
//        this.o3 = o3;
//        this.o4 = o4;
//        this.title = title;
//        this.ans = ans;
//    }


    public Questions(String id, String q, String o1, String o2, String o3, String o4, String title, String ans, String no) {
        this.id = id;
        this.q = q;
        this.o1 = o1;
        this.o2 = o2;
        this.o3 = o3;
        this.o4 = o4;
        this.title = title;
        this.ans = ans;
        this.no = no;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public String getO1() {
        return o1;
    }

    public void setO1(String o1) {
        this.o1 = o1;
    }

    public String getO2() {
        return o2;
    }

    public void setO2(String o2) {
        this.o2 = o2;
    }

    public String getO3() {
        return o3;
    }

    public void setO3(String o3) {
        this.o3 = o3;
    }

    public String getO4() {
        return o4;
    }

    public void setO4(String o4) {
        this.o4 = o4;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    @Override
    public String toString() {
        return "Questions{" +
                "id='" + id + '\'' +
                ", q='" + q + '\'' +
                ", o1='" + o1 + '\'' +
                ", o2='" + o2 + '\'' +
                ", o3='" + o3 + '\'' +
                ", o4='" + o4 + '\'' +
                ", title='" + title + '\'' +
                ", ans='" + ans + '\'' +
                ", no='" + no + '\'' +
                '}';
    }
}

