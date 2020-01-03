package anhbm.nws.weatherapp.presentation.ui.screen.about.mvp;


import anhbm.nws.weatherapp.presentation.presenters.AboutPresenter;

public class AboutPresenterImpl implements AboutPresenter {
    AboutView mView;

    public AboutPresenterImpl(AboutView mView) {
        this.mView = mView;
    }

    @Override
    public void presentState(AboutView.ViewState state) {
        switch (state) {
            case IDLE:
                mView.showState(AboutView.ViewState.IDLE);
                break;
            case LOADING:
                mView.showState(AboutView.ViewState.LOADING);
                break;
            case SHOW_ABOUT:
                mView.showState(AboutView.ViewState.SHOW_ABOUT);
                break;
            case ERROR:
                mView.showState(AboutView.ViewState.ERROR);
                break;
        }
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onError(String message) {

    }
}
