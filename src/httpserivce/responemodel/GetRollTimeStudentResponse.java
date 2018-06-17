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

public class GetRollTimeStudentResponse {

@SerializedName("rcode")
@Expose
private Integer rcode;
@SerializedName("message")
@Expose
private String message;
@SerializedName("data")
@Expose
private GetCurrentRollData data;
@SerializedName("status")
@Expose
private Boolean status;

public Integer getRcode() {
return rcode;
}

public void setRcode(Integer rcode) {
this.rcode = rcode;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

public GetCurrentRollData getData() {
return data;
}

public void setData(GetCurrentRollData data) {
this.data = data;
}

public Boolean getStatus() {
return status;
}

public void setStatus(Boolean status) {
this.status = status;
}

}