/*
 * Helen_Subscription_Book (zhixiao_SubBook)
 *
 * Version 1.0
 *
 * February 4, 2018
 *
 * Copyright (c) 2018 Helen Zhao, CMPUT301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the licence in the project. Otherwise please contact contact@abc.ca.
 *
 */

package com.example.zhixiao_subbook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * @author Helen
 * @version 1.0
 * @see Subscription
 * @see NewSub
 * @see ViewSubscription
 */
public class zhixiao_SubBook extends AppCompatActivity {

    private static final String FILENAME = "sub.sav";
    private zhixiao_SubBook activity = this;

    private EditText nameText;
    private EditText dateText;
    private EditText feeText;
    private EditText commentText;
    private ListView oldSubscriptionList;
    private TextView oldSubscriptionTitle;

    public ArrayList<Subscription> subList;
    public ArrayAdapter<Subscription> adapter;

    private int selectedPosition;

    /**
     * Called when activity is first created.
     *
     * @param savedInstanceState The state of saved instance.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("LifeCycle ---->", "onCreate is called");
        setContentView(R.layout.activity_main);

        nameText = findViewById(R.id.Name);
        dateText = findViewById(R.id.Date);
        feeText = findViewById(R.id.Fee);
        commentText = findViewById(R.id.Comments);
        Button addButton = findViewById(R.id.Add);
        oldSubscriptionList = findViewById(R.id.oldSubscriptionList);
        Button deleteButton = findViewById(R.id.Delete);
        oldSubscriptionTitle = findViewById(R.id.oldSubscriptionTitle);

        registerForContextMenu(oldSubscriptionList);

        addButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                String subName = nameText.getText().toString();
                String subDate = dateText.getText().toString();
                String subFee = feeText.getText().toString();
                String subComment = commentText.getText().toString();

                if (subName.length() == 0) {
                    Context context = getApplicationContext();
                    CharSequence text = "Name must be not be left blank";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                else if (subName.length() > 20) {
                    Context context = getApplicationContext();
                    CharSequence text = "Name must be less than 20 characters";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

                else if (subDate.length() == 0) {
                    Context context = getApplicationContext();
                    CharSequence text = "Date must not be left blank";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

                else if (subFee.length() == 0) {
                    Context context = getApplicationContext();
                    CharSequence text = "Monthly Charges must not be left blank";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

                else if (Float.valueOf(subFee) < 0) {
                    Context context = getApplicationContext();
                    CharSequence text = "Invalid value entered for Monthly Charges";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

                else if (subComment.length() > 30) {
                    Context context = getApplicationContext();
                    CharSequence text = "Comments must be less than 30 characters";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

                else {
                    Subscription subscription = new NewSub(subName, subDate, subFee, subComment);
                    subList.add(subscription);

                    nameText.setText("");
                    dateText.setText("");
                    feeText.setText("");
                    commentText.setText("");

                    adapter.notifyDataSetChanged();

                    saveInFile();
                }
            }
        });

        oldSubscriptionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            /**
             * Selects item on click.
             *
             * @param parent Parent of view.
             * @param view Screen view.
             * @param position Position of click.
             * @param id ID of click.
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);
                selectedPosition = position;
            }
        });

        oldSubscriptionList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(activity, ViewSubscription.class);
                Subscription sub = subList.get(position);

                String subName = sub.getName();
                String subDate = sub.getDate();
                String subFee = sub.getFee();
                String subComment = sub.getComment();
                String pos = String.valueOf(position);

                intent.putExtra("NAME", subName);
                intent.putExtra("DATE", subDate);
                intent.putExtra("FEE", subFee);
                intent.putExtra("COMMENTS", subComment);
                intent.putExtra("POS", pos);

                startActivityForResult(intent, 1);
                return false;
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {

            /**
             * Deletes item when delete button is clicked.
             *
             * @param v Current view.
             */
            public void onClick(View v) {
                setResult(RESULT_OK);

                if (subList.isEmpty()) {
                    Context context = getApplicationContext();
                    CharSequence text = "Subscription list is empty";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

                else {
                    subList.remove(selectedPosition);

                    oldSubscriptionList.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    saveInFile();
                }
            }
        });
    }

    /**
     * Receives possible edits made on Subscriptions.
     *
     * @param requestCode The request code for activity.
     * @param resultCode The result code for activity.
     * @param data Data passed over from activity.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                String newName = data.getStringExtra("newNAME");
                String position = data.getStringExtra("POSITION");
                String newFee = data.getStringExtra("newFEE");
                String newDate = data.getStringExtra("newDATE");
                String newComment = data.getStringExtra("newCOMMENT");
                int pos = Integer.valueOf(position);

                Subscription sub = subList.get(pos);

                sub.setFee(newFee);
                sub.setDate(newDate);
                sub.setName(newName);
                sub.setComment(newComment);

                saveInFile();
            }
        }
    }

    /**
     * Starts activity.
     */
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        Log.i("LifeCycle --->", "onStart is called");

        loadFromFile();
        adapter = new ArrayAdapter<> (this,
                R.layout.activity_list_sub, subList);

        oldSubscriptionList.setAdapter(adapter);
    }

    /**
     * Loads activity.
     */
    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            Type listType = new TypeToken<ArrayList<Subscription>>(){}.getType();
            subList = gson.fromJson(in, listType);

            float total = 0;
            int i;
            for (i=0; i<subList.size(); i++) {
                total = total + Float.valueOf(subList.get(i).getFee());
            }

            String charge = String.format(java.util.Locale.US,"%.2f", total);
            String title = "Total monthly charges: $"+charge;
            oldSubscriptionTitle.setText(title);

        } catch (FileNotFoundException e) {
            subList = new ArrayList<>();
        //} catch (IOException e) {
            //throw new RuntimeException();
        }
    }

    /**
     * Saves activity.
     */
    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(subList, out);
            out.flush();

            float total = 0;
            int i;
            for (i=0; i<subList.size(); i++) {
                total = total + Float.valueOf(subList.get(i).getFee());
            }

            String charge = String.format(java.util.Locale.US,"%.2f", total);
            String title = "Total monthly charges: $"+charge;
            oldSubscriptionTitle.setText(title);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }

    /**
     * Destroys the world. Just kidding.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Lifecycle", "onDestroy is called");
    }
}
