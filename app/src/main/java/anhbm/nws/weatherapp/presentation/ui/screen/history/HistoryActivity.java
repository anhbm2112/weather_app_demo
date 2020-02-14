package anhbm.nws.weatherapp.presentation.ui.screen.history;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

import anhbm.nws.weatherapp.R;
import anhbm.nws.weatherapp.api.weather.modelWeatherList.ListAPI;
import anhbm.nws.weatherapp.application.SQLiteWeather.SqlDatabase;
import anhbm.nws.weatherapp.presentation.presenters.HistoryPresenter;
import anhbm.nws.weatherapp.presentation.ui.adapter.HistoryAdapter;
import anhbm.nws.weatherapp.presentation.ui.screen.history.mvp.HistoryModel;
import anhbm.nws.weatherapp.presentation.ui.screen.history.mvp.HistoryPresenterImpl;

public class HistoryActivity extends AppCompatActivity implements HistoryPresenter, View.OnClickListener {
    private HistoryPresenterImpl historyPresenter;
    private SqlDatabase sqlDatabase;
    private List<HistoryModel> historyModelList;
    private HistoryAdapter historyAdapter;
    private RecyclerView recyclerView;
    private ImageView imageback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        recyclerView = findViewById(R.id.recyclerViewHistory);
        imageback = findViewById(R.id.back_history);
        imageback.setOnClickListener(this);

        historyPresenter = new HistoryPresenterImpl(this, this);
        sqlDatabase = new SqlDatabase(this);
        historyModelList = sqlDatabase.getAllHistory();
        historyAdapter = new HistoryAdapter(this, historyModelList, sqlDatabase);
        LinearLayoutManager LayoutManagaer = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(LayoutManagaer);
        recyclerView.setAdapter(historyAdapter);
    }

    @Override
    public void dataHisrory(List<ListAPI> listAPIListAPI) {

    }

    @Override
    public void onClick(View view) {
        historyPresenter.backManchinh();
    }
}
