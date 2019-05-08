package com.blood.rx2.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.blood.rx2.R;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ShakeActivity extends AppCompatActivity {

    @BindView(R.id.btn)
    Button mBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        RxView.clicks(mBtn)
                .throttleFirst(2, TimeUnit.SECONDS)  // 才发送 2s内第1次点击按钮的事件
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object value) {
                        LLog.i("发送了网络请求");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LLog.i("对Error事件作出响应" + e.toString());
                        // 获取异常错误信息
                    }

                    @Override
                    public void onComplete() {
                        LLog.i("对Complete事件作出响应");
                    }
                });
    }

    /*
    05-08 18:33:08.339 4077-4077/com.blood.rx2 I/LLOG --->: 发送了网络请求
    05-08 18:33:10.370 4077-4077/com.blood.rx2 I/LLOG --->: 发送了网络请求
    05-08 18:33:12.379 4077-4077/com.blood.rx2 I/LLOG --->: 发送了网络请求
    05-08 18:33:14.403 4077-4077/com.blood.rx2 I/LLOG --->: 发送了网络请求
     */

}
