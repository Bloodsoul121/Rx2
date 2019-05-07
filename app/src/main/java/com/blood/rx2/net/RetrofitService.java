package com.blood.rx2.net;

import com.blood.rx2.model.Translation;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface RetrofitService {

    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20world")
    Observable<Translation> getCall();

}
