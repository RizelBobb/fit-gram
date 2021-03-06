package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {
  Boolean signUpModeActive = true;
  TextView changeSignupModeTextView;
  EditText passwordEditText;

  public void showUserList(){
      Intent intent = new Intent(getApplicationContext(), UserListActivity.class);
      startActivity(intent);

  }
  @Override
  public boolean onKey(View view, int i, KeyEvent keyEvent) {

    if (keyEvent.getAction() == KeyEvent.ACTION_DOWN)
    {
      if(i == KeyEvent.KEYCODE_ENTER)
      {
        signUp(view);
        return true;
      }

    }
    return false;
  }

  @Override
  public void onClick(View view ) {
    if (view.getId() == R.id.changeSignupModeTextView) {
      Button signUpButton = (Button) findViewById(R.id.signupButton);
      if (signUpModeActive) {
        signUpModeActive = false;
        signUpButton.setText("Login");
        changeSignupModeTextView.setText("or Sign Up");
        //orginally says login but need to switch to sign up
      } else {
        signUpModeActive = true;
        signUpButton.setText("Signup");
        changeSignupModeTextView.setText("or Login");
      }
    }else if (view.getId() == R.id.backgroundRelativeLayout){

      InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
      inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
    }
  }

  public void signUp(View view) {

    EditText usernameEditText = (EditText) findViewById(R.id.usernameEditText);

    if (usernameEditText.getText().toString().matches("") || passwordEditText.getText().toString().matches("")) {
      Toast.makeText(this, "A username and password are required", Toast.LENGTH_SHORT).show();
    } else {
      if (signUpModeActive) {
        ParseUser user = new ParseUser();
        user.setUsername(usernameEditText.getText().toString());
        user.setPassword(passwordEditText.getText().toString());

        user.signUpInBackground(new SignUpCallback() {
          @Override
          public void done(ParseException e) {
            if (e == null) {
              Log.i("Signup", "Successful");

              //showUserList(); //showing the list of user after signed in
            } else {
              Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
          }
        });
      } else {
        ParseUser.logInInBackground(usernameEditText.getText().toString(), passwordEditText.getText().toString(), new LogInCallback() {
          @Override
          public void done(ParseUser user, ParseException e) {
            if (user != null) {
              Log.i("Signup", "Login successful");
              showUserList(); //when the user logs in

            } else {
              Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
          }
        });
      }
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);

    changeSignupModeTextView = (TextView) findViewById(R.id.changeSignupModeTextView);

    changeSignupModeTextView.setOnClickListener(this);

    RelativeLayout backgroundRelativeLayout = (RelativeLayout) findViewById(R.id.backgroundRelativeLayout)  ;

    backgroundRelativeLayout.setOnClickListener(this);

    ImageView logoImageView = (ImageView) findViewById(R.id.logoImageView);

    logoImageView.setOnClickListener(this);

    passwordEditText = (EditText) findViewById(R.id.passwordEditText);

    passwordEditText.setOnKeyListener(this);


    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }



}