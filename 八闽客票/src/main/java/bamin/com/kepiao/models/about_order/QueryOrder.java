package bamin.com.kepiao.models.about_order;

import java.io.Serializable;

/**
 * Created by zjb on 2016/2/25.
 */
public class QueryOrder implements Serializable{

    /**
     * AID : 2016-02-25-d8dc51e0-6abb-4f8e-9862-7743a4a41a6d
     * State : 4
     * BookTime : /Date(1456382175030)/
     * LineName : 永安-村头
     * SetoutTime : /Date(1456497000000)/
     * StartSiteName : 永安
     * EndSiteName : 村头
     * FullTicket : 1
     * CarryChild : false
     * Insured : false
     * PhoneNumber : 15871105320
     * ClientName : null
     * ClientIdentity : 350627198709080511|
     * GetTicketCode : 64001251573
     * PaymentID : null
     * Price : 0.01
     * SellerCompanyCode : BaMin
     * ScheduleCompanyCode : YongAn
     * MyStateDesc : 尚未取票
     */

    private String AID;
    private int State;
    private String BookTime;
    private String LineName;
    private String SetoutTime;
    private String StartSiteName;
    private String EndSiteName;
    private int FullTicket;
    private boolean CarryChild;
    private boolean Insured;
    private String PhoneNumber;
    private String ClientName;
    private String ClientIdentity;
    private String GetTicketCode;
    private String PaymentID;
    private double Price;
    private String SellerCompanyCode;
    private String ScheduleCompanyCode;
    private String MyStateDesc;

    public QueryOrder(String AID, int state, String bookTime, String lineName, String setoutTime, String startSiteName, String endSiteName, int fullTicket, boolean carryChild, boolean insured, String phoneNumber, String clientName, String clientIdentity, String getTicketCode, String paymentID, double price, String sellerCompanyCode, String scheduleCompanyCode, String myStateDesc) {
        this.AID = AID;
        State = state;
        BookTime = bookTime;
        LineName = lineName;
        SetoutTime = setoutTime;
        StartSiteName = startSiteName;
        EndSiteName = endSiteName;
        FullTicket = fullTicket;
        CarryChild = carryChild;
        Insured = insured;
        PhoneNumber = phoneNumber;
        ClientName = clientName;
        ClientIdentity = clientIdentity;
        GetTicketCode = getTicketCode;
        PaymentID = paymentID;
        Price = price;
        SellerCompanyCode = sellerCompanyCode;
        ScheduleCompanyCode = scheduleCompanyCode;
        MyStateDesc = myStateDesc;
    }

    public void setAID(String AID) {
        this.AID = AID;
    }

    public void setState(int State) {
        this.State = State;
    }

    public void setBookTime(String BookTime) {
        this.BookTime = BookTime;
    }

    public void setLineName(String LineName) {
        this.LineName = LineName;
    }

    public void setSetoutTime(String SetoutTime) {
        this.SetoutTime = SetoutTime;
    }

    public void setStartSiteName(String StartSiteName) {
        this.StartSiteName = StartSiteName;
    }

    public void setEndSiteName(String EndSiteName) {
        this.EndSiteName = EndSiteName;
    }

    public void setFullTicket(int FullTicket) {
        this.FullTicket = FullTicket;
    }

    public void setCarryChild(boolean CarryChild) {
        this.CarryChild = CarryChild;
    }

    public void setInsured(boolean Insured) {
        this.Insured = Insured;
    }

    public void setPhoneNumber(String PhoneNumber) {
        this.PhoneNumber = PhoneNumber;
    }

    public void setClientName(String ClientName) {
        this.ClientName = ClientName;
    }

    public void setClientIdentity(String ClientIdentity) {
        this.ClientIdentity = ClientIdentity;
    }

    public void setGetTicketCode(String GetTicketCode) {
        this.GetTicketCode = GetTicketCode;
    }

    public void setPaymentID(String PaymentID) {
        this.PaymentID = PaymentID;
    }

    public void setPrice(double Price) {
        this.Price = Price;
    }

    public void setSellerCompanyCode(String SellerCompanyCode) {
        this.SellerCompanyCode = SellerCompanyCode;
    }

    public void setScheduleCompanyCode(String ScheduleCompanyCode) {
        this.ScheduleCompanyCode = ScheduleCompanyCode;
    }

    public void setMyStateDesc(String MyStateDesc) {
        this.MyStateDesc = MyStateDesc;
    }

    public String getAID() {
        return AID;
    }

    public int getState() {
        return State;
    }

    public String getBookTime() {
        return BookTime;
    }

    public String getLineName() {
        return LineName;
    }

    public String getSetoutTime() {
        return SetoutTime;
    }

    public String getStartSiteName() {
        return StartSiteName;
    }

    public String getEndSiteName() {
        return EndSiteName;
    }

    public int getFullTicket() {
        return FullTicket;
    }

    public boolean isCarryChild() {
        return CarryChild;
    }

    public boolean isInsured() {
        return Insured;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public String getClientName() {
        return ClientName;
    }

    public String getClientIdentity() {
        return ClientIdentity;
    }

    public String getGetTicketCode() {
        return GetTicketCode;
    }

    public String getPaymentID() {
        return PaymentID;
    }

    public double getPrice() {
        return Price;
    }

    public String getSellerCompanyCode() {
        return SellerCompanyCode;
    }

    public String getScheduleCompanyCode() {
        return ScheduleCompanyCode;
    }

    public String getMyStateDesc() {
        return MyStateDesc;
    }
}
