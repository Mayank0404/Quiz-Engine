package com.example.quizengine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Student_home extends AppCompatActivity {
    TextView q;
    RadioButton rb1,rb2,rb3,rb4;
    Button next,takequiz,scoreview;
    FirebaseFirestore db;
    LinearLayout ll;
    ArrayList<Map<String,Object>> questional=new ArrayList<>();
    Map<String,Object> questionmap;
    int count=0;
    int score=0;
    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);
        SharedPreferences sp=getSharedPreferences("myquiz",MODE_PRIVATE);
        str=sp.getString("userid", "na");
        db=FirebaseFirestore.getInstance();
        ll=findViewById(R.id.student_home_ll);
        ll.setVisibility(View.INVISIBLE);
        q=findViewById(R.id.tv_question_home);
        rb1=findViewById(R.id.rb1);
        rb2=findViewById(R.id.rb2);
        rb3=findViewById(R.id.rb3);
        rb4=findViewById(R.id.rb4);
        next=findViewById(R.id.btn_next);
        takequiz=findViewById(R.id.btn_takequiz);
        scoreview=findViewById(R.id.btn_score);
        scoreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Student_home.this, score.class);
                startActivity(intent);




            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rb1.isChecked() && rb1.getText().toString().equals(questionmap.get("correct").toString()))
                    score++;
                else if (rb2.isChecked() && rb2.getText().toString().equals(questionmap.get("correct").toString()))
                    score++;
                else if (rb3.isChecked() && rb3.getText().toString().equals(questionmap.get("correct").toString()))
                    score++;
                else if (rb4.isChecked() && rb4.getText().toString().equals(questionmap.get("correct").toString()))
                    score++;
                if(count<questional.size()-1) {
                    count++;
                    dispquestion();
                }
                else
                {
                    Toast.makeText(Student_home.this, "your score is"+score, Toast.LENGTH_SHORT).show();
                    ll.setVisibility(View.INVISIBLE);
                    Map<String, Object> user = new HashMap<>();
                    user.put("score",score);
                    db.collection("students").document(str).update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(Student_home.this, " score updated", Toast.LENGTH_SHORT).show();
                        }


                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Student_home.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });

        takequiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                score=0;

                ll.setVisibility(View.VISIBLE);
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
                                    dispquestion();
                                } else {


                                }
                            }
                        });


            }
        });
    }
    void dispquestion()
    {
        questionmap=questional.get(count);
        q.setText(questionmap.get("question").toString());
        rb1.setText(questionmap.get("ans1").toString());
        rb2.setText(questionmap.get("ans2").toString());
        rb3.setText(questionmap.get("ans3").toString());
        rb4.setText(questionmap.get("ans4").toString());
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.student_home,menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.mnu_edit_student) {
            Intent intent = new Intent(Student_home.this, editprofile.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}