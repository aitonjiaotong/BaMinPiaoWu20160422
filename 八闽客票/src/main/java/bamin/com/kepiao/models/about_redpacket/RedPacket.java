package bamin.com.kepiao.models.about_redpacket;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/3/1.
 */
public class RedPacket implements Serializable
{

    /**
     * id : 131
     * user_id : 5
     * date : 1456761600000
     * validity : 2016-03-31
     * amount : 3.0
     * flag : 0
     * activity : 4
     */

    private int id;
    private int user_id;
    private long date;
    private String validity;
    private double amount;
    private int flag;
    private int activity;

    public void setId(int id)
    {
        this.id = id;
    }

    public void setUser_id(int user_id)
    {
        this.user_id = user_id;
    }

    public void setDate(long date)
    {
        this.date = date;
    }

    public void setValidity(String validity)
    {
        this.validity = validity;
    }

    public void setAmount(double amount)
    {
        this.amount = amount;
    }

    public void setFlag(int flag)
    {
        this.flag = flag;
    }

    public void setActivity(int activity)
    {
        this.activity = activity;
    }

    public int getId()
    {
        return id;
    }

    public int getUser_id()
    {
        return user_id;
    }

    public long getDate()
    {
        return date;
    }

    public String getValidity()
    {
        return validity;
    }

    public double getAmount()
    {
        return amount;
    }

    public int getFlag()
    {
        return flag;
    }

    public int getActivity()
    {
        return activity;
    }
}
