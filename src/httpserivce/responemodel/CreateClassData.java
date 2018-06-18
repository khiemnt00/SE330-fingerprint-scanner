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

public class CreateClassData {
    @SerializedName("class")
@Expose
private AttendenceClass _class;

public AttendenceClass getClass_() {
return _class;
}

public void setClass_(AttendenceClass _class) {
this._class = _class;
}
}
