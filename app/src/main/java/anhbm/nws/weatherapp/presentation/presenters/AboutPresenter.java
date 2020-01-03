package anhbm.nws.weatherapp.presentation.presenters;

import anhbm.nws.weatherapp.presentation.presenters.base.BasePresenter;
import anhbm.nws.weatherapp.presentation.presenters.base.BaseView;
import anhbm.nws.weatherapp.presentation.ui.screen.about.mvp.AboutModel;

public interface AboutPresenter extends BasePresenter {
    interface AboutView extends BaseView {
        enum ViewState {
            IDLE, LOADING, SHOW_ABOUT, ERROR
        }

        void showState(ViewState state);

        AboutModel doRetrieveModel();
    }

    void presentState(AboutView.ViewState state);
}
