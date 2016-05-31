package bamin.com.kepiao.models;

/**
 * Created by zjb on 2016/5/31.
 */
public class LeftTimeTicket {

    /**
     * id : 1
     * message : 距离发车时间半小时内停止售票
     * time : 1800000
     */

    private int id;
    private String message;
    private long time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
