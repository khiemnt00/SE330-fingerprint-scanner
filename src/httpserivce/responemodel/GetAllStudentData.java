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

public class GetAllStudentData {
    @SerializedName("students")
    @Expose
    private List<Student> students = null;

    public List<Student> getStudents() {
    return students;
    }

    public void setStudents(List<Student> students) {
    this.students = students;
    }
}
