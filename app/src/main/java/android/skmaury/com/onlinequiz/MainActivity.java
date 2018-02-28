package android.skmaury.com.onlinequiz;

import android.content.DialogInterface;
import android.content.Intent;
import android.skmaury.com.onlinequiz.Model.User;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class MainActivity extends AppCompatActivity {

    /* Declaring variables for SignUp */
    private MaterialEditText newUserName, newPassword, newEmail;

    /* Declaring variables for SignIn */
    private MaterialEditText userName, password;

    /* Declaring variables for SignIn and SignUp buttons */
    Button signUp, signIn;

    /* Declaring reference for FireBase Database */
    FirebaseDatabase firebaseDatabase;

    /* Declaring reference for DataBase Reference */
    DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Setting {@params firebaseDatabase} */
        firebaseDatabase = FirebaseDatabase.getInstance();
        users = firebaseDatabase.getReference("Users");

        userName = findViewById(R.id.userName);
        password = findViewById(R.id.password);

        signIn = findViewById(R.id.sign_in);
        signUp = findViewById(R.id.sign_up);

        /* Setting click listeners for buttons */
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignUpDialog();
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserSignIn(userName.getText().toString(), password.getText().toString());
            }
        });

    }

    private void UserSignIn(final String user, final String pwd) {
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(user).exists()){
                    if(!user.isEmpty()){
                        User login = dataSnapshot.child(user).getValue(User.class);
                        if(login.getPassword().equals(pwd)){
                            Intent homeActivity = new Intent(MainActivity.this,
                                    HomeActivity.class);
                            startActivity(homeActivity);
                            finish();
                        }
                        else
                            Toast.makeText(MainActivity.this,
                                    getString(R.string.wrong_password), Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(MainActivity.this,
                                R.string.enter_user_name, Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(MainActivity.this,
                            getString(R.string.user_not_exists), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showSignUpDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle(R.string.sign_up);
        alertDialog.setMessage(R.string.alert_dialog_fill);

        LayoutInflater inflater = this.getLayoutInflater();
        View sign_up_layout = inflater.inflate(R.layout.sign_up_layout, null);

        newUserName = (MaterialEditText) sign_up_layout.findViewById(R.id.newUserName);
        newPassword = (MaterialEditText) sign_up_layout.findViewById(R.id.newPassword);
        newEmail = (MaterialEditText) sign_up_layout.findViewById(R.id.newEmail);

        alertDialog.setView(sign_up_layout);
        alertDialog.setIcon(R.drawable.ic_account_circle_black_24dp);

        alertDialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final User user = new User(newUserName.getText().toString(),
                        newPassword.getText().toString(),
                        newEmail.getText().toString());
                users.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(user.getUserName()).exists())
                            Toast.makeText(MainActivity.this,
                                    getString(R.string.already_exists), Toast.LENGTH_SHORT).show();
                        else{
                            users.child(user.getUserName())
                                    .setValue(user);
                            Toast.makeText(MainActivity.this,
                                    getString(R.string.user_reg_success), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }
}
