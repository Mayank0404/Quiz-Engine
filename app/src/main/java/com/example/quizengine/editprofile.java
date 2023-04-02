package com.example.quizengine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class editprofile extends AppCompatActivity {
    EditText name,pno,pass,address,email;
    Button update;
    String userid;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);
        name=findViewById(R.id.txt_edit_student_name);
        pno=findViewById(R.id.txt_edit_student_pno);
        pass=findViewById(R.id.txt_edit_student_password);
        email=findViewById(R.id.txt_edit_student_email);
        address=findViewById(R.id.txt_edit_student_address);
        update=findViewById(R.id.btn_update_edit);
        db=FirebaseFirestore.getInstance();
        SharedPreferences sp=getSharedPreferences("myquiz",MODE_PRIVATE);
        userid=sp.getString("userid", "na");
        DocumentReference docRef = db.collection("students").document(userid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Map<String, Object> student = document.getData();
                        name.setText(student.get("name").toString());
                        pass.setText(student.get("password").toString());
                        pno.setText(student.get("pno").toString());
                        email.setText(student.get("email").toString());
                        address.setText(student.get("address").toString());
                    } else {
                        Toast.makeText(editprofile.this, "connectivity issue", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a new user with a first and last name
                Map<String, Object> user = new HashMap<>();
                user.put("password", pass.getText().toString());
                user.put("name", name.getText().toString());


                user.put("address", address.getText().toString());
                user.put("email", email.getText().toString());
                user.put("pno", email.getText().toString());


                db.collection("students").document(userid).update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(editprofile.this, "saved", Toast.LENGTH_SHORT).show();
                    }


                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(editprofile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });





            }
        });


    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.about_us,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
}