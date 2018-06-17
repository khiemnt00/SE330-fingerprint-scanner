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

public class AttendenceClass {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("name")
@Expose
private String name;
@SerializedName("code")
@Expose
private String code;
@SerializedName("year")
@Expose
private String year;
@SerializedName("semester")
@Expose
private Integer semester;

    public AttendenceClass() {
    }

    public AttendenceClass(Integer id, String name, String code, String year, Integer semester) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.year = year;
        this.semester = semester;
    }

    public AttendenceClass(String name, String code, String year, Integer semester) {
        this.name = name;
        this.code = code;
        this.year = year;
        this.semester = semester;
    }


public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getCode() {
return code;
}

public void setCode(String code) {
this.code = code;
}

public String getYear() {
return year;
}

public void setYear(String year) {
this.year = year;
}

public Integer getSemester() {
return semester;
}

public void setSemester(Integer semester) {
this.semester = semester;
}

}