package com.example.quizengine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText userid,password;
    Spinner sp;
    Button btn;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userid=findViewById(R.id.txt_login_userid);
        password=findViewById(R.id.txt_login_password);
        sp=findViewById(R.id.sp_login_category);
        btn=findViewById(R.id.btn_login);
        db=FirebaseFirestore.getInstance();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userid.getText().toString().equals("admin") && password.getText().toString().equals("1234") && sp.getSelectedItem().toString().equals("ADMIN"))
                {

                    Intent intent = new Intent(MainActivity.this, Admin_home.class);
                    startActivity(intent);
                }

                else{
                    DocumentReference docRef = db.collection("students").document(userid.getText().toString());
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    Map<String,Object> student=document.getData();
                                    if (student.get("password").toString().equals(password.getText().toString()))
                                    {
                                        SharedPreferences sp=getSharedPreferences("myquiz",MODE_PRIVATE);
                                        SharedPreferences.Editor editor=sp.edit();
                                        editor.putString("userid",userid.getText().toString());
                                        editor.commit();
                                        Intent intent=new Intent(MainActivity.this,Student_home.class);
                                        startActivity(intent);
                                    }

                                } else {

                                    Toast.makeText(MainActivity.this, "invalid user", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(MainActivity.this, "connectivity issue", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }

            }
        });
    }
}