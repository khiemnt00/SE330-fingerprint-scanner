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

public class RollChecked {
    @SerializedName("checked")
@Expose
private Boolean checked;
@SerializedName("time")
@Expose
private String time;

public Boolean getChecked() {
return checked;
}

public void setChecked(Boolean checked) {
this.checked = checked;
}

public String getTime() {
return time;
}

public void setTime(String time) {
this.time = time;
}
}
