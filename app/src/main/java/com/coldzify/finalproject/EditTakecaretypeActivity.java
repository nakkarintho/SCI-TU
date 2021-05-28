package com.coldzify.finalproject;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.coldzify.finalproject.dataobject.UserProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditTakecaretypeActivity extends AppCompatActivity {
    private AutoCompleteTextView email_autoComplete;
    private FirebaseFirestore db;
    private String docpath = "";
    private String email_addtakecaretype = "";
    private String all_takecaretype = "";
    private String role = "";
    private ArrayList<String> alldatacheck = new ArrayList<>();
    private String alldatacheck_finish = "";
    private LinearLayout addTakeCareType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edittakecaretype);
        db = FirebaseFirestore.getInstance();
        email_autoComplete = findViewById(R.id.email_autoComplete);
        addTakeCareType = findViewById(R.id.addTakeCareType);
    }


    public void OnClickSearchEmail(View view){
        email_addtakecaretype = email_autoComplete.getText().toString();

        CheckBox problem1 = (CheckBox) findViewById(R.id.problemcheck1);
        CheckBox problem2 = (CheckBox) findViewById(R.id.problemcheck2);
        CheckBox problem3 = (CheckBox) findViewById(R.id.problemcheck3);
        CheckBox problem4 = (CheckBox) findViewById(R.id.problemcheck4);
        CheckBox problem5 = (CheckBox) findViewById(R.id.problemcheck5);
        CheckBox problem6 = (CheckBox) findViewById(R.id.problemcheck6);
        CheckBox problem7 = (CheckBox) findViewById(R.id.problemcheck7);
        CheckBox problem8 = (CheckBox) findViewById(R.id.problemcheck8);
        CheckBox problem9 = (CheckBox) findViewById(R.id.problemcheck9);
        CheckBox problem10 = (CheckBox) findViewById(R.id.problemcheck10);

        problem1.setChecked(false);
        problem2.setChecked(false);
        problem3.setChecked(false);
        problem4.setChecked(false);
        problem5.setChecked(false);
        problem6.setChecked(false);
        problem7.setChecked(false);
        problem8.setChecked(false);
        problem9.setChecked(false);
        problem10.setChecked(false);

        db.collection("users")
                .get()
                .addOnCompleteListener(this,new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                UserProfile user = document.toObject(UserProfile.class);
                                if (user.getEmail().equals(email_addtakecaretype)) {
                                    docpath = document.getId();
                                    if(document.getString("takecareType")!=null) {
                                        all_takecaretype = document.getString("takecareType");
                                        String temp[] = all_takecaretype.split(",");
                                        for(int i=0;i<temp.length;i++){
                                            String checktemp = temp[i];
                                            switch (checktemp) {
                                                case "ELECTRICS":
                                                    problem1.setChecked(true);
                                                    break;
                                                case "WATER":
                                                    problem2.setChecked(true);
                                                    break;
                                                case "CONDITIONER":
                                                    problem3.setChecked(true);
                                                    break;
                                                case "MATERIAL":
                                                    problem4.setChecked(true);
                                                    break;
                                                case "TECHNOLOGY":
                                                    problem5.setChecked(true);
                                                    break;
                                                case "INTERNET":
                                                    problem6.setChecked(true);
                                                    break;
                                                case "BUILDING_ENVIRON":
                                                    problem7.setChecked(true);
                                                    break;
                                                case "TELEPHONE":
                                                    problem8.setChecked(true);
                                                    break;
                                                case "CLEAN_SECURITY":
                                                    problem9.setChecked(true);
                                                    break;
                                                case "OTHERS":
                                                    problem10.setChecked(true);
                                                    break;
                                                default:
                                                    break;
                                            }

                                        }


                                    }
                                    Log.d("takecareType", all_takecaretype);
                                    Log.d("CheckEmail", "Have Email In Systems");
                                    break;
                                } else {
                                    docpath = "";
                                }
                            }
                            if (docpath.equals("")) {
                                Log.w("tag", "Error Not Have This Email In Systems : ", task.getException());
                                Toast.makeText(EditTakecaretypeActivity.this, "ไม่มีอีเมลดังกล่าวในระบบ", Toast.LENGTH_LONG).show();
                            } else {
                                 addTakeCareType.setVisibility(View.VISIBLE);

                            }
                        }
                        else{
                            Log.w("tag","Error Not Have This Email In Systems : ",task.getException());
                            Toast.makeText(EditTakecaretypeActivity.this, "ไม่มีอีเมลดังกล่าวในระบบ", Toast.LENGTH_LONG).show();
                        }

                    }

                });



    }


    public  void OnClickChangeTakecaretype(View view){

        CheckBox problem1 = (CheckBox) findViewById(R.id.problemcheck1);
        CheckBox problem2 = (CheckBox) findViewById(R.id.problemcheck2);
        CheckBox problem3 = (CheckBox) findViewById(R.id.problemcheck3);
        CheckBox problem4 = (CheckBox) findViewById(R.id.problemcheck4);
        CheckBox problem5 = (CheckBox) findViewById(R.id.problemcheck5);
        CheckBox problem6 = (CheckBox) findViewById(R.id.problemcheck6);
        CheckBox problem7 = (CheckBox) findViewById(R.id.problemcheck7);
        CheckBox problem8 = (CheckBox) findViewById(R.id.problemcheck8);
        CheckBox problem9 = (CheckBox) findViewById(R.id.problemcheck9);
        CheckBox problem10 = (CheckBox) findViewById(R.id.problemcheck10);


        if(problem1.isChecked()){
            alldatacheck.add("ELECTRICS");
        }
        if(problem2.isChecked()){
            alldatacheck.add("WATER");
        }
        if(problem3.isChecked()){
            alldatacheck.add("CONDITIONER");
        }
        if(problem4.isChecked()){
            alldatacheck.add("MATERIAL");
        }
        if(problem5.isChecked()){
            alldatacheck.add("TECHNOLOGY");
        }
        if(problem6.isChecked()){
            alldatacheck.add("INTERNET");
        }
        if(problem7.isChecked()){
            alldatacheck.add("BUILDING_ENVIRON");
        }
        if(problem8.isChecked()){
            alldatacheck.add("TELEPHONE");
        }
        if(problem9.isChecked()){
            alldatacheck.add("CLEAN_SECURITY");
        }
        if(problem10.isChecked()){
            alldatacheck.add("OTHERS");
        }


        for(int i=0;i<alldatacheck.size();i++){
            if(alldatacheck_finish.equals("")){
                alldatacheck_finish = alldatacheck.get(i);
            }
            else {
                alldatacheck_finish = alldatacheck_finish + "," + alldatacheck.get(i);
            }
        }


        final DocumentReference docRef = db.collection("users").document(docpath);
        Map<String, Object> map = new HashMap<>();
        map.put("takecareType","" );
        map.put("takecareType",alldatacheck_finish );


        docRef.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                alldatacheck.clear();
                alldatacheck_finish = "";
                Log.d("tag", "Update Takecaretype Success");
                Toast.makeText(EditTakecaretypeActivity.this, "ระบบได้แก้ไขงานที่รอบผิดชอบเรียบร้อย", Toast.LENGTH_LONG).show();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("tag", "Failure : Not Have This Email In System", e);
                        Toast.makeText(EditTakecaretypeActivity.this, "ไม่มีอีเมลดังกล่าวในระบบ", Toast.LENGTH_LONG).show();
                    }
                });

            addTakeCareType.setVisibility(View.GONE);
    }


}
