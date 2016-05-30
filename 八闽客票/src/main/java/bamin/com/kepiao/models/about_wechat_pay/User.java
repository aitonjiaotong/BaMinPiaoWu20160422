package bamin.com.kepiao.models.about_wechat_pay;

/**
 * Created by Administrator on 2016/2/4.
 */
public class User {
    /**
     * success : true
     * message : 登陆成功
     * contains : {"id":5,"name":"13799283714","password":"PuQFBPpe6wIFm2b87b/yKw==","sex":null,"login_id":"78c97a0d-bda3-4696-8fbc-fd5925ff337f","phone":"13799283714","image":null,"idCardImage":null,"drivingLicenseImage":null,"idCardImage_back":null,"drivingLicenseImage_back":null}
     */

    private boolean success;
    private String message;
    /**
     * id : 5
     * name : 13799283714
     * password : PuQFBPpe6wIFm2b87b/yKw==
     * sex : null
     * login_id : 78c97a0d-bda3-4696-8fbc-fd5925ff337f
     * phone : 13799283714
     * image : null
     */

    private ContainsBean contains;

    public boolean isSuccess()
    {
        return success;
    }

    public void setSuccess(boolean success)
    {
        this.success = success;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public ContainsBean getContains()
    {
        return contains;
    }

    public void setContains(ContainsBean contains)
    {
        this.contains = contains;
    }

    public static class ContainsBean
    {
        private int id;
        private String name;
        private String password;
        private int sex;
        private String login_id;
        private String phone;
        private String image;

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

        public String getLogin_id()
        {
            return login_id;
        }

        public void setLogin_id(String login_id)
        {
            this.login_id = login_id;
        }

        public String getPhone()
        {
            return phone;
        }

        public void setPhone(String phone)
        {
            this.phone = phone;
        }

        public String getImage()
        {
            return image;
        }

        public void setImage(String image)
        {
            this.image = image;
        }

    }
}
