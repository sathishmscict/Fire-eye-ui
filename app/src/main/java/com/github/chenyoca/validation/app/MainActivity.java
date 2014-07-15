package com.github.chenyoca.validation.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.chenyoca.validation.AndroidValidator;
import com.github.chenyoca.validation.Display;
import com.github.chenyoca.validation.supports.EditTextLazyLoader;
import com.github.chenyoca.validation.Types;
import com.github.chenyoca.validation.Config;
import com.github.chenyoca.validation.ResultWrapper;

public class MainActivity extends Activity {

    Display testDisplay = new Display() {
        @Override
        public void dismiss(EditText field) {

        }

        @Override
        public void show(EditText field, String message) {
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Config conf = Config.build(Types.Required).message("必填选项").commit();
        conf.add(Types.MaxLength).values(20).commit();
        conf.add(Types.Email).commit();

        final EditText test = (EditText) findViewById(R.id.single_test);

        final Button commit = (Button) findViewById(R.id.single_commit);
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResultWrapper rw = AndroidValidator.testField(test, conf, testDisplay);
                int color = rw.passed ?
                        android.R.color.holo_green_dark : android.R.color.holo_red_dark;
                commit.setTextColor(getResources().getColor(color));
            }
        });

        final LinearLayout form = (LinearLayout) findViewById(R.id.form);
        final AndroidValidator av = new AndroidValidator(testDisplay);
        av.putField(R.id.form_field_1, Types.ChineseMobilePhone, Types.Required);
        av.putField(R.id.form_field_2, Types.CreditCard);
        av.putField(R.id.form_field_3, Types.Digits);
        av.putField(R.id.form_field_4, Types.Email);
        av.putField(R.id.form_field_5, Config.build(Types.EqualTo).loader(new EditTextLazyLoader(test)).commit());
        av.putField(R.id.form_field_6, Types.Host);
        av.putField(R.id.form_field_7, Types.URL);
        av.putField(R.id.form_field_8, Types.MaxLength);
        av.putField(R.id.form_field_9, Types.MinLength);
        av.putField(R.id.form_field_10, Types.LengthInRange);
        av.putField(R.id.form_field_11, Types.NotBlank);
        av.putField(R.id.form_field_12, Types.Numeric);
        av.putField(R.id.form_field_13, Config.build(Types.MaxValue).values(100).commit());
        av.putField(R.id.form_field_14, Config.build(Types.MinValue).values(20).commit());
        av.putField(R.id.form_field_15, Config.build(Types.ValueInRange).values(18, 30).commit());

        av.bind(form)
          .applyTypeToView();

        final Button formCommit = (Button) findViewById(R.id.form_commit);
        formCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int color = av.testFormAll(form) ?
                        android.R.color.holo_green_dark : android.R.color.holo_red_dark;
                formCommit.setTextColor(getResources().getColor(color));

            }
        });

    }
}
