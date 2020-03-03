package anhbm.nws.weatherapp.presentation.ui.screen.main.mvp;

public class MainModel {
    String ngayDt;
    double nhietDoTemp;
    String dogioDeg;
    String tocdogioSpeed;
    String doamHumidity;
    String trangthaiDescription;
    String iconSql;

    public static final String TABLE_WEATHER_SQL = "Wheather";
    public static final String COLUMN_NGAY = "ngay";
    public static final String COLUMN_NHIETDO = "nhietdo";
    public static final String COLUMN_DOAM = "doam";
    public static final String COLUMN_DOGIO = "dogio";
    public static final String COLUMN_TOCDOGIO = "tocdogio";
    public static final String COLUMN_TRANGTHAI = "trangthai";
    public static final String COLUMN_ICON = "icon";

    public static final String CREATE_TABLE_WHEATHER = " CREATE TABLE " + TABLE_WEATHER_SQL + "(" + COLUMN_NGAY +
            " INTEGER PRIMARY KEY, " + COLUMN_NHIETDO + " VARCHAR, " + COLUMN_DOAM + " VARCHAR, " + COLUMN_DOGIO + "" +
            " VARCHAR, " + COLUMN_TOCDOGIO + " VARCHAR, " + COLUMN_TRANGTHAI + " VARCHAR, " + COLUMN_ICON + " VARCHAR) ";

    public String getNgayDt() {
        return ngayDt;
    }

    public void setNgayDt(String ngayDt) {
        this.ngayDt = ngayDt;
    }

    public double getNhietDoTemp() {
        return nhietDoTemp;
    }

    public void setNhietDoTemp(double nhietDoTemp) {
        this.nhietDoTemp = nhietDoTemp;
    }

    public String getDogioDeg() {
        return dogioDeg;
    }

    public void setDogioDeg(String dogioDeg) {
        this.dogioDeg = dogioDeg;
    }

    public String getTocdogioSpeed() {
        return tocdogioSpeed;
    }

    public void setTocdogioSpeed(String tocdogioSpeed) {
        this.tocdogioSpeed = tocdogioSpeed;
    }

    public String getDoamHumidity() {
        return doamHumidity;
    }

    public void setDoamHumidity(String doamHumidity) {
        this.doamHumidity = doamHumidity;
    }

    public String getTrangthaiDescription() {
        return trangthaiDescription;
    }

    public void setTrangthaiDescription(String trangthaiDescription) {
        this.trangthaiDescription = trangthaiDescription;
    }

    public String getIconSql() {
        return iconSql;
    }

    public void setIconSql(String iconSql) {
        this.iconSql = iconSql;
    }
}
