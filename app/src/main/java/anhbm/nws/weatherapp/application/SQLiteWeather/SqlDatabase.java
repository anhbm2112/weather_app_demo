package anhbm.nws.weatherapp.application.SQLiteWeather;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;
import anhbm.nws.weatherapp.presentation.ui.screen.history.mvp.HistoryModel;
import anhbm.nws.weatherapp.presentation.ui.screen.main.mvp.MainModel;

public class SqlDatabase extends SQLiteOpenHelper {

    public SqlDatabase(Context context) {
        super(context, "wheather", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        sqLiteDatabase.execSQL(MainModel.CREATE_TABLE_WHEATHER);
        sqLiteDatabase.execSQL(HistoryModel.CREATE_TABLE_HISTORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MainModel.TABLE_WEATHER_SQL);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + HistoryModel.TABLE_HISTORY_SQL);
        onCreate(sqLiteDatabase);

    }

    public long insetHistory(HistoryModel historyModel) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(HistoryModel.COLUMN_GIO, historyModel.getGiohientai());
        contentValues.put(HistoryModel.COLUMN_THANHPHOCHON, historyModel.getThanhpho());
        contentValues.put(HistoryModel.COLUMN_NGAY, historyModel.getNgayhientai());
        contentValues.put(HistoryModel.COLUMN_NHIETDO, historyModel.getNhietDoTemp());
        contentValues.put(HistoryModel.COLUMN_DOGIO, historyModel.getDogioDeg());
        contentValues.put(HistoryModel.COLUMN_TOCDOGIO, historyModel.getTocdogioSpeed());
        contentValues.put(HistoryModel.COLUMN_DOAM, historyModel.getDoamHumidity());
        contentValues.put(HistoryModel.COLUMN_TRANGTHAI, historyModel.getTrangthaiDescription());
        contentValues.put(HistoryModel.COLUMN_ICON, historyModel.getIconSql());
        long result = sqLiteDatabase.insert(HistoryModel.TABLE_HISTORY_SQL, null, contentValues);
        sqLiteDatabase.close();
        return result;
    }

    public void deleteHistory(String s) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(HistoryModel.TABLE_HISTORY_SQL, HistoryModel.COLUMN_GIO + "=?", new String[]{String.valueOf(s)});
        db.close();
    }


    public List<HistoryModel> getAllHistory() {
        List<HistoryModel> historyModelList = new ArrayList<>();
        String SELECT = " SELECT * FROM " + HistoryModel.TABLE_HISTORY_SQL;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String ngay = cursor.getString(cursor.getColumnIndex(HistoryModel.COLUMN_NGAY));
                String gio = cursor.getString(cursor.getColumnIndex(HistoryModel.COLUMN_GIO));
                String thanhpho = cursor.getString(cursor.getColumnIndex(HistoryModel.COLUMN_THANHPHOCHON));
                Double nhietdo = cursor.getDouble(cursor.getColumnIndex(HistoryModel.COLUMN_NHIETDO));
                String dogio = cursor.getString(cursor.getColumnIndex(HistoryModel.COLUMN_DOGIO));
                String tocdogio = cursor.getString(cursor.getColumnIndex(HistoryModel.COLUMN_TOCDOGIO));
                String doam = cursor.getString(cursor.getColumnIndex(HistoryModel.COLUMN_DOAM));
                String trangthai = cursor.getString(cursor.getColumnIndex(HistoryModel.COLUMN_TRANGTHAI));
                String icon = cursor.getString(cursor.getColumnIndex(HistoryModel.COLUMN_ICON));
                HistoryModel historyModel = new HistoryModel();
                historyModel.setNgayhientai(ngay);
                historyModel.setGiohientai(gio);
                historyModel.setNhietDoTemp(nhietdo);
                historyModel.setDogioDeg(dogio);
                historyModel.setTocdogioSpeed(tocdogio);
                historyModel.setDoamHumidity(doam);
                historyModel.setTrangthaiDescription(trangthai);
                historyModel.setIconSql(icon);
                historyModel.setThanhpho(thanhpho);
                historyModelList.add(historyModel);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return historyModelList;
    }

}
