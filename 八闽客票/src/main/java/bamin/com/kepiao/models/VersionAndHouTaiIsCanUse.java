package bamin.com.kepiao.models;

/**
 * Created by zjb on 2016/4/2.
 */
public class VersionAndHouTaiIsCanUse {
    private int id;
    private int ableVersion;
    private boolean live;
    private String message;

    public VersionAndHouTaiIsCanUse(int id, int ableVersion, boolean live, String message) {
        this.id = id;
        this.ableVersion = ableVersion;
        this.live = live;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAbleVersion() {
        return ableVersion;
    }

    public void setAbleVersion(int ableVersion) {
        this.ableVersion = ableVersion;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
