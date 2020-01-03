package anhbm.nws.weatherapp.presentation.ui.screen.about;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import anhbm.nws.weatherapp.R;
import anhbm.nws.weatherapp.presentation.presenters.AboutPresenter;
import anhbm.nws.weatherapp.presentation.ui.screen.about.mvp.AboutModel;
import anhbm.nws.weatherapp.presentation.ui.screen.about.mvp.AboutPresenterImpl;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutActivity extends AppCompatActivity implements AboutPresenter.AboutView {
    private AboutPresenter mPresenter;
    private AboutModel mModel;

    @BindView(R.id.textview_about)
    TextView textView;
    @BindView(R.id.textview_about_version)
    TextView textViewVersion;
    @BindView(R.id.layout_about_github)
    LinearLayout lsyoutGithub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        initLayout();
        mPresenter.presentState(ViewState.SHOW_ABOUT);
    }

    private void initLayout() {
        ButterKnife.bind(AboutActivity.this);

        init();
    }

    private void init() {
        this.mPresenter = new AboutPresenterImpl(this);
        this.mModel = new AboutModel();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showState(ViewState state) {
        switch (state) {
            case IDLE:
                showProgress(false);
                break;
            case LOADING:
                showProgress(true);
                break;
            case SHOW_ABOUT:
                showAbout();
                break;
            case ERROR:
                showError(getString(R.string.error_message_title_oops), getString(R.string.error_message_api));
                break;
        }
    }

    @Override
    public AboutModel doRetrieveModel() {
        return this.mModel;
    }

    @Override
    public void showProgress(boolean flag) {

    }

    @Override
    public void showToast(String message) {
        Toast.makeText(AboutActivity.this, "" + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String title, String message) {
        new MaterialDialog.Builder(AboutActivity.this)
                .title(title)
                .content(message)
                .positiveText(R.string.error_dialog_positive)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .autoDismiss(false)
                .cancelable(false)
                .contentColor(getResources().getColor(R.color.dark))
                .backgroundColorRes(R.color.bone_white)
                .show();
    }

    private void showAbout() {
        PackageInfo pInfo;
        String version = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        textViewVersion.setText("Version: " + version);
    }

    @OnClick(R.id.layout_about_github)
    public void onClickGithub() {
        String url = "http://github.com/nandawperdana/android-mvp-architecture";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}
