package com.bulksms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "MainActivity";
    private int selectedRecipients = 0;
    private boolean isMessageCheckPassed = false;
    private boolean isRecipientCheckPassed = false;
    Button btnSend, btnSelectRecipients;
    CheckBox cbAllRecipients;
    EditText etMessage;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_send_list){
            Intent intent = new Intent(MainActivity.this, SendListActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.fade_out);
            Log.d(TAG, "onOptionsItemSelected: Navigating to SendListActivity");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Started.");

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        btnSelectRecipients = (Button) findViewById(R.id.btn_select_recipients);
        btnSelectRecipients.setOnClickListener(this);
        btnSend = (Button) findViewById(R.id.btn_send);
        btnSend.setOnClickListener(this);
        cbAllRecipients = (CheckBox) findViewById(R.id.cb_all_recipients);
        etMessage = (EditText) findViewById(R.id.et_message);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.btn_select_recipients){
            selectRecipients();
        }

        else if(id == R.id.btn_send){
            checkMessage();
            checkRecipients();
        }
    }

    private void checkMessage() {
        Log.d(TAG, "checkMessage: Started.");
        String message = etMessage.getText().toString().trim();
        if(message.isEmpty()){
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setMessage(R.string.dialog_blank_message);
            dialogBuilder.setTitle(R.string.dialog_blank_message_title);
            dialogBuilder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    isMessageCheckPassed = true;
                }
            });
            dialogBuilder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = dialogBuilder.create();
            alertDialog.show();
        }

        sendMessage();
    }

    private void checkRecipients() {
        Log.d(TAG, "checkRecipients: Started.");
        if(!cbAllRecipients.isChecked()){
            //getRecipientsCount
        }else{
          isRecipientCheckPassed = true;
        }
        sendMessage();
    }

    private void sendMessage() {
        if(isMessageCheckPassed && isRecipientCheckPassed){
            //to send message
            showToast("To send Text");
        }
    }

    private void selectRecipients() {
        showToast("To select Recipients");
    }

    public void showToast(String toastMessage){
        Toast.makeText(getApplicationContext(), toastMessage,Toast.LENGTH_LONG).show();
    }
}