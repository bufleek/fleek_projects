package com.bulksms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SendListActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "SendListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_list);
        Log.d(TAG, "onCreate: Started.");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        init();
    }

    private void init() {
        SendListFragment sendListFragment = new SendListFragment();
        FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_frame, sendListFragment, getString(R.string.send_list_fragment_tag));
        transaction.commit();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.fab){
            AddContactFragment addContactFragment = new AddContactFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_frame, addContactFragment, getString(R.string.add_contact_fragment_tag));
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}