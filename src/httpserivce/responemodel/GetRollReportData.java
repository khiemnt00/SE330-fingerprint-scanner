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

public class GetRollReportData {
 @SerializedName("dates")
@Expose
private List<ReportDate> dates = null;
@SerializedName("rolls")
@Expose
private List<ReportRoll> rolls = null;

public List<ReportDate> getDates() {
return dates;
}

public void setDates(List<ReportDate> dates) {
this.dates = dates;
}

public List<ReportRoll> getRolls() {
return rolls;
}

public void setRolls(List<ReportRoll> rolls) {
this.rolls = rolls;
}   

    @Override
    public String toString() {
        return "GetRollReportData{" + "dates=" + dates + ", rolls=" + rolls + '}';
    }


}
