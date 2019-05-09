package com.blood.rx2.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.blood.rx2.R;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BackpressedActivity extends AppCompatActivity {

    @BindView(R.id.error)
    Button mError;
    @BindView(R.id.buffer)
    Button mBuffer;
    @BindView(R.id.drop)
    Button mDrop;
    @BindView(R.id.latest)
    Button mLatest;
    @BindView(R.id.drop_request)
    Button mDropRequest;

    private Flowable<Integer> mFlowable;
    private Subscriber<Integer> mSubscriber;
    private Subscription mSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backpressed);
        ButterKnife.bind(this);
        initDrop();
    }

    @OnClick({R.id.error, R.id.buffer, R.id.drop, R.id.drop_request, R.id.latest})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.error:
                error();
                break;
            case R.id.buffer:
                buffer();
                break;
            case R.id.drop:
                drop();
                break;
            case R.id.drop_request:
                dropRequest();
                break;
            case R.id.latest:
                lastest();
                break;
        }
    }

    // LATEST与DROP功能基本一致。
    // 唯一的区别就是LATEST总能使消费者能够接收到生产者产生的最后一个事件。
    private void lastest() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; i < 200; i++) {
                    emitter.onNext(i);
                }
            }
        }, BackpressureStrategy.LATEST).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(150);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        LLog.i("onNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        LLog.i("onError: " + t);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

        /*
        05-09 11:32:28.420 4902-4902/com.blood.rx2 I/LLOG --->: onNext: 0
        05-09 11:32:28.420 4902-4902/com.blood.rx2 I/LLOG --->: onNext: 1
        05-09 11:32:28.420 4902-4902/com.blood.rx2 I/LLOG --->: onNext: 2
        05-09 11:32:28.421 4902-4902/com.blood.rx2 I/LLOG --->: onNext: 3
        05-09 11:32:28.421 4902-4902/com.blood.rx2 I/LLOG --->: onNext: 4
        05-09 11:32:28.421 4902-4902/com.blood.rx2 I/LLOG --->: onNext: 5
        05-09 11:32:28.421 4902-4902/com.blood.rx2 I/LLOG --->: onNext: 6
        ......
        05-09 11:32:28.426 4902-4902/com.blood.rx2 I/LLOG --->: onNext: 122
        05-09 11:32:28.426 4902-4902/com.blood.rx2 I/LLOG --->: onNext: 123
        05-09 11:32:28.426 4902-4902/com.blood.rx2 I/LLOG --->: onNext: 124
        05-09 11:32:28.426 4902-4902/com.blood.rx2 I/LLOG --->: onNext: 125
        05-09 11:32:28.426 4902-4902/com.blood.rx2 I/LLOG --->: onNext: 126
        05-09 11:32:28.426 4902-4902/com.blood.rx2 I/LLOG --->: onNext: 127
        05-09 11:32:28.426 4902-4902/com.blood.rx2 I/LLOG --->: onNext: 199 // 这里，超出缓存池后，能拿到最后一个
         */
    }

    private void dropRequest() {
        mSubscription.request(20); // 可以接着上次的drop继续发送事件

        /*
        05-09 11:16:40.406 30408-30408/com.blood.rx2 I/LLOG --->: onNext: 50
        05-09 11:16:40.407 30408-30408/com.blood.rx2 I/LLOG --->: onNext: 51
        05-09 11:16:40.407 30408-30408/com.blood.rx2 I/LLOG --->: onNext: 52
        05-09 11:16:40.407 30408-30408/com.blood.rx2 I/LLOG --->: onNext: 53
        05-09 11:16:40.407 30408-30408/com.blood.rx2 I/LLOG --->: onNext: 54
        ......
        05-09 11:16:40.408 30408-30408/com.blood.rx2 I/LLOG --->: onNext: 67
        05-09 11:16:40.408 30408-30408/com.blood.rx2 I/LLOG --->: onNext: 68
        05-09 11:16:40.408 30408-30408/com.blood.rx2 I/LLOG --->: onNext: 69
         */

        /*
        05-09 11:34:32.400 4902-4902/com.blood.rx2 I/LLOG --->: onNext: 124
        05-09 11:34:32.400 4902-4902/com.blood.rx2 I/LLOG --->: onNext: 125
        05-09 11:34:32.400 4902-4902/com.blood.rx2 I/LLOG --->: onNext: 126
        05-09 11:34:32.400 4902-4902/com.blood.rx2 I/LLOG --->: onNext: 127
        05-09 11:34:32.400 4902-4902/com.blood.rx2 I/LLOG --->: onNext: 10318873
        05-09 11:34:32.400 4902-4902/com.blood.rx2 I/LLOG --->: onNext: 10318874
         */
    }

    private void initDrop() {
        mFlowable = Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; ; i++) {
                    emitter.onNext(i);
                }
            }
        }, BackpressureStrategy.DROP);
        mSubscriber = new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                mSubscription = s;
                s.request(50);
            }

            @Override
            public void onNext(Integer integer) {
                LLog.i("onNext: " + integer);
            }

            @Override
            public void onError(Throwable t) {
                LLog.i("onError: " + t);
            }

            @Override
            public void onComplete() {

            }
        };
    }

    // 消费者通过request()传入其需求n，然后生产者把n个事件传递给消费者供其消费。其他消费不掉的事件就丢掉
    private void drop() {
        mFlowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mSubscriber);
    }

    // 所谓BUFFER就是把RxJava中默认的只能存128个事件的缓存池换成一个大的缓存池，支持存很多很多的数据。
    private void buffer() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; i < 129; i++) {
                    emitter.onNext(i);
                }
            }
        }, BackpressureStrategy.BUFFER).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Integer.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        LLog.i("onNext " + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        LLog.i("onError " + t);
                    }

                    @Override
                    public void onComplete() {
                        LLog.i("onComplete");
                    }
                });
    }

    private void error() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                // 缓存池的默认大小为128，即只能缓存128个事件
                // 在ERROR策略下，如果缓存池溢出，就会立刻抛出MissingBackpressureException异常
                for (int i = 0; i < 129; i++) {
                    emitter.onNext(i);
                }
            }
        }, BackpressureStrategy.ERROR).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Integer.MAX_VALUE); // 这个方法就是用来向生产者申请可以消费的事件数量
                    }

                    @Override
                    public void onNext(Integer integer) {
                        LLog.i("onNext " + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        LLog.i("onError " + t);
                    }

                    @Override
                    public void onComplete() {
                        LLog.i("onComplete");
                    }
                });

        /*
        直接报错，不会走 onNext 方法了
        05-09 11:01:43.222 21975-21975/com.blood.rx2 I/LLOG --->: error onError io.reactivex.exceptions.MissingBackpressureException: create: could not emit value due to lack of requests
         */
    }

}
