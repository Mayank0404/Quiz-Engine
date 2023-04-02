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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class addstudent extends AppCompatActivity {
    EditText id,name,pass,pno,address,email;
    Button btn_save;
    FirebaseFirestore db;
    Spinner year_student,couse_student;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addstudent);
        id=findViewById(R.id.txt_add_student_id);
        name=findViewById(R.id.txt_add_student_name);
        pass=findViewById(R.id.txt_add_student_password);
        btn_save=findViewById(R.id.btn_save_addstudent);
        pno=findViewById(R.id.txt_add_student_pno);
        email=findViewById(R.id.txt_add_student_email);
        address=findViewById(R.id.txt_add_student_address);
        year_student=findViewById(R.id.sp_year_addstudent);
        couse_student=findViewById(R.id.sp_course_addstudent);

        db = FirebaseFirestore.getInstance();
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> user = new HashMap<>();
                user.put("password", pass.getText().toString());
                user.put("name", name.getText().toString());

                user.put("pno", pno.getText().toString());
                user.put("address", address.getText().toString());

                user.put("email",email.getText().toString());
                user.put("uid", id.getText().toString());

                user.put("year",year_student.getSelectedItem().toString());
                user.put("course", couse_student.getSelectedItem().toString());


                db.collection("students").document(id.getText().toString()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(addstudent.this, "saved", Toast.LENGTH_SHORT).show();
                    }


                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(addstudent.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });





                id.setText("");
                name.setText("");
                pass.setText("");
                address.setText("");
                pno.setText("");
                email.setText("");

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