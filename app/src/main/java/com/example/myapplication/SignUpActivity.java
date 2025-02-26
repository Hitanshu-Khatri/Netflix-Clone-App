package com.example.myapplication;
import static android.widget.Toast.LENGTH_LONG;

import static com.example.myapplication.R.id.Next;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import android.text.method.PasswordTransformationMethod;
import android.widget.ImageView;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    private EditText editText, Name, Pass, Confirm;
    private ImageView showPasswordIcon;
    private Button Next;
    private boolean isPasswordVisible = false;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        editText = findViewById(R.id.editText);
        Name = findViewById(R.id.Name);
        Pass = findViewById(R.id.Pass);
        Confirm = findViewById(R.id.Confirm);
        Next = findViewById(R.id.Next);
        showPasswordIcon = findViewById(R.id.showPasswordIcon);


        showPasswordIcon.setOnClickListener(v->{
            if(isPasswordVisible){
                Pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                showPasswordIcon.setImageResource(R.drawable.eye_key_look_password_security_see_svgrepo_com);
            }    else{
                Pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                showPasswordIcon.setImageResource(R.drawable.eye_password_hide_svgrepo_com);
            }
            isPasswordVisible = !isPasswordVisible;
        });

        Next.setOnClickListener(v->{
            String name = Name.getText().toString().trim();
            String password = Pass.getText().toString().trim();
            String Conf = Confirm.getText().toString().trim();

            if(password.equals(Conf)){
                Toast.makeText(SignUpActivity.this,"Account Created",LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), HomeActivity.class)); // Redirect to the home screen
                finish();
            }else{
                Toast.makeText(SignUpActivity.this,"Password mismatch",LENGTH_LONG).show();
            }
        });






    }

}
