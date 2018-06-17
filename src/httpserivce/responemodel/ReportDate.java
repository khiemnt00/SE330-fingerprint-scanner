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

public class ReportDate {
    
@SerializedName("_id")
@Expose
private Integer id;
@SerializedName("time")
@Expose
private String time;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public String getTime() {
return time;
}

public void setTime(String time) {
this.time = time;
}
}
