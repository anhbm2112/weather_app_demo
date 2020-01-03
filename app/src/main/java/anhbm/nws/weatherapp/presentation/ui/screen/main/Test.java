package anhbm.nws.weatherapp.presentation.ui.screen.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import anhbm.nws.weatherapp.R;
import anhbm.nws.weatherapp.domains.model.modeltest;
import anhbm.nws.weatherapp.presentation.ui.adapter.ListAdapter;

public class Test extends AppCompatActivity {

    private ListAdapter adapter;
    private RecyclerView recyler;
    private List<modeltest> movieList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        adapter = new ListAdapter(movieList, this);
        recyler = (RecyclerView)findViewById(R.id.list_number);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyler.setLayoutManager(mLayoutManager);
        recyler.setAdapter(adapter);

        initView();
    }

    private void initView(){


        for(int i = 0; i < 10; i++){
            modeltest movie = new modeltest(i);
            movieList.add(movie);

        }

        adapter.notifyDataSetChanged();


    }
}
