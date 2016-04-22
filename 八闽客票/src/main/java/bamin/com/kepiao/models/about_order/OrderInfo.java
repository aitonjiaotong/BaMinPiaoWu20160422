package bamin.com.kepiao.models.about_order;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zjb on 2016/2/18.
 */
public class OrderInfo implements Serializable{

    /**
     * BookLogAID : 2016-02-18-ca18d82d-7769-43c2-95d3-535068f6e59a
     * GetTicketCode : 64546321775
     * Tickets : [{"LogID":"2016-02-18-9b72a320-e82b-450a-b0d6-61cfa68f5af4","ExecuteScheduleID":"2016-01-18-95b06eaf-dc95-4f05-b219-529b5d376c12","ExecuteDate":"/Date(1455724800000)/","PlanScheduleID":"2010-04-02-c110f97f-8279-4709-8aca-0f73619f2873","PlanScheduleCode":"永XG05","PlanScheduleType":5,"TicketSerialNumber":null,"BillPreFix":null,"BillNumber":0,"SeatID":"2016-01-18-8c430ac9-3a12-467a-ad31-f44c051ac0e7","SeatNumber":16,"State":1,"StartSiteID":"350481002","EndSiteID":"350203001","SetoutTime":"/Date(1455783600000)/","TicketType":0,"CarryChild":false,"Price":115,"Mileage":340,"SellTime":"/Date(1455771343217)/","StartSiteName":"永安","EndSiteName":"厦门","GetonStationID":"7f063412-692d-456e-b9ef-56954c7d12c1","GetonStationName":"永安车站","ExaminGateName":"7","CoachGradeName":"大型高三","SeatTypeName":"座席","IsNodeSell":false,"SellDate":"/Date(1455724800000)/","LineID":"2009-07-23-3b7d207c-a7b8-4b0c-b226-1aac4580c6c1","SellerCompanyCode":"BaMin","ScheduleCompanyCode":"YongAn","BounceCompanyCode":null,"Rebate":false,"MyTicketTypeDesc":"全票","RebateDescript":null,"BookTicketAID":"2016-02-18-ca18d82d-7769-43c2-95d3-535068f6e59a","BounceCharge":0,"BounceMoney":0,"BounceOperateDate":"/Date(1455724800000)/","BounceOperateTime":"/Date(1455771187427)/","PassengerIdentity":"421223199110240036","PassengerName":"张杰博","MyStateDesc":"正常"}]
     * TicketMoney : 115
     * Code : 0
     * Message : 成功
     * DepositeHasTakeOff : false
     */

    private String BookLogAID;
    private String GetTicketCode;
    private double TicketMoney;
    private int Code;
    private String Message;
    private boolean DepositeHasTakeOff;

    public OrderInfo(String bookLogAID, String getTicketCode, double ticketMoney, int code, String message, boolean depositeHasTakeOff, List<TicketsEntity> tickets) {
        BookLogAID = bookLogAID;
        GetTicketCode = getTicketCode;
        TicketMoney = ticketMoney;
        Code = code;
        Message = message;
        DepositeHasTakeOff = depositeHasTakeOff;
        Tickets = tickets;
    }

    /**
     * LogID : 2016-02-18-9b72a320-e82b-450a-b0d6-61cfa68f5af4
     * ExecuteScheduleID : 2016-01-18-95b06eaf-dc95-4f05-b219-529b5d376c12
     * ExecuteDate : /Date(1455724800000)/
     * PlanScheduleID : 2010-04-02-c110f97f-8279-4709-8aca-0f73619f2873
     * PlanScheduleCode : 永XG05
     * PlanScheduleType : 5
     * TicketSerialNumber : null
     * BillPreFix : null
     * BillNumber : 0
     * SeatID : 2016-01-18-8c430ac9-3a12-467a-ad31-f44c051ac0e7
     * SeatNumber : 16
     * State : 1
     * StartSiteID : 350481002
     * EndSiteID : 350203001
     * SetoutTime : /Date(1455783600000)/
     * TicketType : 0
     * CarryChild : false
     * Price : 115
     * Mileage : 340
     * SellTime : /Date(1455771343217)/
     * StartSiteName : 永安
     * EndSiteName : 厦门
     * GetonStationID : 7f063412-692d-456e-b9ef-56954c7d12c1
     * GetonStationName : 永安车站
     * ExaminGateName : 7
     * CoachGradeName : 大型高三
     * SeatTypeName : 座席
     * IsNodeSell : false
     * SellDate : /Date(1455724800000)/
     * LineID : 2009-07-23-3b7d207c-a7b8-4b0c-b226-1aac4580c6c1
     * SellerCompanyCode : BaMin
     * ScheduleCompanyCode : YongAn
     * BounceCompanyCode : null
     * Rebate : false
     * MyTicketTypeDesc : 全票
     * RebateDescript : null
     * BookTicketAID : 2016-02-18-ca18d82d-7769-43c2-95d3-535068f6e59a
     * BounceCharge : 0
     * BounceMoney : 0
     * BounceOperateDate : /Date(1455724800000)/
     * BounceOperateTime : /Date(1455771187427)/
     * PassengerIdentity : 421223199110240036
     * PassengerName : 张杰博
     * MyStateDesc : 正常
     */

    private List<TicketsEntity> Tickets;

    public void setBookLogAID(String BookLogAID) {
        this.BookLogAID = BookLogAID;
    }

    public void setGetTicketCode(String GetTicketCode) {
        this.GetTicketCode = GetTicketCode;
    }

    public void setTicketMoney(int TicketMoney) {
        this.TicketMoney = TicketMoney;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public void setDepositeHasTakeOff(boolean DepositeHasTakeOff) {
        this.DepositeHasTakeOff = DepositeHasTakeOff;
    }

    public void setTickets(List<TicketsEntity> Tickets) {
        this.Tickets = Tickets;
    }

    public String getBookLogAID() {
        return BookLogAID;
    }

    public String getGetTicketCode() {
        return GetTicketCode;
    }

    public double getTicketMoney() {
        return TicketMoney;
    }

    public int getCode() {
        return Code;
    }

    public String getMessage() {
        return Message;
    }

    public boolean isDepositeHasTakeOff() {
        return DepositeHasTakeOff;
    }

    public List<TicketsEntity> getTickets() {
        return Tickets;
    }

    public static class TicketsEntity implements Serializable{
        private String LogID;
        private String ExecuteScheduleID;
        private String ExecuteDate;
        private String PlanScheduleID;
        private String PlanScheduleCode;
        private int PlanScheduleType;
        private Object TicketSerialNumber;
        private Object BillPreFix;
        private int BillNumber;
        private String SeatID;
        private int SeatNumber;
        private int State;
        private String StartSiteID;
        private String EndSiteID;
        private String SetoutTime;
        private int TicketType;
        private boolean CarryChild;
        private double Price;
        private int Mileage;
        private String SellTime;
        private String StartSiteName;
        private String EndSiteName;
        private String GetonStationID;
        private String GetonStationName;
        private String ExaminGateName;
        private String CoachGradeName;
        private String SeatTypeName;
        private boolean IsNodeSell;
        private String SellDate;
        private String LineID;
        private String SellerCompanyCode;
        private String ScheduleCompanyCode;
        private Object BounceCompanyCode;
        private boolean Rebate;
        private String MyTicketTypeDesc;
        private Object RebateDescript;
        private String BookTicketAID;
        private double BounceCharge;
        private double BounceMoney;
        private String BounceOperateDate;
        private String BounceOperateTime;
        private String PassengerIdentity;
        private String PassengerName;
        private String MyStateDesc;

        public void setLogID(String LogID) {
            this.LogID = LogID;
        }

        public void setExecuteScheduleID(String ExecuteScheduleID) {
            this.ExecuteScheduleID = ExecuteScheduleID;
        }

        public void setExecuteDate(String ExecuteDate) {
            this.ExecuteDate = ExecuteDate;
        }

        public void setPlanScheduleID(String PlanScheduleID) {
            this.PlanScheduleID = PlanScheduleID;
        }

        public void setPlanScheduleCode(String PlanScheduleCode) {
            this.PlanScheduleCode = PlanScheduleCode;
        }

        public void setPlanScheduleType(int PlanScheduleType) {
            this.PlanScheduleType = PlanScheduleType;
        }

        public void setTicketSerialNumber(Object TicketSerialNumber) {
            this.TicketSerialNumber = TicketSerialNumber;
        }

        public void setBillPreFix(Object BillPreFix) {
            this.BillPreFix = BillPreFix;
        }

        public void setBillNumber(int BillNumber) {
            this.BillNumber = BillNumber;
        }

        public void setSeatID(String SeatID) {
            this.SeatID = SeatID;
        }

        public void setSeatNumber(int SeatNumber) {
            this.SeatNumber = SeatNumber;
        }

        public void setState(int State) {
            this.State = State;
        }

        public void setStartSiteID(String StartSiteID) {
            this.StartSiteID = StartSiteID;
        }

        public void setEndSiteID(String EndSiteID) {
            this.EndSiteID = EndSiteID;
        }

        public void setSetoutTime(String SetoutTime) {
            this.SetoutTime = SetoutTime;
        }

        public void setTicketType(int TicketType) {
            this.TicketType = TicketType;
        }

        public void setCarryChild(boolean CarryChild) {
            this.CarryChild = CarryChild;
        }

        public void setPrice(int Price) {
            this.Price = Price;
        }

        public void setMileage(int Mileage) {
            this.Mileage = Mileage;
        }

        public void setSellTime(String SellTime) {
            this.SellTime = SellTime;
        }

        public void setStartSiteName(String StartSiteName) {
            this.StartSiteName = StartSiteName;
        }

        public void setEndSiteName(String EndSiteName) {
            this.EndSiteName = EndSiteName;
        }

        public void setGetonStationID(String GetonStationID) {
            this.GetonStationID = GetonStationID;
        }

        public void setGetonStationName(String GetonStationName) {
            this.GetonStationName = GetonStationName;
        }

        public void setExaminGateName(String ExaminGateName) {
            this.ExaminGateName = ExaminGateName;
        }

        public void setCoachGradeName(String CoachGradeName) {
            this.CoachGradeName = CoachGradeName;
        }

        public void setSeatTypeName(String SeatTypeName) {
            this.SeatTypeName = SeatTypeName;
        }

        public void setIsNodeSell(boolean IsNodeSell) {
            this.IsNodeSell = IsNodeSell;
        }

        public void setSellDate(String SellDate) {
            this.SellDate = SellDate;
        }

        public void setLineID(String LineID) {
            this.LineID = LineID;
        }

        public void setSellerCompanyCode(String SellerCompanyCode) {
            this.SellerCompanyCode = SellerCompanyCode;
        }

        public void setScheduleCompanyCode(String ScheduleCompanyCode) {
            this.ScheduleCompanyCode = ScheduleCompanyCode;
        }

        public void setBounceCompanyCode(Object BounceCompanyCode) {
            this.BounceCompanyCode = BounceCompanyCode;
        }

        public void setRebate(boolean Rebate) {
            this.Rebate = Rebate;
        }

        public void setMyTicketTypeDesc(String MyTicketTypeDesc) {
            this.MyTicketTypeDesc = MyTicketTypeDesc;
        }

        public void setRebateDescript(Object RebateDescript) {
            this.RebateDescript = RebateDescript;
        }

        public void setBookTicketAID(String BookTicketAID) {
            this.BookTicketAID = BookTicketAID;
        }

        public void setBounceCharge(int BounceCharge) {
            this.BounceCharge = BounceCharge;
        }

        public void setBounceMoney(int BounceMoney) {
            this.BounceMoney = BounceMoney;
        }

        public void setBounceOperateDate(String BounceOperateDate) {
            this.BounceOperateDate = BounceOperateDate;
        }

        public void setBounceOperateTime(String BounceOperateTime) {
            this.BounceOperateTime = BounceOperateTime;
        }

        public void setPassengerIdentity(String PassengerIdentity) {
            this.PassengerIdentity = PassengerIdentity;
        }

        public void setPassengerName(String PassengerName) {
            this.PassengerName = PassengerName;
        }

        public void setMyStateDesc(String MyStateDesc) {
            this.MyStateDesc = MyStateDesc;
        }

        public String getLogID() {
            return LogID;
        }

        public String getExecuteScheduleID() {
            return ExecuteScheduleID;
        }

        public String getExecuteDate() {
            return ExecuteDate;
        }

        public String getPlanScheduleID() {
            return PlanScheduleID;
        }

        public String getPlanScheduleCode() {
            return PlanScheduleCode;
        }

        public int getPlanScheduleType() {
            return PlanScheduleType;
        }

        public Object getTicketSerialNumber() {
            return TicketSerialNumber;
        }

        public Object getBillPreFix() {
            return BillPreFix;
        }

        public int getBillNumber() {
            return BillNumber;
        }

        public String getSeatID() {
            return SeatID;
        }

        public int getSeatNumber() {
            return SeatNumber;
        }

        public int getState() {
            return State;
        }

        public String getStartSiteID() {
            return StartSiteID;
        }

        public String getEndSiteID() {
            return EndSiteID;
        }

        public String getSetoutTime() {
            return SetoutTime;
        }

        public int getTicketType() {
            return TicketType;
        }

        public boolean isCarryChild() {
            return CarryChild;
        }

        public double getPrice() {
            return Price;
        }

        public int getMileage() {
            return Mileage;
        }

        public String getSellTime() {
            return SellTime;
        }

        public String getStartSiteName() {
            return StartSiteName;
        }

        public String getEndSiteName() {
            return EndSiteName;
        }

        public String getGetonStationID() {
            return GetonStationID;
        }

        public String getGetonStationName() {
            return GetonStationName;
        }

        public String getExaminGateName() {
            return ExaminGateName;
        }

        public String getCoachGradeName() {
            return CoachGradeName;
        }

        public String getSeatTypeName() {
            return SeatTypeName;
        }

        public boolean isIsNodeSell() {
            return IsNodeSell;
        }

        public String getSellDate() {
            return SellDate;
        }

        public String getLineID() {
            return LineID;
        }

        public String getSellerCompanyCode() {
            return SellerCompanyCode;
        }

        public String getScheduleCompanyCode() {
            return ScheduleCompanyCode;
        }

        public Object getBounceCompanyCode() {
            return BounceCompanyCode;
        }

        public boolean isRebate() {
            return Rebate;
        }

        public String getMyTicketTypeDesc() {
            return MyTicketTypeDesc;
        }

        public Object getRebateDescript() {
            return RebateDescript;
        }

        public String getBookTicketAID() {
            return BookTicketAID;
        }

        public double getBounceCharge() {
            return BounceCharge;
        }

        public double getBounceMoney() {
            return BounceMoney;
        }

        public String getBounceOperateDate() {
            return BounceOperateDate;
        }

        public String getBounceOperateTime() {
            return BounceOperateTime;
        }

        public String getPassengerIdentity() {
            return PassengerIdentity;
        }

        public String getPassengerName() {
            return PassengerName;
        }

        public String getMyStateDesc() {
            return MyStateDesc;
        }
    }
}
