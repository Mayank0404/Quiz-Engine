package com.example.quizengine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Admin_home extends AppCompatActivity {
    Button addstudent,addquestion,updatequestion,dispquestion,dispstudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        addquestion=findViewById(R.id.add_question_admin);
        addstudent=findViewById(R.id.add_student_admin);
        updatequestion=findViewById(R.id.update_question_admin);
        dispquestion=findViewById(R.id.disp_question_admin);
        dispstudent=findViewById(R.id.disp_student_admin);

        addquestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Admin_home.this,addquestion.class);
                startActivity(intent);
            }
        });
        addstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Admin_home.this,addstudent.class);
                startActivity(intent);

            }
        });
        dispstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Admin_home.this,dispstudent.class);
                startActivity(intent);

            }
        });
        dispquestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Admin_home.this,dispquestion.class);
                startActivity(intent);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.about_us,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
}