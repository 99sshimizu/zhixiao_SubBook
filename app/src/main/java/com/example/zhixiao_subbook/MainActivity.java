package com.example.zhixiao_subbook;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

public class MainActivity extends AppCompatActivity {

    private static final String FILENAME = "sub.sav";
    private EditText nameText;
    private EditText dateText;
    private EditText feeText;
    private EditText commentText;
    private ListView oldSubscriptionList;
    private TextView oldSubscriptionTitle;

    private ArrayList<Subscription> subList;
    private ArrayAdapter<Subscription> adapter;

    private int selectedPosition;


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

                Subscription subscription = new NewSub(subName, subDate, subFee, subComment);
                subList.add(subscription);
                //totalCharge = totalCharge + Float.valueOf(subFee);
                //oldSubscriptionTitle.setText(String.valueOf(totalCharge));

                nameText.setText("");
                dateText.setText("");
                feeText.setText("");
                commentText.setText("");

                adapter.notifyDataSetChanged();

                saveInFile();

            }
        });

        oldSubscriptionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id) {
                view.setSelected(true);
                selectedPosition = position;


            }
        });


        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                subList.remove(selectedPosition);

                adapter.notifyDataSetChanged();

                saveInFile();
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_list, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.edit_item:
                return true;
            case R.id.view_item:

                return true;
        }
        return false;
    }

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

            String title = "Total monthly charge: $"+String.valueOf(total);
            oldSubscriptionTitle.setText(title);


        } catch (FileNotFoundException e) {
            subList = new ArrayList<>();
        //} catch (IOException e) {
            //throw new RuntimeException();
        }
    }

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

            String title = "Total monthly charge: $"+String.valueOf(total);
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
     * Destroys the world. Bwahahahahahahaha
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Lifecycle", "onDestroy is called");
    }


}
