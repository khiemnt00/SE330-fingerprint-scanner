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

public class Student {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("name")
@Expose
private String name;
@SerializedName("mssv")
@Expose
private Integer mssv;
@SerializedName("class_id")
@Expose
private Integer classId;
@SerializedName("finger_id")
@Expose
private Integer fingerId;
@SerializedName("autheticated")
@Expose
private Boolean autheticated;

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

public Integer getFingerId() {
return fingerId;
}

public void setFingerId(Integer fingerId) {
this.fingerId = fingerId;
}

public Boolean getAutheticated() {
return autheticated;
}

public void setAutheticated(Boolean autheticated) {
this.autheticated = autheticated;
}

    
}
