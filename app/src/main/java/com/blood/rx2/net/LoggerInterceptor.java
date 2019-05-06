package com.blood.rx2.net;

import com.blood.rx2.main.LLog;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class LoggerInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        long startTime = System.currentTimeMillis();
        Response response = chain.proceed(chain.request());
        long endTime = System.currentTimeMillis();
        long duration=endTime-startTime;
        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        LLog.d("\r\n");
        LLog.d("----------Start----------------");
        LLog.d("| "+request.toString());
        String method=request.method();
        if("POST".equals(method)){
            StringBuilder sb = new StringBuilder();
            if (request.body() instanceof FormBody) {
                FormBody body = (FormBody) request.body();
                for (int i = 0; i < body.size(); i++) {
                    sb.append(body.encodedName(i)).append("=").append(body.encodedValue(i)).append(",");
                }
                sb.delete(sb.length() - 1, sb.length());
                LLog.d("| RequestParams:{"+sb.toString()+"}");
            }
        }
        LLog.d("| Response:" + content);
        LLog.d("----------End:"+duration+"毫秒----------");
        return response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();
    }

}
