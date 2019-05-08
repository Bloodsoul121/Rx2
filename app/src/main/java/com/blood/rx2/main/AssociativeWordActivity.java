package com.blood.rx2.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.blood.rx2.R;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class AssociativeWordActivity extends AppCompatActivity {

    @BindView(R.id.edit)
    EditText mEdit;
    @BindView(R.id.tv)
    TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_associative_word);
        ButterKnife.bind(this);
        init();
    }

    /**
     *  当输入框有变化时，等待1秒，无新变化时，发送事件
     */
    private void init() {
        RxTextView.textChanges(mEdit)
                .debounce(1, TimeUnit.SECONDS)
                .skip(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CharSequence>() {
                    @Override
                    public void accept(CharSequence charSequence) throws Exception {
                        mTv.setText(charSequence);
                    }
                });
    }
}
