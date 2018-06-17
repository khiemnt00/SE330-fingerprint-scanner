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

public class GetCurrentRollData {

@SerializedName("rolls")
@Expose
private List<Roll> rolls = null;

public List<Roll> getRolls() {
return rolls;
}

public void setRolls(List<Roll> rolls) {
this.rolls = rolls;
}

}