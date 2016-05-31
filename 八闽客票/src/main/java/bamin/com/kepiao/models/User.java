package bamin.com.kepiao.models;

/**
 * Created by Administrator on 2016/2/4.
 */
public class User {

    /**
     * success : true
     * message : 登陆成功
     * contains : {"id":4,"name":"15871105320","password":"PuQFBPpe6wIFm2b87b/yKw==","login_id":"45612378","sex":null,"phone":"15871105320","image":""}
     */

    private boolean success;
    private String message;
    /**
     * id : 4
     * name : 15871105320
     * password : PuQFBPpe6wIFm2b87b/yKw==
     * login_id : 45612378
     * sex : null
     * phone : 15871105320
     * image :
     */

    private ContainsEntity contains;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ContainsEntity getContains() {
        return contains;
    }

    public void setContains(ContainsEntity contains) {
        this.contains = contains;
    }

    public static class ContainsEntity {
        private int id;
        private String name;
        private String password;
        private String login_id;
        private int sex;
        private String phone;
        private String image;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getLogin_id() {
            return login_id;
        }

        public void setLogin_id(String login_id) {
            this.login_id = login_id;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
