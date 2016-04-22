package bamin.com.kepiao.models.about_banner;

/**
 * Created by Administrator on 2016/2/29.
 */
public class BannerInfo
{


    /**
     * id : 1
     * name : banner01
     * url : http://120.24.46.15:8080/bmpw/img/banner01.png
     * url2 : http://120.24.46.15:8080/bmpw/front/getRedEnvelope?activity=4
     * flag : null
     */

    private int id;
    private String name;
    private String url;
    private String url2;
    private Object flag;

    public void setId(int id)
    {
        this.id = id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public void setUrl2(String url2)
    {
        this.url2 = url2;
    }

    public void setFlag(Object flag)
    {
        this.flag = flag;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getUrl()
    {
        return url;
    }

    public String getUrl2()
    {
        return url2;
    }

    public Object getFlag()
    {
        return flag;
    }
}
