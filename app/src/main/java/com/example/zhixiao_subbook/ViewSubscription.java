package com.example.zhixiao_subbook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author Helen
 * @version 1.5
 * @see zhixiao_SubBook
 */
public class ViewSubscription extends AppCompatActivity {

    private EditText nameEdit;
    private EditText dateEdit;
    private EditText feeEdit;
    private EditText commentsEdit;
    private String pos;

    /**
     * Called when activity is first created.
     *
     * @param savedInstanceState The state of saved instance.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sub);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String subName = intent.getStringExtra("NAME");
        String subDate = intent.getStringExtra("DATE");
        String subFee = intent.getStringExtra("FEE");
        String subComment = intent.getStringExtra("COMMENTS");

        pos = intent.getStringExtra("POS");

        nameEdit = findViewById(R.id.name);
        dateEdit = findViewById(R.id.date);
        feeEdit = findViewById(R.id.fee);
        commentsEdit = findViewById(R.id.comments);
        Button saveButton = findViewById(R.id.save);

        nameEdit.setText(subName);
        dateEdit.setText(subDate);
        feeEdit.setText(subFee);
        commentsEdit.setText(subComment);

        saveButton.setOnClickListener(new View.OnClickListener() {

            /**
             * Saves possible edits on click.
             *
             * @param view Current view.
             */
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
                String newName = nameEdit.getText().toString();
                String newDate = dateEdit.getText().toString();
                String newFee = feeEdit.getText().toString();
                String newComment = commentsEdit.getText().toString();

                if (newName.length() == 0) {
                    Context context = getApplicationContext();
                    CharSequence text = "Name must be not be left blank";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                else if (newName.length() > 20) {
                    Context context = getApplicationContext();
                    CharSequence text = "Name must be less than 20 characters";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

                else if (newDate.length() == 0) {
                    Context context = getApplicationContext();
                    CharSequence text = "Date must not be left blank";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

                else if (newFee.length() == 0) {
                    Context context = getApplicationContext();
                    CharSequence text = "Monthly Charges must not be left blank";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

                else if (Float.valueOf(newFee) < 0) {
                    Context context = getApplicationContext();
                    CharSequence text = "Invalid value entered for Monthly Charges";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

                else if (newComment.length() > 30) {
                    Context context = getApplicationContext();
                    CharSequence text = "Comments must be less than 30 characters";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

                else {
                    Intent returnIntent = new Intent();
                    //returnIntent.putExtra("NAME", newName);
                    returnIntent.putExtra("POSITION", pos);
                    returnIntent.putExtra("newFEE",newFee);
                    returnIntent.putExtra("newDATE",newDate);
                    returnIntent.putExtra("newCOMMENT", newComment);
                    returnIntent.putExtra("newNAME",newName);
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }
            }
        });
    }
}
