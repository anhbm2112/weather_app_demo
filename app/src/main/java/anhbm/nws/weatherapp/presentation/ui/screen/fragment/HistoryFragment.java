package anhbm.nws.weatherapp.presentation.ui.screen.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import anhbm.nws.weatherapp.R;
import anhbm.nws.weatherapp.application.SQLiteWeather.SqlDatabase;
import anhbm.nws.weatherapp.presentation.presenters.onSetInterFace.ItemOnClick;
import anhbm.nws.weatherapp.presentation.ui.adapter.HistoryAdapter;
import anhbm.nws.weatherapp.presentation.ui.screen.BaseFragment;
import anhbm.nws.weatherapp.presentation.ui.screen.history.mvp.HistoryModel;

public class HistoryFragment extends BaseFragment implements View.OnClickListener {
    private SqlDatabase sqlDatabase;
    private List<HistoryModel> historyModelList;
    private HistoryAdapter historyAdapter;
    private RecyclerView recyclerView;
    private ImageView imageback;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerViewHistory);
        imageback = view.findViewById(R.id.back_history);
        imageback.setOnClickListener(this);
        sqlDatabase = new SqlDatabase(getActivity());
        historyModelList = sqlDatabase.getAllHistory();
        historyAdapter = new HistoryAdapter(getActivity(), historyModelList, sqlDatabase);
        LinearLayoutManager LayoutManagaer = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(LayoutManagaer);
        recyclerView.setAdapter(historyAdapter);

    }

    @Override
    public void onClick(View view) {
        HomeFragment homeFragment = new HomeFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frameLayout_main, homeFragment).commit();

    }
}
