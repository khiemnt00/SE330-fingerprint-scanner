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

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReportRoll {
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
@SerializedName("count")
@Expose
private Integer count;
@SerializedName("rolls")
@Expose
private List<RollChecked> rolls = null;

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

public Integer getCount() {
return count;
}

public void setCount(Integer count) {
this.count = count;
}

public List<RollChecked> getRolls() {
return rolls;
}

public void setRolls(List<RollChecked> rolls) {
this.rolls = rolls;
}
}
