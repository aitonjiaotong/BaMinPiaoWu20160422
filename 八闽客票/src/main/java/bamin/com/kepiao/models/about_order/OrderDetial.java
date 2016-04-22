package bamin.com.kepiao.models.about_order;

/**
 * Created by zjb on 2016/3/8.
 */
public class OrderDetial {

    /**
     * LogID : 2016-03-08-110341f1-cfd2-4f6c-9837-1ae100679a7a
     * ExecuteScheduleID : 2016-02-17-6ba9e844-916c-4e10-b123-43a7346866cf
     * ExecuteDate : /Date(1457366400000)/
     * PlanScheduleID : 2015-01-19-2455b330-7d67-467d-838e-fd640e7b7bb4
     * PlanScheduleCode : 内部测试
     * PlanScheduleType : 4
     * TicketSerialNumber : null
     * BillPreFix : null
     * BillNumber : 0
     * SeatID : 2016-02-17-c81ce78d-6cb9-4606-bf3c-0e3419c04cb7
     * SeatNumber : 5
     * State : 1
     * StartSiteID : 350481002
     * EndSiteID : 350481017
     * SetoutTime : /Date(1457447400000)/
     * TicketType : 0
     * CarryChild : false
     * Price : 0.01
     * Mileage : 34
     * SellTime : /Date(1457403309250)/
     * StartSiteName : 永安
     * EndSiteName : 村头
     * GetonStationID : 7f063412-692d-456e-b9ef-56954c7d12c1
     * GetonStationName : 永安车站
     * ExaminGateName : 3
     * CoachGradeName : 小型中级
     * SeatTypeName : 座席
     * IsNodeSell : false
     * SellDate : /Date(1457366400000)/
     * LineID : 9a96294f-7e6e-4281-b830-72ace6d37d26
     * SellerCompanyCode : BaMin
     * ScheduleCompanyCode : YongAn
     * BounceCompanyCode : null
     * Rebate : true
     * MyTicketTypeDesc : 全票
     * RebateDescript : null
     * BookTicketAID : 2016-03-08-90dc104c-2bd6-4c48-af0f-aa1a6c3f31c9
     * BounceCharge : 0
     * BounceMoney : 0
     * BounceOperateDate : /Date(1457366400000)/
     * BounceOperateTime : /Date(1457408408198)/
     * PassengerIdentity : 359001197507291027
     * PassengerName : 张先生
     * MyStateDesc : 正常
     */

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
    private int BounceCharge;
    private int BounceMoney;
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

    public void setPrice(double Price) {
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

    public int getBounceCharge() {
        return BounceCharge;
    }

    public int getBounceMoney() {
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
