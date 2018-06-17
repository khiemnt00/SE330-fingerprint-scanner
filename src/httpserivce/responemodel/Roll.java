/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package httpserivce.responemodel;

/**
 *
 * @author tuyen
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Roll {

@SerializedName("name")
@Expose
private String name;
@SerializedName("student_id")
@Expose
private Integer studentId;
@SerializedName("mssv")
@Expose
private Integer mssv;
@SerializedName("class_id")
@Expose
private Integer classId;
@SerializedName("time")
@Expose
private String time;

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public Integer getStudentId() {
return studentId;
}

public void setStudentId(Integer studentId) {
this.studentId = studentId;
}

public Integer getMssv() {
return mssv;
}

public void setMssv(Integer mssv) {
this.mssv = mssv;
}

public Integer getClassId() {
return classId;
}

public void setClassId(Integer classId) {
this.classId = classId;
}

public String getTime() {
return time;
}

public void setTime(String time) {
this.time = time;
}

}