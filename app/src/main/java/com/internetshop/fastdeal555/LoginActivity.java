package com.internetshop.fastdeal555;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText edEmail, edPassword;
    private FirebaseAuth mAuth;
    private TextView AdminLink, NotAdminLink;
    private Button LoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginButton=(Button)findViewById(R.id.loginbutton);
        init();
        AdminLink=(TextView) findViewById(R.id.admin_panel_link);
        NotAdminLink=(TextView) findViewById(R.id.not_admin_panel_link);
    }



    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser cUser = mAuth.getCurrentUser();
        if (cUser != null) {
            Toast.makeText(this, "User not null", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "User  null", Toast.LENGTH_SHORT).show();
        }

    }

    private void init(){
        edPassword=findViewById(R.id.login_password_input);
        edEmail=findViewById(R.id.login_phone_number_input);
        mAuth = FirebaseAuth.getInstance();
    }

    public void onClickSignUp(View view){

     if (!TextUtils.isEmpty(edEmail.getText().toString()) &&    !TextUtils.isEmpty(edPassword.getText().toString()))
        {
        mAuth.createUserWithEmailAndPassword(edEmail.getText().toString(), edPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                  if (task.isSuccessful()){
                      showsigned();
                      sentEmailver();
                      Toast.makeText(getApplicationContext(), "User SignUp Success", Toast.LENGTH_SHORT).show();
                  }
                  else{
                      Toast.makeText(getApplicationContext(), "User SignUp Faild", Toast.LENGTH_SHORT).show();

                  }
            }
        });


    }
    else{
        Toast.makeText(getApplicationContext(), "Please enter Email and Password", Toast.LENGTH_SHORT).show();
    }}

    private void showsigned() {
        FirebaseUser user= mAuth.getCurrentUser();
      assert user !=null;
        if(user.isEmailVerified()){
            Intent intent=new Intent(LoginActivity.this, MainActivity.class);
        }
    }



    public void onClickSignIn(View view){
        if (!TextUtils.isEmpty(edEmail.getText().toString())&& !TextUtils.isEmpty(edPassword.getText().toString()))
        {

        mAuth.signInWithEmailAndPassword(edEmail.getText().toString(), edPassword.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override

            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "User SignIn Success", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(LoginActivity.this, AdminCategoryActivity.class);
                    
                }
                else{
                    Toast.makeText(getApplicationContext(), "User SignIn Faild", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}

private void sentEmailver(){
        FirebaseUser user =mAuth.getCurrentUser();
 user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
     @Override
     public void onComplete(@NonNull Task<Void> task) {
         if(task.isSuccessful()){
             Toast.makeText(getApplicationContext(), "Check you email for verify email adress", Toast.LENGTH_SHORT).show();

         }
         else{
             Toast.makeText(getApplicationContext(), "Sent email failed", Toast.LENGTH_SHORT).show();

         }
     }
 });

}

    public void OnClickAdmin(View view) {
        LoginButton.setText("LoginAdmin");
        AdminLink.setVisibility(View.INVISIBLE);
        NotAdminLink.setVisibility(View.VISIBLE);

    }


    public void OnClicknotAdmin(View view) {
        LoginButton.setText("Login");
        AdminLink.setVisibility(View.VISIBLE);
        NotAdminLink.setVisibility(View.INVISIBLE);
    }
}