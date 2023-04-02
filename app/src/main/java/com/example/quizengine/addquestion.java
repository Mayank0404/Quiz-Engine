package com.example.quizengine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class addquestion extends AppCompatActivity {
    EditText txtquestion,txtans1,txtans2,txtans3,txtans4,txtcorrect;
    Button bt;
    Spinner year,couse;
    FirebaseFirestore db ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addquestion);
        txtquestion=findViewById(R.id.question);
        txtans1=findViewById(R.id.ans1);
        txtans2=findViewById(R.id.ans2);
        txtans3=findViewById(R.id.ans3);
        txtans4=findViewById(R.id.ans4);
        txtcorrect=findViewById(R.id.correct);
        bt=findViewById(R.id. save);
        year=findViewById(R.id.sp_year_category);
        couse=findViewById(R.id.sp_course_category);
        db = FirebaseFirestore.getInstance();
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> user = new HashMap<>();
                user.put("question", txtquestion.getText().toString());
                user.put("ans1", txtans1.getText().toString());
                user.put("ans2", txtans2.getText().toString());
                user.put("ans3", txtans3.getText().toString());
                user.put("ans4", txtans4.getText().toString());
                user.put("correct",txtcorrect.getText().toString());
                user.put("year",year.getSelectedItem().toString());
                user.put("course",couse.getSelectedItem().toString());

                db.collection("questions")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(addquestion.this, "data saved", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(addquestion.this,e.getMessage() , Toast.LENGTH_SHORT).show();
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