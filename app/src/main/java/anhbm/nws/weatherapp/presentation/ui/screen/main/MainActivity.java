package anhbm.nws.weatherapp.presentation.ui.screen.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import anhbm.nws.weatherapp.R;
import anhbm.nws.weatherapp.domains.interactors.WeatherInteractor;
import anhbm.nws.weatherapp.presentation.presenters.MainPresenter;
import anhbm.nws.weatherapp.presentation.ui.screen.BaseActivity;
import anhbm.nws.weatherapp.presentation.ui.screen.about.AboutActivity;
import anhbm.nws.weatherapp.presentation.ui.screen.main.mvp.MainModel;
import anhbm.nws.weatherapp.presentation.ui.screen.main.mvp.MainPresenterImpl;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainPresenter.MainView {
    private MainPresenter presenter;
    private MainModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        presenter.presentState(ViewState.LOAD_WEATHER);
    }

    private void init() {
        ButterKnife.bind(this);
        presenter = new MainPresenterImpl(this);
        model = new MainModel(this);

        initLayout();
    }

    private void initLayout() {
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//        recyclerView.setAdapter(new PeopleAdapter(doRetrieveModel().getListPeople(), MainActivity.this));

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showProgress(boolean flag) {

    }

    @Override
    public void showState(MainPresenter.MainView.ViewState viewState) {
        switch (viewState) {
            case IDLE:
                showProgress(false);
                break;
            case LOADING:
                showProgress(true);
                break;
            case SHOW_WEATHER:
                showPeople();

                break;
            case OPEN_ABOUT:
                openActivityAbout();
                break;
            case ERROR:
                showToast(doRetrieveModel().getErrorMessage());
                break;
        }
    }

    @Override
    public MainModel doRetrieveModel() {
        return this.model;
    }

    /**
     * show WeatherResponse to UI
     */
    private void showPeople() {
        // show the data
        //recyclerView.getAdapter().notifyDataSetChanged();
        presenter.presentState(ViewState.IDLE);
        presenter.presentState(ViewState.IDLE);


    }

    private void openActivityAbout() {
        Intent intent = new Intent(MainActivity.this, AboutActivity.class);
        startActivity(intent);
    }


}
