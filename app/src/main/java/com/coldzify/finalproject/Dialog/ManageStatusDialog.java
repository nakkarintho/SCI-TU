package com.coldzify.finalproject.Dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.coldzify.finalproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ManageStatusDialog extends DialogFragment {
    private static final String TAG = "ManageStatusDialog";


    private Button ok_button;
    private int progress;
    private String reportID,user_name;
    private Spinner spinner;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        db.collection("users").document(mAuth.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful() && task.getResult() != null){
                            DocumentSnapshot doc = task.getResult();
                            if(doc.getString("userType") != null){
                                String firstname = doc.getString("firstname");
                                String lastname = doc.getString("lastname");
                                user_name = firstname +  " "+lastname;
                            }
                        }
                    }
                });
        if(getArguments() != null){
            progress = getArguments().getInt("progress");
            reportID = getArguments().getString("reportID");
        }

    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.manage_status_dialog,null);

        spinner = view.findViewById(R.id.spinner);

        String[] arr = getResources().getStringArray(R.array.report_status);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(),
                android.R.layout.simple_dropdown_item_1line,arr);
        spinner.setAdapter(adapter);
        spinner.setSelection(progress-1);
        ok_button = view.findViewById(R.id.ok_button);


        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ok_button.setText("กำลังโหลด...");
                 ok_button.setEnabled(false);
                if(spinner.getSelectedItemPosition()+1 <= progress){
                    dismiss();
                    return;
                }
                final FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference ref =  db.collection("reports").document(reportID);
                ref.update("status",spinner.getSelectedItemPosition()+1)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getContext(),"เปลี่ยนสถานะเรียบร้อยแล้ว",Toast.LENGTH_LONG).show();
                                }
                                else{
                                    Toast.makeText(getContext(),"เกิดข้อผิดพลาด ไมสามารถเปลี่ยนสถานะได้",Toast.LENGTH_LONG).show();
                                }
                                dismiss();
                            }
                        });
                Date d = new Date();
                ref.update("takecareBy",user_name);
                ref.update("lastModified", new Timestamp(d));
                Map<String, Object> docData = new HashMap<>();
                docData.put("staffname", user_name);
                docData.put("date", new Timestamp(d));
                ref.collection("takecareBy").add(docData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                            }
                        });
            }
        });

        builder.setView(view);
        return builder.create();
    }



    @Override
    public void onDestroyView() {
        Dialog dialog = getDialog();
        // handles https://code.google.com/p/android/issues/detail?id=17423
        if (dialog != null && getRetainInstance()) {
            dialog.setDismissMessage(null);
        }
        super.onDestroyView();
    }



}
