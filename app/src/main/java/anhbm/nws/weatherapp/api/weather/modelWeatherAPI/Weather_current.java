
package anhbm.nws.weatherapp.api.weather.modelWeatherAPI;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Weather_current {

    @SerializedName("ts")
    @Expose
    private String ts;
    @SerializedName("tp")
    @Expose
    private Integer tp;
    @SerializedName("pr")
    @Expose
    private Integer pr;
    @SerializedName("hu")
    @Expose
    private Integer hu;
    @SerializedName("ws")
    @Expose
    private Double ws;
    @SerializedName("wd")
    @Expose
    private Integer wd;
    @SerializedName("ic")
    @Expose
    private String ic;

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public Integer getTp() {
        return tp;
    }

    public void setTp(Integer tp) {
        this.tp = tp;
    }

    public Integer getPr() {
        return pr;
    }

    public void setPr(Integer pr) {
        this.pr = pr;
    }

    public Integer getHu() {
        return hu;
    }

    public void setHu(Integer hu) {
        this.hu = hu;
    }

    public Double getWs() {
        return ws;
    }

    public void setWs(Double ws) {
        this.ws = ws;
    }

    public Integer getWd() {
        return wd;
    }

    public void setWd(Integer wd) {
        this.wd = wd;
    }

    public String getIc() {
        return ic;
    }

    public void setIc(String ic) {
        this.ic = ic;
    }

}
