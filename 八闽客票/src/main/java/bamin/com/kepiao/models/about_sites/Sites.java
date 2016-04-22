package bamin.com.kepiao.models.about_sites;

/**
 * Created by Administrator on 2016/2/26.
 */
public class Sites
{

    /**
     * flag : 0
     * zoneName : null
     * zoneID : 501
     * siteCode : ZS
     * siteID : 442001001
     * siteName : 中山
     */

    private int flag;
    private Object zoneName;
    private int zoneID;
    private String siteCode;
    private int siteID;
    private String siteName;

    public void setFlag(int flag)
    {
        this.flag = flag;
    }

    public void setZoneName(Object zoneName)
    {
        this.zoneName = zoneName;
    }

    public void setZoneID(int zoneID)
    {
        this.zoneID = zoneID;
    }

    public void setSiteCode(String siteCode)
    {
        this.siteCode = siteCode;
    }

    public void setSiteID(int siteID)
    {
        this.siteID = siteID;
    }

    public void setSiteName(String siteName)
    {
        this.siteName = siteName;
    }

    public int getFlag()
    {
        return flag;
    }

    public Object getZoneName()
    {
        return zoneName;
    }

    public int getZoneID()
    {
        return zoneID;
    }

    public String getSiteCode()
    {
        return siteCode;
    }

    public int getSiteID()
    {
        return siteID;
    }

    public String getSiteName()
    {
        return siteName;
    }
}
