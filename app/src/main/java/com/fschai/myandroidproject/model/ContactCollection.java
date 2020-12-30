package com.fschai.myandroidproject.model;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ContactCollection {
    private List<Contact> contactList = new ArrayList<>();

    public ContactCollection() {
    }

    public void setContactListFromJSON(Context context) {
        try {
            InputStream inputStream = context.getAssets().open("data.json");
            int size = inputStream.available();

            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            // Convert the buffer into a string.
            String localJsonString = new String(buffer);
            JSONArray jsonArray = new JSONArray(localJsonString);

            this.contactList.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                Contact contact = new Contact();
                contact.parse(jsonObject);
                contactList.add(contact);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void addContact(Contact contact) {
        this.contactList.add(contact);
    }

    public void updateContact(Contact newContact) {
        for (int i = 0; i < this.contactList.size(); i++) {
            if (this.contactList.get(i).getId().equals(newContact.getId())) {
                this.contactList.set(i, newContact);
                break;
            }
        }
    }

    public List<Contact> getContactList() {
        return contactList;
    }

    public void setContactList(List<Contact> contactList) {
        this.contactList = contactList;
    }
}
