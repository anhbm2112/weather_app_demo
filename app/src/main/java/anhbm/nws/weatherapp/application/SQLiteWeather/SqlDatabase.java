package anhbm.nws.weatherapp.application.SQLiteWeather;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import anhbm.nws.weatherapp.api.weather.modelWeatherList.ListAPI;
import anhbm.nws.weatherapp.api.weather.modelWeatherList.WeatherList;
import anhbm.nws.weatherapp.presentation.ui.screen.main.mvp.MainModel;

public class SqlDatabase extends SQLiteOpenHelper {

    public SqlDatabase(Context context) {
        super(context, "wheather", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(MainModel.CREATE_TABLE_WHEATHER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MainModel.TABLE_WEATHER_SQL);
        onCreate(sqLiteDatabase);
    }

    public long insetWheather(JSONObject jsonObject,MainModel mainModel) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MainModel.COLUMN_NGAY, mainModel.getNgayDt());
        contentValues.put(MainModel.COLUMN_NHIETDO, mainModel.getNhietDoTemp());
        contentValues.put(MainModel.COLUMN_DOGIO, mainModel.getDogioDeg());
        contentValues.put(MainModel.COLUMN_TOCDOGIO, mainModel.getTocdogioSpeed());
        contentValues.put(MainModel.COLUMN_DOAM, mainModel.getDoamHumidity());
        contentValues.put(MainModel.COLUMN_TRANGTHAI, mainModel.getTrangthaiDescription());
        contentValues.put(MainModel.COLUMN_ICON, mainModel.getIconSql());

        long result = sqLiteDatabase.insert(MainModel.TABLE_WEATHER_SQL, null, contentValues);
        sqLiteDatabase.close();
        return result;
    }

    public List<MainModel> getAllClass() {
        List<MainModel> mainModelList = new ArrayList<>();
        String SELECT = " SELECT * FROM " + MainModel.TABLE_WEATHER_SQL;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String ngay = cursor.getString(cursor.getColumnIndex(MainModel.COLUMN_NGAY));
                Double nhietdo = cursor.getDouble(cursor.getColumnIndex(MainModel.COLUMN_NHIETDO));
                String dogio = cursor.getString(cursor.getColumnIndex(MainModel.COLUMN_DOGIO));
                String tocdogio = cursor.getString(cursor.getColumnIndex(MainModel.COLUMN_NGAY));
                String doam = cursor.getString(cursor.getColumnIndex(MainModel.COLUMN_NGAY));
                String trangthai = cursor.getString(cursor.getColumnIndex(MainModel.COLUMN_NGAY));
                String icon = cursor.getString(cursor.getColumnIndex(MainModel.COLUMN_NGAY));
                MainModel mainModel = new MainModel();
                mainModel.setNgayDt(ngay);
                mainModel.setNhietDoTemp(nhietdo);
                mainModel.setDogioDeg(dogio);
                mainModel.setTocdogioSpeed(tocdogio);
                mainModel.setDoamHumidity(doam);
                mainModel.setTrangthaiDescription(trangthai);
                mainModel.setIconSql(icon);
                mainModelList.add(mainModel);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return mainModelList;
    }
}
