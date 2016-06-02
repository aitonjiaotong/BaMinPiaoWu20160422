package bamin.com.kepiao.models.about_order;

import java.util.List;

/**
 * Created by zjb on 2016/2/26.
 * 用户的所有订单号
 */
public class AccountOrder {

    /**
     * contains : [{"id":1334,"date":1464848696000,"bookLogAID":"2016-06-02-c15dddba-1943-4df1-9cca-c82810189db7","account_id":123,"redEnvelope_id":null,"phone":"null","insure":3,"price":121,"real_pay":null,"status":2,"sum":null,"startSiteName":null,"endSiteName":null,"getTicketCode":null,"identify":null,"bookTime":null,"setoutTime":null,"pay_model":null,"serial":null,"yuliu":null,"carryChild":null}]
     * num : 1
     */

    private int num;
    /**
     * id : 1334
     * date : 1464848696000
     * bookLogAID : 2016-06-02-c15dddba-1943-4df1-9cca-c82810189db7
     * account_id : 123
     * redEnvelope_id : null
     * phone : null
     * insure : 3.0
     * price : 121.0
     * real_pay : null
     * status : 2
     * sum : null
     * startSiteName : null
     * endSiteName : null
     * getTicketCode : null
     * identify : null
     * bookTime : null
     * setoutTime : null
     * pay_model : null
     * serial : null
     * yuliu : null
     * carryChild : null
     */

    private List<ContainsEntity> contains;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public List<ContainsEntity> getContains() {
        return contains;
    }

    public void setContains(List<ContainsEntity> contains) {
        this.contains = contains;
    }

    public static class ContainsEntity {
        private int id;
        private long date;
        private String bookLogAID;
        private int account_id;
        private Object redEnvelope_id;
        private String phone;
        private double insure;
        private double price;
        private Object real_pay;
        private int status;
        private Object sum;
        private Object startSiteName;
        private Object endSiteName;
        private Object getTicketCode;
        private Object identify;
        private Object bookTime;
        private Object setoutTime;
        private Object pay_model;
        private Object serial;
        private Object yuliu;
        private Object carryChild;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public long getDate() {
            return date;
        }

        public void setDate(long date) {
            this.date = date;
        }

        public String getBookLogAID() {
            return bookLogAID;
        }

        public void setBookLogAID(String bookLogAID) {
            this.bookLogAID = bookLogAID;
        }

        public int getAccount_id() {
            return account_id;
        }

        public void setAccount_id(int account_id) {
            this.account_id = account_id;
        }

        public Object getRedEnvelope_id() {
            return redEnvelope_id;
        }

        public void setRedEnvelope_id(Object redEnvelope_id) {
            this.redEnvelope_id = redEnvelope_id;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public double getInsure() {
            return insure;
        }

        public void setInsure(double insure) {
            this.insure = insure;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public Object getReal_pay() {
            return real_pay;
        }

        public void setReal_pay(Object real_pay) {
            this.real_pay = real_pay;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public Object getSum() {
            return sum;
        }

        public void setSum(Object sum) {
            this.sum = sum;
        }

        public Object getStartSiteName() {
            return startSiteName;
        }

        public void setStartSiteName(Object startSiteName) {
            this.startSiteName = startSiteName;
        }

        public Object getEndSiteName() {
            return endSiteName;
        }

        public void setEndSiteName(Object endSiteName) {
            this.endSiteName = endSiteName;
        }

        public Object getGetTicketCode() {
            return getTicketCode;
        }

        public void setGetTicketCode(Object getTicketCode) {
            this.getTicketCode = getTicketCode;
        }

        public Object getIdentify() {
            return identify;
        }

        public void setIdentify(Object identify) {
            this.identify = identify;
        }

        public Object getBookTime() {
            return bookTime;
        }

        public void setBookTime(Object bookTime) {
            this.bookTime = bookTime;
        }

        public Object getSetoutTime() {
            return setoutTime;
        }

        public void setSetoutTime(Object setoutTime) {
            this.setoutTime = setoutTime;
        }

        public Object getPay_model() {
            return pay_model;
        }

        public void setPay_model(Object pay_model) {
            this.pay_model = pay_model;
        }

        public Object getSerial() {
            return serial;
        }

        public void setSerial(Object serial) {
            this.serial = serial;
        }

        public Object getYuliu() {
            return yuliu;
        }

        public void setYuliu(Object yuliu) {
            this.yuliu = yuliu;
        }

        public Object getCarryChild() {
            return carryChild;
        }

        public void setCarryChild(Object carryChild) {
            this.carryChild = carryChild;
        }
    }
}
