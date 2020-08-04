package com.bulksms;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bulksms.utils.DataManager;
import com.bulksms.utils.DbOpenHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddContactFragment extends Fragment{
    private static final String TAG = "AddContactFragment";
    EditText etName, etPhone;
    Button btnSave;
    ProgressBar pbSaving;
    private FloatingActionButton mFab;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: Started.");
        mFab = getActivity().findViewById(R.id.fab);
        mFab.setVisibility(View.INVISIBLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_add_contact, container, false);

        final Context context = view.getContext();
        btnSave = (Button) view.findViewById(R.id.btn_save);
        etName = (EditText) view.findViewById(R.id.et_name);
        etPhone = (EditText) view.findViewById(R.id.et_phone);
        pbSaving = (ProgressBar) view.findViewById(R.id.pb_saving);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertContact(context);
            }
        });
        return view;
    }

    private void insertContact(Context context) {
        Log.d(TAG, "insertContact: Called.");
        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        int phoneLength = phone.length();
        if(phoneLength != 10){
            etPhone.setError("A valid phone number is required");
        }
        else{
            pbSaving.setVisibility(View.VISIBLE);
            DbOpenHelper dbHelper = new DbOpenHelper(context);
            DataManager dm = new DataManager(dbHelper);
            dm.insertContact(name, phone);
            etName.setText("");
            etPhone.setText("");
            pbSaving.setVisibility(View.INVISIBLE);
            makeToast("Added", context);
            Log.d(TAG, "insertContact: 1 contact added.");
        }
    }

    public void makeToast(String text, Context context){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}