package com.example.quizengine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class view_student extends AppCompatActivity {
    TextView name,pno,email,add,id,year,course;
    String str;
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student);
        Intent intent=getIntent();
        db=FirebaseFirestore.getInstance();
        str=intent.getStringExtra("username");
        id=findViewById(R.id.tv_student_id);
        name=findViewById(R.id.tv_student_name);
        email=findViewById(R.id.tv_student_email);
        pno=findViewById(R.id.tv_student_pno);
        add=findViewById(R.id.tv_student_address);
        year=findViewById(R.id.tv_student_year);
        course=findViewById(R.id.tv_student_course);
        DocumentReference docRef = db.collection("students").document(str);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Map<String, Object> student = document.getData();
                        name.setText("NAME:  "+student.get("name").toString());
                        id.setText("ID:  "+str);
                        email.setText("EMAIL:  "+student.get("pno").toString());
                        pno.setText("PHONE NO.:  "+student.get("pno").toString());
                        add.setText("ADDRESS:  "+student.get("address").toString());
                        year.setText("YEAR:  "+student.get("year").toString());
                        course.setText("COURSE:  "+student.get("course").toString());


                    } else {
                        Toast.makeText(view_student.this, "connectivity issue", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }
}