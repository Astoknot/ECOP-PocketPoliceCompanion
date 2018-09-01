package com.bellatrix.aditi.e_cop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.bellatrix.aditi.e_cop.Cop.SignUpAsCopActivity;
import com.bellatrix.aditi.e_cop.Court.SignUpAsCourtActivity;
import com.bellatrix.aditi.e_cop.User.SignUpAsUserActivity;

public class SignUpActivity extends AppCompatActivity {

    ImageButton ibUser,ibCop,ibCourt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ibUser = (ImageButton)findViewById(R.id.userButton);
        ibCop = (ImageButton)findViewById(R.id.copButton);
        ibCourt = (ImageButton)findViewById(R.id.courtButton);

        ibUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this,SignUpAsUserActivity.class));
                finish();
            }
        });

        ibCop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this,SignUpAsCopActivity.class));
                finish();
            }
        });

        ibCourt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this,SignUpAsCourtActivity.class));
                finish();
            }
        });

    }
}
