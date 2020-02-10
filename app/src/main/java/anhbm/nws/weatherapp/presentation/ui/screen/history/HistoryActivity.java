package anhbm.nws.weatherapp.presentation.ui.screen.history;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import anhbm.nws.weatherapp.R;

public class HistoryActivity extends AppCompatActivity {
    //    private SqlDatabase sqlDatabase;
//    private List<MainModel> mainModelList;
//    private String ngay, dogio, tocdogio, doam, trangthai, icon;
//    private double nhietdo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);


        /// inset du lieu vao sqldata
//        MainModel mainModel = new MainModel();
//        mainModel.setNgayDt(ngay);
//        mainModel.setNhietDoTemp(nhietdo);
//        mainModel.setDogioDeg(dogio);
//        mainModel.setTocdogioSpeed(tocdogio);
//        mainModel.setDoamHumidity(doam);
//        mainModel.setTrangthaiDescription(trangthai);
//        mainModel.setIconSql(icon);
//
//        sqlDatabase = new SqlDatabase(this);
//        long kiemtra = sqlDatabase.insetWheather(mainModel);
//        if (kiemtra > 0) {
//            Toast.makeText(MainActivity.this, "Thêm Thành Công", Toast.LENGTH_LONG).show();
//
//        } else {
//            Toast.makeText(MainActivity.this, "Thêm Thất Bại", Toast.LENGTH_LONG).show();
//        }
    }
}
