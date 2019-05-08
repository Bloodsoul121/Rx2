package com.blood.rx2.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.blood.rx2.R;
import com.blood.rx2.model.Translation;
import com.blood.rx2.net.RetrofitManager;
import com.blood.rx2.net.RetrofitService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        // reverseLayout 是否倒序加载
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        MainAdapter adapter = new MainAdapter(this);
        adapter.setCallBack(new MainAdapter.CallBack() {
            @Override
            public void onClick(String str, int position) {
                onFunctionClick(str);
            }
        });
        mRecyclerview.setAdapter(adapter);
        mRecyclerview.setHasFixedSize(true);
        adapter.setData(getFunctionTitle());
    }

    private List<String> getFunctionTitle() {
        List<String> list = new ArrayList<>();
        list.add("网络请求轮询（无条件）");
        list.add("网络请求轮询（有条件）");
        list.add("网络请求嵌套回调");
        list.add("从磁盘/内存缓存/网络中获取缓存数据");
        list.add("合并数据源 & 同时展示 merge");
        list.add("合并数据源 & 同时展示 zip");
        list.add("联合判断多个事件（表单）");
        list.add("功能防抖");
        list.add("联想搜索优化");
        list.add("create");
        list.add("just");
        list.add("fromArray");
        list.add("fromIterable");
        list.add("defer");
        list.add("timer");
        list.add("interval");
        list.add("intervalRange");
        list.add("range");
        list.add("rangeLong");
        list.add("map");
        list.add("flatMap");
        list.add("concatMap");
        list.add("buffer");
        list.add("concat");
        list.add("merge");
        list.add("concatDelayError");
        list.add("zip");
        list.add("combineLatest");
        list.add("reduce");
        list.add("collect");
        list.add("startWith");
        list.add("count");
        list.add("subscribe");
        list.add("delay");
        list.add("onErrorReturn");
        list.add("onErrorResumeNext");
        list.add("retry");
        list.add("retryUntil");
        list.add("retryWhen");
        list.add("all");
        list.add("takeWhile");
        list.add("skipWhile");
        list.add("takeUntil");
        list.add("takeUntil 传入Observable对象");
        list.add("skipUntil 传入Observable对象");
        list.add("sequenceEqual");
        list.add("contains");
        list.add("isEmpty");
        list.add("amb");
        list.add("defaultIfEmpty");
        list.add("repeat");
        list.add("repeatWhen");
        list.add("filter");
        list.add("ofType");
        list.add("skip");
        list.add("distinct");
        list.add("take");
        list.add("throttleFirst");
        list.add("sample");
        list.add("firstElement");
        list.add("elementAt");
        list.add("elementAtOrError");
        return list;
    }

    private void onFunctionClick(String str) {
        switch (str) {
            case "网络请求轮询（无条件）":
                interval_request();
                break;
            case "网络请求轮询（有条件）":
                interval_request_condition();
                break;
            case "网络请求嵌套回调":
                request_double_callback();
                break;
            case "从磁盘/内存缓存/网络中获取缓存数据":
                request_from_three_level_buffer();
                break;
            case "合并数据源 & 同时展示 merge":
                combine_and_show();
                break;
            case "合并数据源 & 同时展示 zip":
                combine_and_show2();
                break;
            case "联合判断多个事件（表单）":
                startActivity(new Intent(this, FormActivity.class));
                break;
            case "功能防抖":
                shake_function();
                break;
            case "联想搜索优化":
                associative_word();
                break;
            case "create":
                create();
                break;
            case "just":
                just();
                break;
            case "fromArray":
                fromArray();
                break;
            case "fromIterable":
                fromIterable();
                break;
            case "defer":
                defer();
                break;
            case "timer":
                timer();
                break;
            case "interval":
                interval();
                break;
            case "intervalRange":
                intervalRange();
                break;
            case "range":
                range();
                break;
            case "rangeLong":
                rangeLong();
                break;
            case "map":
                map();
                break;
            case "flatMap":
                flatMap();
                break;
            case "concatMap":
                concatMap();
                break;
            case "buffer":
                buffer();
                break;
            case "concat":
                concat();
                break;
            case "merge":
                merge();
                break;
            case "concatDelayError":
                concatDelayError();
                break;
            case "zip":
                zip();
                break;
            case "combineLatest":
                combineLatest();
                break;
            case "reduce":
                reduce();
                break;
            case "collect":
                collect();
                break;
            case "startWith":
                startWith();
                break;
            case "count":
                count();
                break;
            case "subscribe":
                subscribe();
                break;
            case "delay":
                delay();
                break;
            case "onErrorReturn":
                onErrorReturn();
                break;
            case "onErrorResumeNext":
                onErrorResumeNext();
                break;
            case "retry":
                retry();
                break;
            case "retryUntil":
                retryUntil();
                break;
            case "retryWhen":
                retryWhen();
                break;
            case "all":
                all();
                break;
            case "takeWhile":
                takeWhile();
                break;
            case "skipWhile":
                skipWhile();
                break;
            case "takeUntil":
                takeUntil();
                break;
            case "takeUntil 传入Observable对象":
                takeUntil2();
                break;
            case "skipUntil 传入Observable对象":
                skipUntil2();
                break;
            case "sequenceEqual":
                sequenceEqual();
                break;
            case "contains":
                contains();
                break;
            case "isEmpty":
                isEmpty();
                break;
            case "amb":
                amb();
                break;
            case "defaultIfEmpty":
                defaultIfEmpty();
                break;
            case "repeat":
                repeat();
                break;
            case "repeatWhen":
                repeatWhen();
                break;
            case "filter":
                filter();
                break;
            case "ofType":
                ofType();
                break;
            case "skip":
                skip();
                break;
            case "distinct":
                distinct();
                break;
            case "take":
                take();
                break;
            case "throttleFirst":
                throttleFirst();
                break;
            case "sample":
                sample();
                break;
            case "firstElement":
                firstElement();
                break;
            case "elementAt":
                elementAt();
                break;
            case "elementAtOrError":
                elementAtOrError();
                break;
        }
    }

    private void associative_word() {
        startActivity(new Intent(this, AssociativeWordActivity.class));
    }

    private void shake_function() {
        startActivity(new Intent(this, ShakeActivity.class));
    }

    // 在elementAt（）的基础上，当出现越界情况（即获取的位置索引 ＞ 发送事件序列长度）时，即抛出异常
    private void elementAtOrError() {
        Observable.just(1, 2, 3, 4, 5)
                .elementAtOrError(6)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        LLog.i("获取到的事件元素是： " + integer);
                    }
                });

        /*
        05-08 18:20:41.559 1569-1569/com.blood.rx2 E/AndroidRuntime: FATAL EXCEPTION: main
        Process: com.blood.rx2, PID: 1569
        io.reactivex.exceptions.OnErrorNotImplementedException: The exception was not handled due to missing onError handler in the subscribe() method call.
         */
    }

    private void elementAt() {
        // 使用1：获取位置索引 = 2的 元素
        // 位置索引从0开始
        Observable.just(1, 2, 3, 4, 5)
                .elementAt(2)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        LLog.i("获取到的事件元素是： " + integer);
                    }
                });

        // 使用2：获取的位置索引 ＞ 发送事件序列长度时，设置默认参数
        Observable.just(1, 2, 3, 4, 5)
                .elementAt(6, 10)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        LLog.i("获取到的事件元素是： " + integer);
                    }
                });

        /*
        05-08 18:19:20.250 32463-32463/com.blood.rx2 I/LLOG --->: 获取到的事件元素是： 3
        05-08 18:19:20.253 32463-32463/com.blood.rx2 I/LLOG --->: 获取到的事件元素是： 10
         */
    }

    private void firstElement() {
        // 获取第1个元素
        Observable.just(1, 2, 3, 4, 5)
                .firstElement()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        LLog.i("获取到的第一个事件是： " + integer);
                    }
                });

        // 获取最后1个元素
        Observable.just(1, 2, 3, 4, 5)
                .lastElement()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        LLog.i("获取到的最后1个事件是： " + integer);
                    }
                });

        /*
        05-08 18:17:29.220 31244-31244/com.blood.rx2 I/LLOG --->: 获取到的第一个事件是： 1
        05-08 18:17:29.221 31244-31244/com.blood.rx2 I/LLOG --->: 获取到的最后1个事件是： 5
         */
    }

    // 在某段时间内，只发送该段时间内最新（最后）1次事件，与 throttleLast（） 操作符类似
    private void sample() {
        Observable.intervalRange(1, 10, 0, 1, TimeUnit.SECONDS)
                .sample(3, TimeUnit.SECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        LLog.i("sample " + aLong);
                    }
                });

        /*
        05-08 18:13:29.089 25882-30045/com.blood.rx2 I/LLOG --->: sample 3
        05-08 18:13:32.088 25882-30045/com.blood.rx2 I/LLOG --->: sample 6
        05-08 18:13:35.089 25882-30045/com.blood.rx2 I/LLOG --->: sample 9
         */
    }

    // 在某段时间内，只发送该段时间内第1次事件 / 最后1次事件（throttleLast）
    private void throttleFirst() {
        Observable.intervalRange(1, 10, 1, 1, TimeUnit.SECONDS)
                .throttleFirst(3, TimeUnit.SECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        LLog.i("throttleFirst " + aLong);
                    }
                });

        /*
        05-08 17:57:42.760 24378-24784/com.blood.rx2 I/LLOG --->: throttleFirst 1
        05-08 17:57:46.759 24378-24784/com.blood.rx2 I/LLOG --->: throttleFirst 5
        05-08 17:57:50.759 24378-24784/com.blood.rx2 I/LLOG --->: throttleFirst 9
         */
    }

    // 指定观察者最多能接收到的事件数量
    private void take() {
        Observable.intervalRange(1, 6, 1, 1, TimeUnit.SECONDS)
                .take(3)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        LLog.i("take " + aLong);
                    }
                });

        Observable.intervalRange(1, 6, 1, 1, TimeUnit.SECONDS)
                .takeLast(3)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        LLog.i("takeLast " + aLong);
                    }
                });

        /*
        05-08 17:50:38.696 20038-20590/com.blood.rx2 I/LLOG --->: take 1
        05-08 17:50:39.696 20038-20590/com.blood.rx2 I/LLOG --->: take 2
        05-08 17:50:40.695 20038-20590/com.blood.rx2 I/LLOG --->: take 3

        // 同时打印出来
        05-08 17:53:58.661 22106-23199/com.blood.rx2 I/LLOG --->: takeLast 4
        05-08 17:53:58.661 22106-23199/com.blood.rx2 I/LLOG --->: takeLast 5
        05-08 17:53:58.661 22106-23199/com.blood.rx2 I/LLOG --->: takeLast 6
         */
    }

    // 过滤事件序列中重复的事件 / 连续重复的事件
    private void distinct() {
        // 使用1：过滤事件序列中重复的事件
        Observable.just(1, 2, 3, 1, 2)
                .distinct()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        LLog.i("不重复的整型事件元素是： " + integer);
                    }
                });

        // 使用2：过滤事件序列中 连续重复的事件
        // 下面序列中，连续重复的事件 = 3、4
        Observable.just(1, 2, 3, 1, 2, 3, 3, 4, 4)
                .distinctUntilChanged()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        LLog.i("不连续重复的整型事件元素是： " + integer);
                    }
                });

        /*
        05-08 17:47:44.386 18393-18393/com.blood.rx2 I/LLOG --->: 不重复的整型事件元素是： 1
        05-08 17:47:44.386 18393-18393/com.blood.rx2 I/LLOG --->: 不重复的整型事件元素是： 2
        05-08 17:47:44.386 18393-18393/com.blood.rx2 I/LLOG --->: 不重复的整型事件元素是： 3

        05-08 17:47:44.387 18393-18393/com.blood.rx2 I/LLOG --->: 不连续重复的整型事件元素是： 1
        05-08 17:47:44.388 18393-18393/com.blood.rx2 I/LLOG --->: 不连续重复的整型事件元素是： 2
        05-08 17:47:44.388 18393-18393/com.blood.rx2 I/LLOG --->: 不连续重复的整型事件元素是： 3
        05-08 17:47:44.388 18393-18393/com.blood.rx2 I/LLOG --->: 不连续重复的整型事件元素是： 1
        05-08 17:47:44.388 18393-18393/com.blood.rx2 I/LLOG --->: 不连续重复的整型事件元素是： 2
        05-08 17:47:44.388 18393-18393/com.blood.rx2 I/LLOG --->: 不连续重复的整型事件元素是： 3
        05-08 17:47:44.388 18393-18393/com.blood.rx2 I/LLOG --->: 不连续重复的整型事件元素是： 4
         */
    }

    // 跳过某个事件，另外，skipLast 同理
    private void skip() {
        Observable.just(1, 2, 3, 4, 5, 6)
                .skip(2)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        LLog.i("skip " + integer);
                    }
                });

        /*
        05-08 17:45:11.104 16905-16905/com.blood.rx2 I/LLOG --->: skip 3
        05-08 17:45:11.104 16905-16905/com.blood.rx2 I/LLOG --->: skip 4
        05-08 17:45:11.104 16905-16905/com.blood.rx2 I/LLOG --->: skip 5
        05-08 17:45:11.104 16905-16905/com.blood.rx2 I/LLOG --->: skip 6
         */
    }

    // 过滤 特定数据类型的数据
    private void ofType() {
        Observable.just(1, "a", 3L, true)
                .ofType(Long.class)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        LLog.i("ofType " + aLong);
                    }
                });

        /*
        05-08 17:42:30.760 15193-15193/com.blood.rx2 I/LLOG --->: ofType 3
         */
    }

    private void filter() {
        Observable.just(1, 7, 9, 3, 4)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer > 3;
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        LLog.i("filter " + integer);
                    }
                });

        /*
        05-08 17:39:49.358 13541-13541/com.blood.rx2 I/LLOG --->: filter 7
        05-08 17:39:49.358 13541-13541/com.blood.rx2 I/LLOG --->: filter 9
        05-08 17:39:49.358 13541-13541/com.blood.rx2 I/LLOG --->: filter 4
         */
    }

    // 遇到错误时，将发生的错误传递给一个新的被观察者（Observable），并决定是否需要重新订阅原始被观察者（Observable）& 发送事件
    private void retryWhen() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onError(new Throwable("throwable"));
                emitter.onNext(3);
            }
        }).subscribeOn(Schedulers.newThread())
                // 遇到error事件才会回调
                .retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {
                        return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                            @Override
                            public ObservableSource<?> apply(Throwable throwable) throws Exception {
                                return Observable.error(new Throwable("终止 retry"));
                            }
                        });
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        LLog.i("retryWhen onNext " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LLog.i("retryWhen " + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        LLog.i("retryWhen onComplete");
                    }
                });

        /*
        05-08 17:24:57.578 8492-9094/com.blood.rx2 I/LLOG --->: retryWhen onNext 1
        05-08 17:24:57.578 8492-9094/com.blood.rx2 I/LLOG --->: retryWhen onNext 2
        05-08 17:24:57.582 8492-9094/com.blood.rx2 I/LLOG --->: retryWhen java.lang.Throwable: 终止 retry
         */
    }

    // 出现错误后，判断是否需要重新发送数据
    private void retryUntil() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onError(new Throwable("throwable"));
            }
        }).subscribeOn(Schedulers.newThread()).retryUntil(new BooleanSupplier() {
            @Override
            public boolean getAsBoolean() throws Exception {
                return false; // 返回 true 则不重新发送数据事件
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                LLog.i("retryUntil " + integer);
            }
        });
    }

    // 重试，即当出现错误时，让被观察者（Observable）重新发射数据
    // 接收到 onError（）时，重新订阅 & 发送事件，Throwable 和 Exception都可拦截
    private void retry() {
        /*
        <-- 1. retry（） -->
        // 作用：出现错误时，让被观察者重新发送数据
        // 注：若一直错误，则一直重新发送

        <-- 2. retry（long time） -->
        // 作用：出现错误时，让被观察者重新发送数据（具备重试次数限制
        // 参数 = 重试次数

        <-- 3. retry（Predicate predicate） -->
        // 作用：出现错误后，判断是否需要重新发送数据（若需要重新发送& 持续遇到错误，则持续重试）
        // 参数 = 判断逻辑

        <--  4. retry（new BiPredicate<Integer, Throwable>） -->
        // 作用：出现错误后，判断是否需要重新发送数据（若需要重新发送 & 持续遇到错误，则持续重试
        // 参数 =  判断逻辑（传入当前重试次数 & 异常错误信息）

        <-- 5. retry（long time,Predicate predicate） -->
        // 作用：出现错误后，判断是否需要重新发送数据（具备重试次数限制
        // 参数 = 设置重试次数 & 判断逻辑
         */

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onError(new Throwable("throwable"));
            }
        })
                .retry()
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        LLog.i("onNext " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LLog.i("onError " + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        LLog.i("onComplete");
                    }
                });

        /*
        05-08 16:42:08.431 16996-16996/? I/LLOG --->: onNext 1
        05-08 16:42:08.431 16996-16996/? I/LLOG --->: onNext 2
        05-08 16:42:08.431 16996-16996/? I/LLOG --->: onNext 1
        05-08 16:42:08.431 16996-16996/? I/LLOG --->: onNext 2
        05-08 16:42:08.431 16996-16996/? I/LLOG --->: onNext 1
        05-08 16:42:08.431 16996-16996/? I/LLOG --->: onNext 2
        ...... // 无限发送中
         */
    }

    /**
     * 遇到错误时，发送1个新的Observable
     * <p>
     * onErrorResumeNext（）拦截的错误 = Throwable；若需拦截Exception请用onExceptionResumeNext（）
     * <p>
     * 若onErrorResumeNext（）拦截的错误 = Exception，则会将错误传递给观察者的onError方法，也就是说不拦截！！！
     */
    private void onErrorResumeNext() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onError(new Throwable("发生错误了"));
            }
        }).onErrorResumeNext(new Function<Throwable, ObservableSource<? extends Integer>>() {
            @Override
            public ObservableSource<? extends Integer> apply(@NonNull Throwable throwable) throws Exception {

                // 1. 捕捉错误异常
                LLog.e("在onErrorReturn处理了错误: " + throwable.toString());

                // 2. 发生错误事件后，发送一个新的被观察者 & 发送事件序列
                return Observable.just(11, 22);

            }
        })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer value) {
                        LLog.i("接收到了事件" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LLog.e("对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        LLog.i("对Complete事件作出响应");
                    }
                });

        /*
        05-08 16:36:06.433 14125-14125/com.blood.rx2 I/LLOG --->: 接收到了事件1
        05-08 16:36:06.433 14125-14125/com.blood.rx2 I/LLOG --->: 接收到了事件2
        05-08 16:36:06.433 14125-14125/com.blood.rx2 E/LLOG --->: 在onErrorReturn处理了错误: java.lang.Throwable: 发生错误了
        05-08 16:36:06.435 14125-14125/com.blood.rx2 I/LLOG --->: 接收到了事件11
        05-08 16:36:06.435 14125-14125/com.blood.rx2 I/LLOG --->: 接收到了事件22
        05-08 16:36:06.435 14125-14125/com.blood.rx2 I/LLOG --->: 对Complete事件作出响应
         */
    }

    // 遇到错误时，发送1个特殊事件 & 正常终止
    private void onErrorReturn() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onError(new NullPointerException()); // 模拟异常
            }
        }).onErrorReturn(new Function<Throwable, Integer>() {
            @Override
            public Integer apply(Throwable throwable) throws Exception {
                return 666; // 捕获到异常，返回666后，事件结束
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                LLog.i("onErrorReturn " + integer);
            }
        });

        /*
        05-08 15:56:07.917 1964-1964/com.blood.rx2 I/LLOG --->: onErrorReturn 1
        05-08 15:56:07.917 1964-1964/com.blood.rx2 I/LLOG --->: onErrorReturn 666
         */
    }

    // 使得被观察者延迟一段时间再发送事件
    private void delay() {
        Observable.just(1)
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        LLog.i("delay doOnNext");
                    }
                })
                .delay(2, TimeUnit.SECONDS)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        LLog.i("delay accept " + integer);
                    }
                });

        /*
        间隔了2s
        05-08 15:50:09.111 31593-31593/com.blood.rx2 I/LLOG --->: delay doOnNext
        05-08 15:50:11.112 31593-32128/com.blood.rx2 I/LLOG --->: delay accept 1
         */
    }

    private void subscribe() {
        Observable.just(1)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void combine_and_show2() {
        RetrofitService service = RetrofitManager.getInstance().createService(RetrofitService.class);
        // 步骤3：采用Observable<...>形式 对 2个网络请求 进行封装
        Observable<Translation> observable1 = service.register().subscribeOn(Schedulers.io()); // 新开线程进行网络请求1
        Observable<Translation> observable2 = service.login().subscribeOn(Schedulers.io());// 新开线程进行网络请求2
        // 即2个网络请求异步 & 同时发送

        // 步骤4：通过使用Zip（）对两个网络请求进行合并再发送
        Observable.zip(observable1, observable2,
                new BiFunction<Translation, Translation, String>() {
                    // 注：创建BiFunction对象传入的第3个参数 = 合并后数据的数据类型
                    @Override
                    public String apply(Translation translation1,
                                        Translation translation2) throws Exception {
                        return translation1.show() + " & " + translation2.show();
                    }
                }).observeOn(AndroidSchedulers.mainThread()) // 在主线程接收 & 处理数据
                .subscribe(new Consumer<String>() {
                    // 成功返回数据时调用
                    @Override
                    public void accept(String combine_infro) throws Exception {
                        // 结合显示2个网络请求的数据结果
                        LLog.i("最终接收到的数据是：" + combine_infro);
                    }
                }, new Consumer<Throwable>() {
                    // 网络请求错误时调用
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        System.out.println("登录失败");
                    }
                });

        /*
        05-08 15:29:43.711 22940-22940/com.blood.rx2 I/LLOG --->: 最终接收到的数据是：hi registe & hi login
         */
    }

    // 用于存放最终展示的数据
    String result = "数据源来自 = ";

    private void combine_and_show() {
        /*
         * 设置第1个Observable：通过网络获取数据
         * 此处仅作网络请求的模拟
         **/
        Observable<String> network = Observable.just("网络");

        /*
         * 设置第2个Observable：通过本地文件获取数据
         * 此处仅作本地文件请求的模拟
         **/
        Observable<String> file = Observable.just("本地文件");

        /*
         * 通过merge（）合并事件 & 同时发送事件
         **/
        Observable.merge(network, file)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        LLog.i("combine_and_show onNext " + s);
                        result += s + "+";
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        LLog.i("combine_and_show onComplete " + result.substring(0, result.length() - 1));
                    }
                });

        /*
        05-08 15:23:46.994 20179-20179/com.blood.rx2 I/LLOG --->: combine_and_show onNext 网络
        05-08 15:23:46.994 20179-20179/com.blood.rx2 I/LLOG --->: combine_and_show onNext 本地文件
        05-08 15:23:46.995 20179-20179/com.blood.rx2 I/LLOG --->: combine_and_show onComplete 数据源来自 = 网络+本地文件
         */
    }

    // 该2变量用于模拟内存缓存 & 磁盘缓存中的数据
    String memoryCache = null;
    String diskCache = "从磁盘缓存中获取数据";

    private void request_from_three_level_buffer() {
        /*
         * 设置第1个Observable：检查内存缓存是否有该数据的缓存
         **/
        Observable<String> memory = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                // 先判断内存缓存有无数据
                if (memoryCache != null) {
                    // 若有该数据，则发送
                    emitter.onNext(memoryCache);
                } else {
                    // 若无该数据，则直接发送结束事件
                    emitter.onComplete();
                }
            }
        });

        /*
         * 设置第2个Observable：检查磁盘缓存是否有该数据的缓存
         **/
        Observable<String> disk = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                // 先判断磁盘缓存有无数据
                if (diskCache != null) {
                    // 若有该数据，则发送
                    emitter.onNext(diskCache);
                } else {
                    // 若无该数据，则直接发送结束事件
                    emitter.onComplete();
                }
            }
        });

        /*
         * 设置第3个Observable：通过网络获取数据
         **/
        Observable<String> network = Observable.just("从网络中获取数据");
        // 此处仅作网络请求的模拟

        /*
         * 通过concat（） 和 firstElement（）操作符实现缓存功能
         **/

        // 1. 通过concat（）合并memory、disk、network 3个被观察者的事件（即检查内存缓存、磁盘缓存 & 发送网络请求）
        //    并将它们按顺序串联成队列
        Observable.concat(memory, disk, network)
                // 2. 通过firstElement()，从串联队列中取出并发送第1个有效事件（Next事件），即依次判断检查memory、disk、network
                .firstElement()
                // 即本例的逻辑为：
                // a. firstElement()取出第1个事件 = memory，即先判断内存缓存中有无数据缓存；由于memoryCache = null，即内存缓存中无数据，所以发送结束事件（视为无效事件）
                // b. firstElement()继续取出第2个事件 = disk，即判断磁盘缓存中有无数据缓存：由于diskCache ≠ null，即磁盘缓存中有数据，所以发送Next事件（有效事件）
                // c. 即firstElement()已发出第1个有效事件（disk事件），所以停止判断。

                // 3. 观察者订阅
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        LLog.i("request_from_three_level_buffer " + s);
                    }
                });

        /*
        05-08 14:48:57.965 8734-8734/com.blood.rx2 I/LLOG --->: request_from_three_level_buffer 从磁盘缓存中获取数据
         */
    }

    // 统计被观察者发送事件的数量
    private void count() {
        Observable.just(1, 2, 3)
                .count()
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        LLog.i("count " + aLong);
                    }
                });

        /*
        05-08 14:42:05.006 5581-5581/com.blood.rx2 I/LLOG --->: count 3
         */
    }

    // 在一个被观察者发送事件前，追加发送一些数据 / 一个新的被观察者
    private void startWith() {
        Observable.just(1, 2, 3)
                .startWith(4)
                .startWithArray(5, 6)
                .startWith(Observable.just(7, 8))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        LLog.i("startWith " + integer);
                    }
                });

        /*
        05-08 14:39:47.335 4336-4336/com.blood.rx2 I/LLOG --->: startWith 7
        05-08 14:39:47.335 4336-4336/com.blood.rx2 I/LLOG --->: startWith 8
        05-08 14:39:47.335 4336-4336/com.blood.rx2 I/LLOG --->: startWith 5
        05-08 14:39:47.335 4336-4336/com.blood.rx2 I/LLOG --->: startWith 6
        05-08 14:39:47.336 4336-4336/com.blood.rx2 I/LLOG --->: startWith 4
        05-08 14:39:47.336 4336-4336/com.blood.rx2 I/LLOG --->: startWith 1
        05-08 14:39:47.336 4336-4336/com.blood.rx2 I/LLOG --->: startWith 2
        05-08 14:39:47.336 4336-4336/com.blood.rx2 I/LLOG --->: startWith 3
         */
    }

    // 将被观察者Observable发送的数据事件收集到一个数据结构里
    private void collect() {
        Observable.just(1, 2, 3)
                .collect(new Callable<List<Integer>>() {
                    @Override
                    public List<Integer> call() throws Exception {
                        return new ArrayList<>(); // 1. 创建数据结构（容器），用于收集被观察者发送的数据
                    }
                }, new BiConsumer<List<Integer>, Integer>() {
                    @Override
                    public void accept(List<Integer> list, Integer integer) throws Exception {
                        // 参数说明：list = 容器，integer = 后者数据
                        list.add(integer);
                        // 对发送的数据进行收集
                    }
                })
                .subscribe(new Consumer<List<Integer>>() {
                    @Override
                    public void accept(List<Integer> integers) throws Exception {
                        LLog.i("collect " + integers.toString());
                    }
                });

        /*
        05-08 14:36:53.265 2819-2819/com.blood.rx2 I/LLOG --->: collect [1, 2, 3]
         */
    }

    // 把被观察者需要发送的事件聚合成1个事件 & 发送
    private void reduce() {
        Observable.just(1, 2, 3, 4)
                .reduce(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
                        return integer + integer2;
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        LLog.i("reduce " + integer);
                    }
                });

        /*
        05-08 14:31:22.029 32474-32474/com.blood.rx2 I/LLOG --->: reduce 10
         */
    }

    /**
     * 当两个Observables中的任何一个发送了数据后，将先发送了数据的Observables 的最新（最后）一个数据
     * 与 另外一个Observable发送的每个数据结合，最终基于该函数的结果发送数据
     * <p>
     * 与Zip（）的区别：Zip（） = 按个数合并，即1对1合并；CombineLatest（） = 按时间合并，即在同一个时间点上合并
     */
    private void combineLatest() {
        Observable.combineLatest(Observable.just(1, 2, 3), Observable.intervalRange(4, 4, 1, 1, TimeUnit.SECONDS), new BiFunction<Integer, Long, String>() {
            @Override
            public String apply(Integer integer, Long aLong) throws Exception {
                return integer + " -> " + aLong;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                LLog.i("combineLatest " + s);
            }
        });

        /*
        05-08 14:26:41.032 30606-31018/com.blood.rx2 I/LLOG --->: combineLatest 3 -> 4
        05-08 14:26:42.031 30606-31018/com.blood.rx2 I/LLOG --->: combineLatest 3 -> 5
        05-08 14:26:43.031 30606-31018/com.blood.rx2 I/LLOG --->: combineLatest 3 -> 6
        05-08 14:26:44.031 30606-31018/com.blood.rx2 I/LLOG --->: combineLatest 3 -> 7
         */
    }

    // 合并 多个被观察者（Observable）发送的事件，生成一个新的事件序列（即组合过后的事件序列），并最终发送
    // 最终合并的事件数量 = 多个被观察者（Observable）中数量最少的数量
    private void zip() {
        Observable.zip(Observable.just(1, 2, 3), Observable.just(4, 5, 6), new BiFunction<Integer, Integer, String>() {
            @Override
            public String apply(Integer integer, Integer integer2) throws Exception {
                return integer + " " + integer2;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                LLog.i("zip " + s);
            }
        });

        /*
        05-08 14:18:27.739 26593-26593/com.blood.rx2 I/LLOG --->: zip 1 4
        05-08 14:18:27.739 26593-26593/com.blood.rx2 I/LLOG --->: zip 2 5
        05-08 14:18:27.739 26593-26593/com.blood.rx2 I/LLOG --->: zip 3 6
         */
    }

    // 将 onError 事件推迟到其他观察者发送事件结束之后才触发
    private void concatDelayError() {
        Observable.concatArrayDelayError(Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(0);
                emitter.onError(new NullPointerException());
            }
        }), Observable.just(1, 2, 3))
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        LLog.i("concatDelayError " + o);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LLog.i("concatDelayError " + throwable);
                    }
                });

        /*
        05-08 14:11:38.627 23581-23581/com.blood.rx2 I/LLOG --->: concatDelayError 0
        05-08 14:11:38.628 23581-23581/com.blood.rx2 I/LLOG --->: concatDelayError 1
        05-08 14:11:38.628 23581-23581/com.blood.rx2 I/LLOG --->: concatDelayError 2
        05-08 14:11:38.628 23581-23581/com.blood.rx2 I/LLOG --->: concatDelayError 3
        05-08 14:11:38.628 23581-23581/com.blood.rx2 I/LLOG --->: concatDelayError java.lang.NullPointerException
         */
    }

    // 组合多个被观察者一起发送数据，合并后 按时间线并行执行
    // 区别：组合被观察者的数量，即merge（）组合被观察者数量≤4个，而mergeArray（）则可＞4个
    private void merge() {
        Observable.merge(Observable.intervalRange(0, 3, 1, 1, TimeUnit.SECONDS),
                Observable.intervalRange(6, 3, 1, 1, TimeUnit.SECONDS))
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long integer) throws Exception {
                        LLog.i("merge " + integer);
                    }
                });

//        Observable.mergeArray(); // 同理

        /*
        05-08 13:59:09.674 19385-20170/com.blood.rx2 I/LLOG --->: merge 0
        05-08 13:59:09.675 19385-20172/com.blood.rx2 I/LLOG --->: merge 6
        05-08 13:59:10.673 19385-20170/com.blood.rx2 I/LLOG --->: merge 1
        05-08 13:59:10.674 19385-20172/com.blood.rx2 I/LLOG --->: merge 7
        05-08 13:59:11.673 19385-20170/com.blood.rx2 I/LLOG --->: merge 2
        05-08 13:59:11.674 19385-20172/com.blood.rx2 I/LLOG --->: merge 8
         */
    }

    // 组合多个被观察者一起发送数据，合并后 按发送顺序串行执行
    // 区别：组合被观察者的数量，即concat（）组合被观察者数量≤4个，而concatArray（）则可＞4个
    private void concat() {
        Observable.concat(Observable.just(1, 2, 3), Observable.just(4, 5), Observable.just(6, 7))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        LLog.i("concat " + integer);
                    }
                });

//        Observable.concatArray(); // 同理

        /*
        05-08 13:52:07.602 15396-15396/com.blood.rx2 I/LLOG --->: concat 1
        05-08 13:52:07.602 15396-15396/com.blood.rx2 I/LLOG --->: concat 2
        05-08 13:52:07.602 15396-15396/com.blood.rx2 I/LLOG --->: concat 3
        05-08 13:52:07.603 15396-15396/com.blood.rx2 I/LLOG --->: concat 4
        05-08 13:52:07.603 15396-15396/com.blood.rx2 I/LLOG --->: concat 5
        05-08 13:52:07.603 15396-15396/com.blood.rx2 I/LLOG --->: concat 6
        05-08 13:52:07.603 15396-15396/com.blood.rx2 I/LLOG --->: concat 7
         */
    }

    private void request_double_callback() {
        RetrofitService service = RetrofitManager.getInstance().createService(RetrofitService.class);
        Observable<Translation> register = service.register();
        final Observable<Translation> login = service.login();
        register.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Function<Translation, ObservableSource<Translation>>() {
                    @Override
                    public ObservableSource<Translation> apply(Translation translation) throws Exception {
                        // 这里可以对第一个网络请求的结果进行处理
                        return login;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Translation>() {
                    @Override
                    public void accept(Translation translation) throws Exception {
                        LLog.i("登录成功");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LLog.i("登录失败");
                    }
                });
    }

    // 定期从 被观察者（Obervable）需要发送的事件中 获取一定数量的事件 & 放到缓存区中，最终发送
    // 也就是缓存区大小固定，然后定义每次新放入到缓存区的个数
    private void buffer() {
        Observable.just(1, 2, 3, 4, 5)
                // 设置缓存区大小 & 步长
                // 缓存区大小 = 每次从被观察者中获取的事件数量
                // 步长 = 每次获取新事件的数量
                .buffer(3, 1)
                .subscribe(new Consumer<List<Integer>>() {
                    @Override
                    public void accept(List<Integer> integers) throws Exception {
                        LLog.i("buffer " + integers.toString());
                    }
                });

        /*
        05-08 11:39:11.984 9957-9957/com.blood.rx2 I/LLOG --->: buffer [1, 2, 3]
        05-08 11:39:11.984 9957-9957/com.blood.rx2 I/LLOG --->: buffer [2, 3, 4]
        05-08 11:39:11.984 9957-9957/com.blood.rx2 I/LLOG --->: buffer [3, 4, 5]
        05-08 11:39:11.984 9957-9957/com.blood.rx2 I/LLOG --->: buffer [4, 5]
        05-08 11:39:11.984 9957-9957/com.blood.rx2 I/LLOG --->: buffer [5]
         */
    }

    // 类似 flatMap（）操作符（区别：有序的）
    private void concatMap() {
        Observable.just(1, 2, 3)
                .flatMap(new Function<Integer, ObservableSource<Integer>>() {
                    @Override
                    public ObservableSource<Integer> apply(Integer integer) throws Exception {
                        return Observable.just(integer * 2, integer * 3);
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        LLog.i("concatMap " + integer);
                    }
                });
    }

    // 将被观察者发送的事件序列进行 拆分 & 单独转换，再合并成一个新的事件序列，最后再进行发送（无序的，重点！）
    private void flatMap() {
        Observable.just(1, 2, 3)
                .flatMap(new Function<Integer, ObservableSource<Integer>>() {
                    @Override
                    public ObservableSource<Integer> apply(Integer integer) throws Exception {
                        return Observable.just(integer * 2, integer * 3);
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        LLog.i("flatMap " + integer);
                    }
                });

        /*
        事件可能是打乱顺序的，例如 646329
        05-08 11:29:32.429 8112-8112/com.blood.rx2 I/LLOG --->: flatMap 2
        05-08 11:29:32.429 8112-8112/com.blood.rx2 I/LLOG --->: flatMap 3
        05-08 11:29:32.429 8112-8112/com.blood.rx2 I/LLOG --->: flatMap 4
        05-08 11:29:32.429 8112-8112/com.blood.rx2 I/LLOG --->: flatMap 6
        05-08 11:29:32.429 8112-8112/com.blood.rx2 I/LLOG --->: flatMap 6
        05-08 11:29:32.430 8112-8112/com.blood.rx2 I/LLOG --->: flatMap 9
         */
    }

    // 对 被观察者发送的每1个事件都通过 指定的函数 处理，从而变换成另外一种事件
    private void map() {
        Observable.just(1, 2, 3)
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return "integer : " + integer;
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        LLog.i("map " + s);
                    }
                });
    }

    // 设置变量 = 模拟轮询服务器次数
    private int request_count = 0;

    private void interval_request_condition() {
        RetrofitService service = RetrofitManager.getInstance().createService(RetrofitService.class);
        service.getCall()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Observable<Object> objectObservable) throws Exception {
                        return objectObservable.flatMap(new Function<Object, ObservableSource<?>>() {
                            @Override
                            public ObservableSource<?> apply(Object o) throws Exception {
                                if (request_count > 3) {
                                    return Observable.empty();
                                }
                                return Observable.just(0).delay(2, TimeUnit.SECONDS);
                            }
                        });
                    }
                })
                .subscribe(new Consumer<Translation>() {
                    @Override
                    public void accept(Translation translation) throws Exception {
                        translation.show();
                        request_count++;
                    }
                });

        /*
        05-07 10:04:36.311 20196-20196/com.blood.rx2 D/RxJava: 嗨世界
        05-07 10:04:38.449 20196-20196/com.blood.rx2 D/RxJava: 嗨世界
        05-07 10:04:40.594 20196-20196/com.blood.rx2 D/RxJava: 嗨世界
        05-07 10:04:42.717 20196-20196/com.blood.rx2 D/RxJava: 嗨世界
         */
    }

    // 有条件地、重复发送 被观察者事件
    // 若新被观察者（Observable）返回1个Complete / Error事件，则不重新订阅 & 发送原来的 Observable
    // 若新被观察者（Observable）返回其余事件时，则重新订阅 & 发送原来的 Observable
    private void repeatWhen() {
        Observable.just(1, 2, 3)
                .subscribeOn(Schedulers.newThread())
                .repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Observable<Object> objectObservable) throws Exception {
                        return objectObservable.flatMap(new Function<Object, ObservableSource<?>>() {
                            @Override
                            public ObservableSource<?> apply(Object o) throws Exception {
                                LLog.i("repeatWhen flatMap " + o);
                                return Observable.empty();
//                                return Observable.just(4);
                            }
                        });
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        LLog.i("repeatWhen " + integer);
                    }
                });

        /*
        05-07 09:51:16.644 18710-18710/com.blood.rx2 I/LLOG --->: repeatWhen 1
        05-07 09:51:16.644 18710-18710/com.blood.rx2 I/LLOG --->: repeatWhen 2
        05-07 09:51:16.644 18710-18710/com.blood.rx2 I/LLOG --->: repeatWhen 3
        05-07 09:51:16.644 18710-18710/com.blood.rx2 I/LLOG --->: repeatWhen flatMap 0
         */
    }

    // 无条件地、重复发送 被观察者事件
    private void repeat() {
        Observable.just(1, 2, 3)
                .repeat(3)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        LLog.i("repeat " + integer);
                    }
                });

        /*
        05-07 09:38:05.142 12146-12146/com.blood.rx2 I/LLOG --->: repeat 1
        05-07 09:38:05.143 12146-12146/com.blood.rx2 I/LLOG --->: repeat 2
        05-07 09:38:05.143 12146-12146/com.blood.rx2 I/LLOG --->: repeat 3
        05-07 09:38:05.143 12146-12146/com.blood.rx2 I/LLOG --->: repeat 1
        05-07 09:38:05.143 12146-12146/com.blood.rx2 I/LLOG --->: repeat 2
        05-07 09:38:05.143 12146-12146/com.blood.rx2 I/LLOG --->: repeat 3
        05-07 09:38:05.143 12146-12146/com.blood.rx2 I/LLOG --->: repeat 1
        05-07 09:38:05.143 12146-12146/com.blood.rx2 I/LLOG --->: repeat 2
        05-07 09:38:05.143 12146-12146/com.blood.rx2 I/LLOG --->: repeat 3
         */
    }

    private void interval_request() {
        Observable.interval(1, 2, TimeUnit.SECONDS)
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        RetrofitService retrofitService = RetrofitManager.getInstance().createService(RetrofitService.class);
                        retrofitService.getCall()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<Translation>() {
                                    @Override
                                    public void accept(Translation translation) throws Exception {
                                        translation.show();
                                    }
                                });
                    }
                })
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {

                    }
                });
    }

    // 类似于range（），区别在于该方法支持数据类型 = Long
    private void rangeLong() {
        Observable.rangeLong(2L, 5L)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        LLog.i("rangeLong " + aLong);
                    }
                });
    }

    private void range() {
        // 参数说明：
        // 参数1 = 事件序列起始点；
        // 参数2 = 事件数量；
        // 注：若设置为负数，则会抛出异常
        Observable.range(3, 10)
                // 该例子发送的事件序列特点：从3开始发送，每次发送事件递增1，一共发送10个事件
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        LLog.i("开始采用subscribe连接");
                    }
                    // 默认最先调用复写的 onSubscribe（）

                    @Override
                    public void onNext(Integer value) {
                        LLog.i("接收到了事件" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LLog.i("对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        LLog.i("对Complete事件作出响应");
                    }

                });
    }

    // 每隔指定时间 就发送 事件，可指定发送的数据的数量
    private void intervalRange() {
        // 参数说明：
        // 参数1 = 事件序列起始点；
        // 参数2 = 事件数量；
        // 参数3 = 第1次事件延迟发送时间；
        // 参数4 = 间隔时间数字；
        // 参数5 = 时间单位
        Observable.intervalRange(3, 10, 2, 1, TimeUnit.SECONDS)
                // 该例子发送的事件序列特点：
                // 1. 从3开始，一共发送10个事件；
                // 2. 第1次延迟2s发送，之后每隔2秒产生1个数字（从0开始递增1，无限个）
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        LLog.i("开始采用subscribe连接");
                    }
                    // 默认最先调用复写的 onSubscribe（）

                    @Override
                    public void onNext(Long value) {
                        LLog.i("接收到了事件" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LLog.i("对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        LLog.i("对Complete事件作出响应");
                    }

                });
    }

    // 每隔指定时间 就发送 事件
    private void interval() {
        // 注：interval默认在computation调度器上执行
        // 也可自定义指定线程调度器（第3个参数）：interval(long,TimeUnit,Scheduler)

        // 参数说明：
        // 参数1 = 第1次延迟时间；
        // 参数2 = 间隔时间数字；
        // 参数3 = 时间单位；
        Observable.interval(3, 1, TimeUnit.SECONDS)
                // 该例子发送的事件序列特点：延迟3s后发送事件，每隔1秒产生1个数字（从0开始递增1，无限个）
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        LLog.i("开始采用subscribe连接");
                    }
                    // 默认最先调用复写的 onSubscribe（）

                    @Override
                    public void onNext(Long value) {
                        LLog.i("接收到了事件" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LLog.i("对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        LLog.i("对Complete事件作出响应");
                    }

                });
    }

    // 延迟指定时间后，发送1个数值0（Long类型）
    private void timer() {
        Observable.timer(2, TimeUnit.SECONDS)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        LLog.i("timer ");
                    }
                })
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        LLog.i("timer " + aLong);
                    }
                });

        /*
        相隔了2秒
        05-06 20:03:12.312 3529-3529/com.blood.rx2 I/LLOG --->: timer
        05-06 20:03:14.314 3529-4310/com.blood.rx2 I/LLOG --->: timer 0
         */
    }

    private Integer i = 0;

    // 直到有观察者（Observer ）订阅时，才动态创建被观察者对象（Observable） & 发送事件
    private void defer() {
        final Observable<Integer> observable = Observable.defer(new Callable<ObservableSource<Integer>>() {
            @Override
            public ObservableSource<Integer> call() throws Exception {
                return Observable.just(i);
            }
        });

        i = 1;
        observable.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer o) throws Exception {
                LLog.i("defer " + o);
            }
        });

        /*
        05-06 20:00:18.860 1603-1603/com.blood.rx2 I/LLOG --->: defer 1
         */
    }

    private void fromIterable() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        Observable.fromIterable(list).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                LLog.i("fromIterable " + integer);
            }
        });

        /*
        05-06 19:46:42.086 29184-29184/com.blood.rx2 I/LLOG --->: fromIterable 1
        05-06 19:46:42.086 29184-29184/com.blood.rx2 I/LLOG --->: fromIterable 2
        05-06 19:46:42.086 29184-29184/com.blood.rx2 I/LLOG --->: fromIterable 3
         */
    }

    private void fromArray() {
        int[] arr = {1, 2, 3, 4};
        Observable.fromArray(arr).subscribe(new Consumer<int[]>() {
            @Override
            public void accept(int[] ints) throws Exception {
                LLog.i("fromArray " + Arrays.toString(ints));
            }
        });

        /*
        05-06 19:44:16.558 27844-27844/com.blood.rx2 I/LLOG --->: fromArray [1, 2, 3, 4]
         */
    }

    private void just() {
        Observable<? extends Serializable> observable = Observable.just(1, 2, "a");
        observable.subscribe();
    }

    private void create() {
        Observable<Object> observable = Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {

            }
        });
        observable.subscribe();
    }

    // 在不发送任何有效事件（ Next事件）、仅发送了 Complete 事件的前提下，发送一个默认值
    private void defaultIfEmpty() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                // 不发送任何有效事件
                //  e.onNext(1);
                //  e.onNext(2);

                // 仅发送Complete事件
                e.onComplete();
            }
        }).defaultIfEmpty(10) // 若仅发送了Complete事件，默认发送 值 = 10
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        LLog.i("开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Integer value) {
                        LLog.i("接收到了事件" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LLog.i("对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        LLog.i("对Complete事件作出响应");
                    }
                });

        /*
        05-06 19:20:10.106 19238-19238/com.blood.rx2 I/LLOG --->: 开始采用subscribe连接
        05-06 19:20:10.108 19238-19238/com.blood.rx2 I/LLOG --->: 接收到了事件10
        05-06 19:20:10.108 19238-19238/com.blood.rx2 I/LLOG --->: 对Complete事件作出响应
         */
    }

    // 当需要发送多个 Observable时，只发送 先发送数据的Observable的数据，而其余 Observable则被丢弃。
    private void amb() {
        // 设置2个需要发送的Observable & 放入到集合中
        List<ObservableSource<Integer>> list = new ArrayList<>();
        // 第1个Observable延迟1秒发射数据
        list.add(Observable.just(1, 2, 3).delay(1, TimeUnit.SECONDS));
        // 第2个Observable正常发送数据
        list.add(Observable.just(4, 5, 6));

        // 一共需要发送2个Observable的数据
        // 但由于使用了amba（）,所以仅发送先发送数据的Observable
        // 即第二个（因为第1个延时了）
        Observable.amb(list).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                LLog.i("amb " + integer);
            }
        });

        /*
        05-06 19:16:32.582 17209-17209/com.blood.rx2 I/LLOG --->: amb 4
        05-06 19:16:32.583 17209-17209/com.blood.rx2 I/LLOG --->: amb 5
        05-06 19:16:32.583 17209-17209/com.blood.rx2 I/LLOG --->: amb 6
         */
    }

    private void isEmpty() {
        Observable.just(1, 2, 3, 4, 5, 6)
                .isEmpty()
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        LLog.i("isEmpty " + aBoolean);
                    }
                });

        /*
        05-06 18:12:17.719 30436-30436/com.blood.rx2 I/LLOG --->: isEmpty false
         */
    }

    // 判断发送的数据中是否包含指定数据, 若包含，返回 true；否则，返回 false
    private void contains() {
        Observable.just(1, 2, 3, 4, 5, 6)
                .contains(4)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        LLog.i("contains " + aBoolean);
                    }
                });

        /*
        05-06 18:10:23.248 29224-29224/com.blood.rx2 I/LLOG --->: contains true
         */
    }

    // 判定两个Observables需要发送的数据是否相同
    private void sequenceEqual() {
        Observable.sequenceEqual(Observable.just(1, 2, 3), Observable.just(1, 2, 3))
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        LLog.i("sequenceEqual " + aBoolean);
                    }
                });

        /*
        05-06 18:06:08.374 27378-27378/com.blood.rx2 I/LLOG --->: sequenceEqual true
         */
    }

    // 等到 skipUntil（） 传入的Observable开始发送数据，（原始）第1个Observable的数据才开始发送数据
    private void skipUntil2() {
        Observable.interval(1, TimeUnit.SECONDS)
                // 第2个Observable：延迟5s后开始发送1个Long型数据
                .skipUntil(Observable.timer(5, TimeUnit.SECONDS))
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        LLog.i("skipUntil2 " + aLong);
                    }
                });

        /*
        05-06 18:03:17.920 25859-26343/com.blood.rx2 I/LLOG --->: skipUntil2 4
        05-06 18:03:18.919 25859-26343/com.blood.rx2 I/LLOG --->: skipUntil2 5
        05-06 18:03:19.922 25859-26343/com.blood.rx2 I/LLOG --->: skipUntil2 6
        05-06 18:03:20.920 25859-26343/com.blood.rx2 I/LLOG --->: skipUntil2 7
        05-06 18:03:21.919 25859-26343/com.blood.rx2 I/LLOG --->: skipUntil2 8
        ......
         */
    }

    // 即 等到 takeUntil（） 传入的Observable开始发送数据，（原始）第1个Observable的数据停止发送数据
    private void takeUntil2() {
        Observable.interval(1, TimeUnit.SECONDS)
                .takeUntil(Observable.timer(3, TimeUnit.SECONDS))
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        LLog.i("takeUntil2 " + aLong);
                    }
                });

        /*
        05-06 17:58:12.396 23754-23919/com.blood.rx2 I/LLOG --->: takeUntil2 0
        05-06 17:58:13.395 23754-23919/com.blood.rx2 I/LLOG --->: takeUntil2 1
         */
    }

    // 满足条件时，停止发送数据（仍然发送最后不满足的那一个）
    private void takeUntil() {
        Observable.interval(1, TimeUnit.SECONDS)
                .takeUntil(new Predicate<Long>() {
                    @Override
                    public boolean test(Long aLong) throws Exception {
                        return aLong > 4;
                    }
                })
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        LLog.i("takeUntil " + aLong);
                    }
                });

        /*
        05-06 17:51:52.098 21171-21762/com.blood.rx2 I/LLOG --->: takeUntil 0
        05-06 17:51:53.097 21171-21762/com.blood.rx2 I/LLOG --->: takeUntil 1
        05-06 17:51:54.097 21171-21762/com.blood.rx2 I/LLOG --->: takeUntil 2
        05-06 17:51:55.097 21171-21762/com.blood.rx2 I/LLOG --->: takeUntil 3
        05-06 17:51:56.098 21171-21762/com.blood.rx2 I/LLOG --->: takeUntil 4
        05-06 17:51:57.097 21171-21762/com.blood.rx2 I/LLOG --->: takeUntil 5   这里5是不满足的，但是仍然发送了
         */
    }

    // 当判断条件为 false 时才开始发送数据（并不是判断true就不发送false就发送，注意了！）
    private void skipWhile() {
        Observable.interval(1, TimeUnit.SECONDS)
                .skipWhile(new Predicate<Long>() {
                    @Override
                    public boolean test(Long aLong) throws Exception {
                        return (aLong < 4 || aLong > 8); // 这里 aLong > 8 并没什么用
//                        return aLong > 4; // 这里从0开始一直发送数据
                    }
                })
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        LLog.i("skipWhile " + aLong);
                    }
                });

        /*
        05-06 17:44:27.901 18098-18196/com.blood.rx2 I/LLOG --->: skipWhile 4
        05-06 17:44:28.901 18098-18196/com.blood.rx2 I/LLOG --->: skipWhile 5
        05-06 17:44:29.901 18098-18196/com.blood.rx2 I/LLOG --->: skipWhile 6
        05-06 17:44:30.901 18098-18196/com.blood.rx2 I/LLOG --->: skipWhile 7
        05-06 17:44:31.901 18098-18196/com.blood.rx2 I/LLOG --->: skipWhile 8
        05-06 17:44:32.901 18098-18196/com.blood.rx2 I/LLOG --->: skipWhile 9
        05-06 17:44:33.901 18098-18196/com.blood.rx2 I/LLOG --->: skipWhile 10
        ......
         */
    }

    // 判断发送的数据是否满足条件，满足则发送，否则不发送
    private void takeWhile() {
        Observable.interval(1, TimeUnit.SECONDS)
                .takeWhile(new Predicate<Long>() {
                    @Override
                    public boolean test(Long aLong) throws Exception {
                        return aLong < 4;
                    }
                })
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        LLog.i("takeWhile " + aLong);
                    }
                });

        /*
        05-06 17:37:46.487 13330-13869/com.blood.rx2 I/LLOG --->: takeWhile 0
        05-06 17:37:47.487 13330-13869/com.blood.rx2 I/LLOG --->: takeWhile 1
        05-06 17:37:48.487 13330-13869/com.blood.rx2 I/LLOG --->: takeWhile 2
        05-06 17:37:49.487 13330-13869/com.blood.rx2 I/LLOG --->: takeWhile 3
         */
    }

    // 判断是否全部都满足该条件
    private void all() {
        Observable.just(1, 2, 3)
                .all(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer < 4;
                    }
                })
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        LLog.i("all " + aBoolean);
                    }
                });

        /*
         *  05-06 17:32:13.980 10356-10356/com.blood.rx2 I/LLOG --->: all true
         */
    }

}
