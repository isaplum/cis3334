package css.cis3334.firebaseauthentication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {

    private TextView textViewStatus;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private Button buttonGoogleLogin;
    private Button buttonCreateLogin;
    private Button buttonSignOut;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


/*
 *  Initiates variables that identify UI elements.
 *  Call for actions to be taken upon initialization of app
 *  Defines actions carried out in response to interactions made by user (button clicks, etc.)
 * @param Bundle savedInstanceState
 * @override
 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("CIS3334", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("CIS3334", "onAuthStateChanged:signed_out");
                }
            }

        };


        textViewStatus = (TextView) findViewById(R.id.textViewStatus);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonGoogleLogin = (Button) findViewById(R.id.buttonGoogleLogin);
        buttonCreateLogin = (Button) findViewById(R.id.buttonCreateLogin);
        buttonSignOut = (Button) findViewById(R.id.buttonSignOut);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                signIn(editTextEmail.getText().toString(), editTextPassword.getText().toString());
            }
        });
//When Create Login button is clicked, user will create an account
        buttonCreateLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                createAccount(editTextEmail.getText().toString(), editTextPassword.getText().toString());
            }
        });
//When Google sign in button clicked, user will be signed in to google
        buttonGoogleLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                googleSignIn();
            }
        });
//When sign out button clicked, user will be signed out
        buttonSignOut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                signOut();
            }
        });


    }
    /*
     *  Creates user account
     *  Displays error message if error occurred when account is being created
     * @param String email, String password
     */
    private void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            textViewStatus.setText("Authentication failed.");

                        }

                        // ...
                    }
                });

    }
    /*
        *  Authenticates user and allows them to sign in.  If unsuccessful, error message appears displaying the text "Authentication failed"
        *
        * @param String email, String password
        */
    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {

                            textViewStatus.setText("Authentication failed.");
                        }

                        // ...
                    }
                });
    }


    /*
        *  Allows user to sign out
        *
        */
    private void signOut() {
    mAuth.signOut();
    }

    private void googleSignIn() {

    }

    /*
    *  Starts session
    *
    */
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    /*
    *  stops session
    *
    */
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }

    }

}
