package bamin.com.kepiao.models.about_companysubzone;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class CompanySubZone {

    @Expose
    private String fullName;
    @Expose
    private String fullCode;
    @Expose
    private String zoneName;
    @Expose
    private Integer parentZoneID;
    @Expose
    private List<SubZone> subZones = new ArrayList<SubZone>();
    @Expose
    private Integer zoneCode;
    @Expose
    private Integer zoneID;

    /**
     * 
     * @return
     *     The fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * 
     * @param fullName
     *     The fullName
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * 
     * @return
     *     The fullCode
     */
    public String getFullCode() {
        return fullCode;
    }

    /**
     * 
     * @param fullCode
     *     The fullCode
     */
    public void setFullCode(String fullCode) {
        this.fullCode = fullCode;
    }

    /**
     * 
     * @return
     *     The zoneName
     */
    public String getZoneName() {
        return zoneName;
    }

    /**
     * 
     * @param zoneName
     *     The zoneName
     */
    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    /**
     * 
     * @return
     *     The parentZoneID
     */
    public Integer getParentZoneID() {
        return parentZoneID;
    }

    /**
     * 
     * @param parentZoneID
     *     The parentZoneID
     */
    public void setParentZoneID(Integer parentZoneID) {
        this.parentZoneID = parentZoneID;
    }

    /**
     * 
     * @return
     *     The subZones
     */
    public List<SubZone> getSubZones() {
        return subZones;
    }

    /**
     * 
     * @param subZones
     *     The subZones
     */
    public void setSubZones(List<SubZone> subZones) {
        this.subZones = subZones;
    }

    /**
     * 
     * @return
     *     The zoneCode
     */
    public Integer getZoneCode() {
        return zoneCode;
    }

    /**
     * 
     * @param zoneCode
     *     The zoneCode
     */
    public void setZoneCode(Integer zoneCode) {
        this.zoneCode = zoneCode;
    }

    /**
     * 
     * @return
     *     The zoneID
     */
    public Integer getZoneID() {
        return zoneID;
    }

    /**
     * 
     * @param zoneID
     *     The zoneID
     */
    public void setZoneID(Integer zoneID) {
        this.zoneID = zoneID;
    }

}
