package com.example.quizengine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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

public class updatestudent_viadmin extends AppCompatActivity {

    EditText name,pass,pno,address,email;
    Button btn_save;
    FirebaseFirestore db;
    Spinner year_student,couse_student;
    String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatestudent_viadmin);

        Intent intent=getIntent();
        str=intent.getStringExtra("username");
        db=FirebaseFirestore.getInstance();
        name=findViewById(R.id.txt_update_student_name);
        pass=findViewById(R.id.txt_update_student_password);
        btn_save=findViewById(R.id.btn_update_edit_admin);
        pno=findViewById(R.id.txt_update_student_pno);
        email=findViewById(R.id.txt_update_student_email);
        address=findViewById(R.id.txt_update_student_address);
        year_student=findViewById(R.id.sp_year_update);
        couse_student=findViewById(R.id.sp_course_update);
        DocumentReference docRef = db.collection("students").document(str);
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
                        Toast.makeText(updatestudent_viadmin.this, "connectivity issue", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> user = new HashMap<>();
                user.put("password", pass.getText().toString());
                user.put("name", name.getText().toString());


                user.put("address", address.getText().toString());
                user.put("email", email.getText().toString());
                user.put("pno", email.getText().toString());
                user.put("year",year_student.getSelectedItem().toString());
                user.put("course", couse_student.getSelectedItem().toString());


                db.collection("students").document(str).update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(updatestudent_viadmin.this, "updated", Toast.LENGTH_SHORT).show();
                    }


                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(updatestudent_viadmin.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });




            }
        });

    }
}