package com.example.quizengine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class score extends AppCompatActivity {

    TextView grade,marks,anscorrect,totalquestion;

    FirebaseFirestore db;
    String userid;
    ArrayList<Map<String,Object>> questional=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        SharedPreferences sp=getSharedPreferences("myquiz",MODE_PRIVATE);
        userid=sp.getString("userid", "na");
        grade=findViewById(R.id.grade);
        marks=findViewById(R.id.marks);
        totalquestion=findViewById(R.id.totalquestions);
        anscorrect=findViewById(R.id.answercorrect);

        db=FirebaseFirestore.getInstance();
        db.collection("questions")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String,Object> question1=document.getData();
                                questional.add(question1);

                            }

                        } else {


                        }
                    }
                });




        totalquestion.setText(questional.size()+"");
        DocumentReference docRef = db.collection("students").document(userid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Map<String, Object> student = document.getData();
                        marks.setText(student.get("score").toString());
                        anscorrect.setText(student.get("score").toString());

                    } else {
                        Toast.makeText(score.this, "connectivity issue", Toast.LENGTH_SHORT).show();
                    }
                }

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