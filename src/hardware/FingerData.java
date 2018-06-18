package hardware;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tuyen
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FingerData {

    @SerializedName("msg_id")
    @Expose
    private String msgId;
    @SerializedName("cmd")
    @Expose
    private Integer type;
    @SerializedName("data")
    @Expose
    private Integer data;

    public String getMsgId() {
    return msgId;
    }

    public void setMsgId(String msgId) {
    this.msgId = msgId;
    }

    public Integer getType() {
    return type;
    }

    public void setType(Integer type) {
    this.type = type;
    }

    public Integer getData() {
    return data;
    }

    public void setData(Integer data) {
    this.data = data;
    }

    @Override
    public String toString() {
        return "FingerData{" + "msgId=" + msgId + ", type=" + type + ", data=" + data + '}';
    }

}
