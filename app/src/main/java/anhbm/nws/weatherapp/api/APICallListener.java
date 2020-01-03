package anhbm.nws.weatherapp.api;


import java.util.List;

import anhbm.nws.weatherapp.utils.Enums;

public interface APICallListener {
    void onAPICallSucceed(Enums.APIRoute route, BaseResponse responseModel);

    void onAPICallSucceed(Enums.APIRoute route, List<BaseResponse> responseModels);

    void onAPICallFailed(Enums.APIRoute route, Throwable throwable);
}
