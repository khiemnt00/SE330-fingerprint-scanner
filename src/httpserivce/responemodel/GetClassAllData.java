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

public class GetClassAllData {

@SerializedName("classes")
@Expose
private List<AttendenceClass> classes = null;

public List<AttendenceClass> getClasses() {
return classes;
}

public void setClasses(List<AttendenceClass> classes) {
this.classes = classes;
}

}