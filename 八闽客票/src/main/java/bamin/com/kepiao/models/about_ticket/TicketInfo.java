package bamin.com.kepiao.models.about_ticket;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/20.
 */
public class TicketInfo implements Serializable
{

    /**
     * ExecuteScheduleID : 2016-01-16-de4914b9-2558-4b8f-be12-746089247d4d
     * ExecuteDate : /Date(1455638400000)/
     * SetoutTime : /Date(1455686400000)/
     * State : 1
     * ScheduleType : 1
     * StartSiteID : 350702001
     * StartSiteName : 南平
     * EndSiteID : 350102001
     * EndSiteName : 福州北站
     * Limit : -1
     * StationName : 南平汽车站
     * TimeOffset : 0
     * GateName : 4
     * ScheduleBelong : 福州北站
     * PlanScheduleCode : 南加FZ003
     * LineName : 南平-福州北站（竹歧）
     * EndTicketTime : /Date(1455686400000)/
     * FullPrice : 74
     * HalfPrice : 37
     * Mileage : 187
     * FreeSeats : 0
     * LineViaSiteDesc : 南平,福州北站
     * CoachSeatNumber : 38
     * MaxTicket : 0
     * CoachGradeName : 中型高一
     * SeatTypeName : 普通
     * Rebate : false
     * ScheduleAttribute : 2
     * OuternalShare : true
     * StationCanTakeTicket : false
     * CompanyCode : NanPing
     * EndZoneName : 福建省 福州市 鼓楼区
     * RebateDescript : null
     * InsurePrice : 0
     */

    private String ExecuteScheduleID;
    private String ExecuteDate;
    private String SetoutTime;
    private int State;
    private int ScheduleType;
    private String StartSiteID;
    private String StartSiteName;
    private String EndSiteID;
    private String EndSiteName;
    private int Limit;
    private String StationName;
    private int TimeOffset;
    private String GateName;
    private String ScheduleBelong;
    private String PlanScheduleCode;
    private String LineName;
    private String EndTicketTime;
    private double FullPrice;
    private double HalfPrice;
    private int Mileage;
    private int FreeSeats;
    private String LineViaSiteDesc;
    private int CoachSeatNumber;
    private int MaxTicket;
    private String CoachGradeName;
    private String SeatTypeName;
    private boolean Rebate;
    private int ScheduleAttribute;
    private boolean OuternalShare;
    private boolean StationCanTakeTicket;
    private String CompanyCode;
    private String EndZoneName;
    private Object RebateDescript;
    private double InsurePrice;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TicketInfo that = (TicketInfo) o;

        if (State != that.State) return false;
        if (ScheduleType != that.ScheduleType) return false;
        if (Limit != that.Limit) return false;
        if (TimeOffset != that.TimeOffset) return false;
        if (Double.compare(that.FullPrice, FullPrice) != 0) return false;
        if (Double.compare(that.HalfPrice, HalfPrice) != 0) return false;
        if (Mileage != that.Mileage) return false;
        if (FreeSeats != that.FreeSeats) return false;
        if (CoachSeatNumber != that.CoachSeatNumber) return false;
        if (MaxTicket != that.MaxTicket) return false;
        if (Rebate != that.Rebate) return false;
        if (ScheduleAttribute != that.ScheduleAttribute) return false;
        if (OuternalShare != that.OuternalShare) return false;
        if (StationCanTakeTicket != that.StationCanTakeTicket) return false;
        if (Double.compare(that.InsurePrice, InsurePrice) != 0) return false;
        if (ExecuteScheduleID != null ? !ExecuteScheduleID.equals(that.ExecuteScheduleID) : that.ExecuteScheduleID != null)
            return false;
        if (ExecuteDate != null ? !ExecuteDate.equals(that.ExecuteDate) : that.ExecuteDate != null)
            return false;
        if (SetoutTime != null ? !SetoutTime.equals(that.SetoutTime) : that.SetoutTime != null)
            return false;
        if (StartSiteID != null ? !StartSiteID.equals(that.StartSiteID) : that.StartSiteID != null)
            return false;
        if (StartSiteName != null ? !StartSiteName.equals(that.StartSiteName) : that.StartSiteName != null)
            return false;
        if (EndSiteID != null ? !EndSiteID.equals(that.EndSiteID) : that.EndSiteID != null)
            return false;
        if (EndSiteName != null ? !EndSiteName.equals(that.EndSiteName) : that.EndSiteName != null)
            return false;
        if (StationName != null ? !StationName.equals(that.StationName) : that.StationName != null)
            return false;
        if (GateName != null ? !GateName.equals(that.GateName) : that.GateName != null)
            return false;
        if (ScheduleBelong != null ? !ScheduleBelong.equals(that.ScheduleBelong) : that.ScheduleBelong != null)
            return false;
        if (PlanScheduleCode != null ? !PlanScheduleCode.equals(that.PlanScheduleCode) : that.PlanScheduleCode != null)
            return false;
        if (LineName != null ? !LineName.equals(that.LineName) : that.LineName != null)
            return false;
        if (EndTicketTime != null ? !EndTicketTime.equals(that.EndTicketTime) : that.EndTicketTime != null)
            return false;
        if (LineViaSiteDesc != null ? !LineViaSiteDesc.equals(that.LineViaSiteDesc) : that.LineViaSiteDesc != null)
            return false;
        if (CoachGradeName != null ? !CoachGradeName.equals(that.CoachGradeName) : that.CoachGradeName != null)
            return false;
        if (SeatTypeName != null ? !SeatTypeName.equals(that.SeatTypeName) : that.SeatTypeName != null)
            return false;
        if (CompanyCode != null ? !CompanyCode.equals(that.CompanyCode) : that.CompanyCode != null)
            return false;
        if (EndZoneName != null ? !EndZoneName.equals(that.EndZoneName) : that.EndZoneName != null)
            return false;
        return !(RebateDescript != null ? !RebateDescript.equals(that.RebateDescript) : that.RebateDescript != null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = ExecuteScheduleID != null ? ExecuteScheduleID.hashCode() : 0;
        result = 31 * result + (ExecuteDate != null ? ExecuteDate.hashCode() : 0);
        result = 31 * result + (SetoutTime != null ? SetoutTime.hashCode() : 0);
        result = 31 * result + State;
        result = 31 * result + ScheduleType;
        result = 31 * result + (StartSiteID != null ? StartSiteID.hashCode() : 0);
        result = 31 * result + (StartSiteName != null ? StartSiteName.hashCode() : 0);
        result = 31 * result + (EndSiteID != null ? EndSiteID.hashCode() : 0);
        result = 31 * result + (EndSiteName != null ? EndSiteName.hashCode() : 0);
        result = 31 * result + Limit;
        result = 31 * result + (StationName != null ? StationName.hashCode() : 0);
        result = 31 * result + TimeOffset;
        result = 31 * result + (GateName != null ? GateName.hashCode() : 0);
        result = 31 * result + (ScheduleBelong != null ? ScheduleBelong.hashCode() : 0);
        result = 31 * result + (PlanScheduleCode != null ? PlanScheduleCode.hashCode() : 0);
        result = 31 * result + (LineName != null ? LineName.hashCode() : 0);
        result = 31 * result + (EndTicketTime != null ? EndTicketTime.hashCode() : 0);
        temp = Double.doubleToLongBits(FullPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(HalfPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + Mileage;
        result = 31 * result + FreeSeats;
        result = 31 * result + (LineViaSiteDesc != null ? LineViaSiteDesc.hashCode() : 0);
        result = 31 * result + CoachSeatNumber;
        result = 31 * result + MaxTicket;
        result = 31 * result + (CoachGradeName != null ? CoachGradeName.hashCode() : 0);
        result = 31 * result + (SeatTypeName != null ? SeatTypeName.hashCode() : 0);
        result = 31 * result + (Rebate ? 1 : 0);
        result = 31 * result + ScheduleAttribute;
        result = 31 * result + (OuternalShare ? 1 : 0);
        result = 31 * result + (StationCanTakeTicket ? 1 : 0);
        result = 31 * result + (CompanyCode != null ? CompanyCode.hashCode() : 0);
        result = 31 * result + (EndZoneName != null ? EndZoneName.hashCode() : 0);
        result = 31 * result + (RebateDescript != null ? RebateDescript.hashCode() : 0);
        temp = Double.doubleToLongBits(InsurePrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public void setExecuteScheduleID(String ExecuteScheduleID) {
        this.ExecuteScheduleID = ExecuteScheduleID;
    }

    public void setExecuteDate(String ExecuteDate) {
        this.ExecuteDate = ExecuteDate;
    }

    public void setSetoutTime(String SetoutTime) {
        this.SetoutTime = SetoutTime;
    }

    public void setState(int State) {
        this.State = State;
    }

    public void setScheduleType(int ScheduleType) {
        this.ScheduleType = ScheduleType;
    }

    public void setStartSiteID(String StartSiteID) {
        this.StartSiteID = StartSiteID;
    }

    public void setStartSiteName(String StartSiteName) {
        this.StartSiteName = StartSiteName;
    }

    public void setEndSiteID(String EndSiteID) {
        this.EndSiteID = EndSiteID;
    }

    public void setEndSiteName(String EndSiteName) {
        this.EndSiteName = EndSiteName;
    }

    public void setLimit(int Limit) {
        this.Limit = Limit;
    }

    public void setStationName(String StationName) {
        this.StationName = StationName;
    }

    public void setTimeOffset(int TimeOffset) {
        this.TimeOffset = TimeOffset;
    }

    public void setGateName(String GateName) {
        this.GateName = GateName;
    }

    public void setScheduleBelong(String ScheduleBelong) {
        this.ScheduleBelong = ScheduleBelong;
    }

    public void setPlanScheduleCode(String PlanScheduleCode) {
        this.PlanScheduleCode = PlanScheduleCode;
    }

    public void setLineName(String LineName) {
        this.LineName = LineName;
    }

    public void setEndTicketTime(String EndTicketTime) {
        this.EndTicketTime = EndTicketTime;
    }

    public void setFullPrice(int FullPrice) {
        this.FullPrice = FullPrice;
    }

    public void setHalfPrice(int HalfPrice) {
        this.HalfPrice = HalfPrice;
    }

    public void setMileage(int Mileage) {
        this.Mileage = Mileage;
    }

    public void setFreeSeats(int FreeSeats) {
        this.FreeSeats = FreeSeats;
    }

    public void setLineViaSiteDesc(String LineViaSiteDesc) {
        this.LineViaSiteDesc = LineViaSiteDesc;
    }

    public void setCoachSeatNumber(int CoachSeatNumber) {
        this.CoachSeatNumber = CoachSeatNumber;
    }

    public void setMaxTicket(int MaxTicket) {
        this.MaxTicket = MaxTicket;
    }

    public void setCoachGradeName(String CoachGradeName) {
        this.CoachGradeName = CoachGradeName;
    }

    public void setSeatTypeName(String SeatTypeName) {
        this.SeatTypeName = SeatTypeName;
    }

    public void setRebate(boolean Rebate) {
        this.Rebate = Rebate;
    }

    public void setScheduleAttribute(int ScheduleAttribute) {
        this.ScheduleAttribute = ScheduleAttribute;
    }

    public void setOuternalShare(boolean OuternalShare) {
        this.OuternalShare = OuternalShare;
    }

    public void setStationCanTakeTicket(boolean StationCanTakeTicket) {
        this.StationCanTakeTicket = StationCanTakeTicket;
    }

    public void setCompanyCode(String CompanyCode) {
        this.CompanyCode = CompanyCode;
    }

    public void setEndZoneName(String EndZoneName) {
        this.EndZoneName = EndZoneName;
    }

    public void setRebateDescript(Object RebateDescript) {
        this.RebateDescript = RebateDescript;
    }

    public void setInsurePrice(double InsurePrice) {
        this.InsurePrice = InsurePrice;
    }

    public String getExecuteScheduleID() {
        return ExecuteScheduleID;
    }

    public String getExecuteDate() {
        return ExecuteDate;
    }

    public String getSetoutTime() {
        return SetoutTime;
    }

    public int getState() {
        return State;
    }

    public int getScheduleType() {
        return ScheduleType;
    }

    public String getStartSiteID() {
        return StartSiteID;
    }

    public String getStartSiteName() {
        return StartSiteName;
    }

    public String getEndSiteID() {
        return EndSiteID;
    }

    public String getEndSiteName() {
        return EndSiteName;
    }

    public int getLimit() {
        return Limit;
    }

    public String getStationName() {
        return StationName;
    }

    public int getTimeOffset() {
        return TimeOffset;
    }

    public String getGateName() {
        return GateName;
    }

    public String getScheduleBelong() {
        return ScheduleBelong;
    }

    public String getPlanScheduleCode() {
        return PlanScheduleCode;
    }

    public String getLineName() {
        return LineName;
    }

    public String getEndTicketTime() {
        return EndTicketTime;
    }

    public double getFullPrice() {
        return FullPrice;
    }

    public double getHalfPrice() {
        return HalfPrice;
    }

    public int getMileage() {
        return Mileage;
    }

    public int getFreeSeats() {
        return FreeSeats;
    }

    public String getLineViaSiteDesc() {
        return LineViaSiteDesc;
    }

    public int getCoachSeatNumber() {
        return CoachSeatNumber;
    }

    public int getMaxTicket() {
        return MaxTicket;
    }

    public String getCoachGradeName() {
        return CoachGradeName;
    }

    public String getSeatTypeName() {
        return SeatTypeName;
    }

    public boolean isRebate() {
        return Rebate;
    }

    public int getScheduleAttribute() {
        return ScheduleAttribute;
    }

    public boolean isOuternalShare() {
        return OuternalShare;
    }

    public boolean isStationCanTakeTicket() {
        return StationCanTakeTicket;
    }

    public String getCompanyCode() {
        return CompanyCode;
    }

    public String getEndZoneName() {
        return EndZoneName;
    }

    public Object getRebateDescript() {
        return RebateDescript;
    }

    public double getInsurePrice() {
        return InsurePrice;
    }
}
