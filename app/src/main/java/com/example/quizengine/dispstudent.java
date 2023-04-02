package com.example.quizengine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class dispstudent extends AppCompatActivity {
    TableLayout tl;
    FirebaseFirestore db;
    TextView currentTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispstudent);
        tl=findViewById(R.id.disp_table);
        db = FirebaseFirestore.getInstance();
        db.collection("students")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d(TAG, document.getId() + " => " + document.getData());
                                Map<String,Object> user=document.getData();
                                String userid=user.get("uid").toString();
                                String name=user.get("name").toString();
                                String pno=user.get("pno").toString();
                                String course=user.get("course").toString();
                                TextView tv1=new TextView(dispstudent.this);
                                TextView tv2=new TextView(dispstudent.this);
                                TextView tv3=new TextView(dispstudent.this);
                                TextView tv4=new TextView(dispstudent.this);
                                tv1.setText(userid);
                                tv2.setText(name);
                                tv3.setText(course);
                                tv4.setText(pno);
                                tv1.setTextAppearance(R.style.textviewwstyle);
                                tv2.setTextAppearance(R.style.textviewwstyle);
                                tv3.setTextAppearance(R.style.textviewwstyle);
                                tv4.setTextAppearance(R.style.textviewwstyle);
                                TableRow row=new TableRow(dispstudent.this);
                                registerForContextMenu(row);
                                TableRow.LayoutParams lp;
                                row.setWeightSum(4);
                                lp=new TableRow.LayoutParams(0,TableRow.LayoutParams.WRAP_CONTENT,1f);
                                lp.setMargins(10,10,10,10);
                                tv1.setLayoutParams(lp);
                                tv2.setLayoutParams(lp);
                                tv3.setLayoutParams(lp);
                                tv4.setLayoutParams(lp);
                                row.setLayoutParams(lp);
                                row.addView(tv1);
                                row.addView(tv2);
                                row.addView(tv3);
                                row.addView(tv4);
                                tl.addView(row);


                            }
                        } else {
                            // Log.w(TAG, "Error getting documents.", task.getException());
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater mi=this.getMenuInflater();
        mi.inflate(R.menu.disp_student,menu);

        TableRow t=(TableRow)v;
        currentTextView=(TextView)t.getChildAt(0);



    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.mnu_admin_update_student)
        {

            Intent intent=new Intent(dispstudent.this,updatestudent_viadmin.class);
            intent.putExtra("username",currentTextView.getText().toString());


            startActivity(intent);


        }
        if(item.getItemId()==R.id.mnu_admin_delete_student)
        {
            String str=currentTextView.getText().toString();
            db.collection("students").document(str).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(dispstudent.this, "deleted ", Toast.LENGTH_SHORT).show();
                }


            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(dispstudent.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });



        }
        if(item.getItemId()==R.id.mnu_admin_view_student)
        {
            Toast.makeText(dispstudent.this, "selected", Toast.LENGTH_SHORT).show();

            Intent in=new Intent(dispstudent.this,view_student.class);
            in.putExtra("username",currentTextView.getText().toString());
            startActivity(in);



        }

        return super.onContextItemSelected(item);
    }
}
