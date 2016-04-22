package bamin.com.kepiao.models.about_used_contact;

import java.io.Serializable;

/**
 * Created by zjb on 2016/2/19.
 */
public class UsedContactInfo implements Serializable{
    private Integer id;
    private String name;
    private String idcard;
    private String phone;
    private Integer ischild;//0为成人 1为儿童
    private Integer account_id;

    public UsedContactInfo(Integer id, String name, String idcard, String phone, Integer ischild, Integer account_id) {
        this.id = id;
        this.name = name;
        this.idcard = idcard;
        this.phone = phone;
        this.ischild = ischild;
        this.account_id = account_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getIschild() {
        return ischild;
    }

    public void setIschild(Integer ischild) {
        this.ischild = ischild;
    }

    public Integer getAccount_id() {
        return account_id;
    }

    public void setAccount_id(Integer account_id) {
        this.account_id = account_id;
    }
    //    private String name;
//    private String personID;
//    private String phoneNum;
//
//    public UsedContactInfo(String name, String personID, String phoneNum) {
//        this.name = name;
//        this.personID = personID;
//        this.phoneNum = phoneNum;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getPersonID() {
//        return personID;
//    }
//
//    public void setPersonID(String personID) {
//        this.personID = personID;
//    }
//
//    public String getPhoneNum() {
//        return phoneNum;
//    }
//
//    public void setPhoneNum(String phoneNum) {
//        this.phoneNum = phoneNum;
//    }
}
