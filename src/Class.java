
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tan
 */
public class Class {
    private String name;
    private String classid;
    private String hocky;
    private String namhoc;
    student student;
    private String giangvien;
    public Class(){super();}
    public Class(String name, String classid, String hocky, String namhoc, String giangvien){
        this.name = name;
        this.classid = classid;
        this.hocky = hocky;
        this.namhoc = namhoc;
        this.giangvien = giangvien;
    }
    public Class(String classid, student student){
        super();
        this.classid = classid;
        this.student = student;
    }
    
    public student getstudent(){
        return this.student;
    }
    public void setstudent(student student){
        this.student = student;
    }
    public String getname(){
        return this.name;
    }
    public void setname(String name){
        this.name = name;
    }
    public String getclassid(){
        return this.classid;
    }
    public void setclassid(String classid){
        this.classid = classid;
    }
    public String gethocky(){
        return this.hocky;
    }
    public void sethocky(String hocky){
        this.hocky = hocky;
    }
    public String getnamhoc(){
        return this.namhoc;
    }
    public void setnamhoc(String namhoc){
        this.namhoc = namhoc;
    }
    public String getgiangvien(){
        return this.giangvien;
    }
    public void setgiangvien(String giangvien){
        this.giangvien = giangvien;
    }
    ArrayList<student> sinhvien = new ArrayList<student>();
    public void addstudent(student student){
        this.sinhvien.add(student);
    }
    public Object[] toArray()
    {
        return new Object[]{this.name, this.classid, this.hocky, this.namhoc, this.giangvien};
    }
}
