package com.ep.eventparticipant.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ep.eventparticipant.R;
import com.ep.eventparticipant.object.User;
import com.ep.eventparticipant.other.AsHttpUtils;

import static com.ep.eventparticipant.fragment.FragmentUser.curUser;

public class midificatePhone extends AppCompatActivity {
    private Button return_;
    private EditText Phone;
    private TextView finish;
    //    User user = new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_midificate_phone);
        return_ = findViewById(R.id.return_);
        return_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                finish();
            }
        });
        Phone =  findViewById(R.id.modificate_Phone);
        Phone.setText(curUser.getPhone());

        finish = findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tel = Phone.getText().toString();
                curUser.setPhone(tel);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        AsHttpUtils.updateUserInfo(curUser);
                    }
                }).start();

                finish();
            }
        });
    }
}
