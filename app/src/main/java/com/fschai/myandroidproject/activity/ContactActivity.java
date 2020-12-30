package com.fschai.myandroidproject.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.fschai.myandroidproject.R;
import com.fschai.myandroidproject.model.Contact;

import java.util.UUID;

public class ContactActivity extends AppCompatActivity {
    private EditText etId, etFirstName, etLastName, etPhone, etEmail;
    private String mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etId = (EditText) findViewById(R.id.etId);
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etEmail = (EditText) findViewById(R.id.etEmail);

        if (getIntent().getExtras() != null) {
            mode = getIntent().getExtras().getString("mode");
        }
        Log.d("TAG", mode);
        if (mode.equals("add")) {
            etId.setText(UUID.randomUUID().toString());
        } else {
            Contact contact = (Contact) getIntent().getExtras().getSerializable("contact");
            etId.setText(contact.getId());
            etFirstName.setText(contact.getFirstName());
            etLastName.setText(contact.getLastName());
            etPhone.setText(contact.getPhone());
            etEmail.setText(contact.getEmail());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_save:
                Contact newContact = new Contact(
                        etId.getText().toString(),
                        etFirstName.getText().toString(),
                        etLastName.getText().toString(),
                        etEmail.getText().toString(),
                        etPhone.getText().toString()
                );
                if (mode.equals("add")) {
                    MainActivity.contactCollection.addContact(newContact);
                    MainActivity.dataAdded();
                } else {
                    MainActivity.contactCollection.updateContact(newContact);
                    MainActivity.dataUpdated();
                }
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}