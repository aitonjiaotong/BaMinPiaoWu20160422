package bamin.com.kepiao.models.about_order;

import java.io.Serializable;

/**
 * Created by zjb on 2016/2/26.
 */
public class OrderList implements Serializable{
    private Integer id;
    private String date;//下订单时间
    private String bookLogAID;
    private Integer account_id;
    private Integer redEnvelope_id;//红包id

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBookLogAID() {
        return bookLogAID;
    }

    public void setBookLogAID(String bookLogAID) {
        this.bookLogAID = bookLogAID;
    }

    public Integer getAccount_id() {
        return account_id;
    }

    public void setAccount_id(Integer account_id) {
        this.account_id = account_id;
    }

    public Integer getRedEnvelope_id() {
        return redEnvelope_id;
    }

    public void setRedEnvelope_id(Integer redEnvelope_id) {
        this.redEnvelope_id = redEnvelope_id;
    }
}
