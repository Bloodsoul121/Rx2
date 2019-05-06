package com.blood.rx2.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.blood.rx2.R;

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
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

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
                onFunctionClick(str, position);
            }
        });
        mRecyclerview.setAdapter(adapter);
        mRecyclerview.setHasFixedSize(true);
        adapter.setData(getFunctionTitle());
    }

    private List<String> getFunctionTitle() {
        List<String> list = new ArrayList<>();
        list.add("create");
        list.add("just");
        list.add("fromArray");
        list.add("fromIterable");
        list.add("defer");
        list.add("timer");
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
        return list;
    }

    private void onFunctionClick(String str, int position) {
        switch (str) {
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
        }
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
    }

    private void create() {
        Observable<Object> observable = Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {

            }
        });
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