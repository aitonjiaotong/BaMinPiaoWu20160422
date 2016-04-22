package bamin.com.kepiao.models;

/**
 * Created by Administrator on 2016/2/4.
 */
public class User
{

    /**
     * id : 2
     * name : null
     * password : null
     * sex : null
     * image : null
     * phone : 1
     */

    private int id;
    private String name;
    private String password;
    private int sex;
    private String image;
    private String phone;
    private String login_id;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public int getSex()
    {
        return sex;
    }

    public void setSex(int sex)
    {
        this.sex = sex;
    }

    public String getImage()
    {
        return image;
    }

    public void setImage(String image)
    {
        this.image = image;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getLogin_id()
    {
        return login_id;
    }

    public void setLogin_id(String login_id)
    {
        this.login_id = login_id;
    }
}
