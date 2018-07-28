package com.ep.eventparticipant.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ep.eventparticipant.R;
import com.ep.eventparticipant.object.User;

public class modification extends AppCompatActivity {
    private Button return_;
    private EditText Name;
    private TextView finish;
    User user = new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modification);
        return_ = findViewById(R.id.return_);
        finish = findViewById(R.id.finish);
        return_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                finish();
            }
        });
        Name =  findViewById(R.id.modificate_name);
        Name.setText(user.getName());
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }
}
