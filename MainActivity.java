package com.map.distanceconverter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Dialog;
import android.graphics.Color;
//importing widgets for processing the inputs
import android.view.WindowManager;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;
//importing the listeners from the java package in android
import android.text.Editable;
import android.text.TextWatcher;

//importing package from java
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    public static NumberFormat distanceFormat = NumberFormat.getInstance();

    public double meters = 0.0;


    //Variables for assigning the results to variables
    public long kiloResults ;
    public long inchResults;
    public long feetResults;


    public TextView distanceTextView;
    public TextView kiloTextView;
    public TextView inchTextView;
    public TextView feetTextView;

    //codes for the headers
    public TextView feetHeader;
    public TextView kiloHeader;
    public TextView inchHeader;

    //codes for the radio buttons
    public RadioButton distKilo;
    public RadioButton distInch;
    public RadioButton distFeet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Displaying of Toast Message at the Start of the application
        Context context = getApplicationContext();
        CharSequence text = "DISTANCE CONVERTER";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context,text,duration);
        toast.setGravity(Gravity.TOP|Gravity.CENTER,5,5);
        toast.show();

        //using the floating action button to display the content of the information on the calculation
      FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setTitle("Conversion Info And Keys ");
                dialog.setContentView(R.layout.dialogfragment);
                dialog.show();
            }
        });

        //codes for activating the identifications of the radio buttons
        distKilo = (RadioButton) findViewById(R.id.distKilo);
        distInch = (RadioButton) findViewById(R.id.distInches);
        distFeet = (RadioButton) findViewById(R.id.distFeet);


        //codes for the header of the results pane
        kiloHeader = (TextView) findViewById(R.id.kiloHeader);
        inchHeader = (TextView) findViewById(R.id.inchHeader);
        feetHeader = (TextView) findViewById(R.id.feetHeader);

        //codes for alerting the input and the display text with their ID
        distanceTextView = (TextView) findViewById(R.id.distanceTextView);
        kiloTextView = (TextView) findViewById(R.id.kiloTextView);
        inchTextView = (TextView) findViewById(R.id.inchTextView);
        feetTextView = (TextView) findViewById(R.id.feetTextView);

        //initializing the values inside the results text views
        kiloTextView.setText(distanceFormat.format(0));
        inchTextView.setText(distanceFormat.format(0));
        feetTextView.setText(distanceFormat.format(0));

        //codes for the Edittext and its textwatcher
        EditText distanceEditText = (EditText) findViewById(R.id.distanceEditText);
        distanceEditText.addTextChangedListener(distanceEditTextWatcher);
        distanceEditText.setOnFocusChangeListener(editTextFocusListener);

        distKilo.setOnFocusChangeListener(distKiloListener);
        distInch.setOnFocusChangeListener(distKiloListener);
        distFeet.setOnFocusChangeListener(distKiloListener);


    }
    //Hiding the Keyboard on focus change //Hiding the Keyboard on focus change
    View.OnFocusChangeListener editTextFocusListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {
            if (!b){
                hideKeyboard(view);
            }
        }
    };

    View.OnFocusChangeListener distKiloListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {
            if(!b){
                hideKeyboard(view);
            }
        }
    };

    /*protected void onStop(Bundle savedInstanceState){
        super.onStop(savedInstanceState);
        setContentView(R.layout.activity_main);
    }*/

    public void hideKeyboard(View view){
        InputMethodManager inputMethod = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethod.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public void onRadioButton(View view){
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()){
            case R.id.distKilo:
                if(checked){
                    //displaying on the Kilo Pane for displaying the results
                    kiloHeader.setEnabled(true);
                    kiloTextView.setEnabled(true);
                    kiloTextView.setBackgroundColor(ContextCompat.getColor(this, R.color.background));

                    //color codes and the effects for Feet Text view
                    feetTextView.setEnabled(false);
                    feetHeader.setEnabled(false);
                    feetTextView.setBackgroundColor(ContextCompat.getColor(this,R.color.disabled_color));
                    feetTextView.setText(distanceFormat.format(0));
                    //Color codes and the effects for the Inch text view
                    inchHeader.setEnabled(false);
                    inchTextView.setEnabled(false);
                    inchTextView.setBackgroundColor(ContextCompat.getColor(this,R.color.disabled_color));
                    inchTextView.setText(distanceFormat.format(0));

                    showKilometers();


                }
                else{
                    //Enabling the feet text view
                    feetTextView.setEnabled(true);
                    feetHeader.setEnabled(true);
                    feetTextView.setBackgroundColor(ContextCompat.getColor(this, R.color.background));
                    feetTextView.setText(distanceFormat.format(feetResults));

                    //Enabling the inch text view
                    inchTextView.setEnabled(true);
                    inchHeader.setEnabled(true);
                    inchTextView.setBackgroundColor(ContextCompat.getColor(this, R.color.background));
                    feetTextView.setText(distanceFormat.format(inchResults));
                }
                break;
            case R.id.distInches:
                if(checked){
                    //displaying the results on the inch text view based on the radio button
                    inchTextView.setEnabled(true);
                    inchHeader.setEnabled(true);
                    inchTextView.setBackgroundColor(ContextCompat.getColor(this, R.color.background));

                    //disabling the kilometer header and its text view and setting its results to zero
                    kiloHeader.setEnabled(false);
                    kiloTextView.setEnabled(false);
                    kiloTextView.setBackgroundColor(ContextCompat.getColor(this, R.color.disabled_color));
                    kiloTextView.setText(distanceFormat.format(0));

                    //disabling the feet header and its text view and setting the results to zero
                    feetTextView.setEnabled(false);
                    feetHeader.setEnabled(false);
                    feetTextView.setBackgroundColor(ContextCompat.getColor(this, R.color.disabled_color));
                    feetTextView.setText(distanceFormat.format(0));
                    showInches();

                }else{
                    //Enabling the kilo text view and its computation
                    kiloHeader.setEnabled(true);
                    kiloTextView.setEnabled(true);
                    kiloTextView.setBackgroundColor(ContextCompat.getColor(this, R.color.background));
                    kiloTextView.setText(distanceFormat.format(kiloResults));

                    //Enabling the feet text view and its computation
                    feetTextView.setEnabled(true);
                    feetHeader.setEnabled(true);
                    feetTextView.setBackgroundColor(ContextCompat.getColor(this, R.color.background));
                    feetTextView.setText(distanceFormat.format(feetResults));
                }
                break;
            case R.id.distFeet:
                if(checked){
                    //displaying the results on the feet text view based on the radio button
                    feetTextView.setEnabled(true);
                    feetHeader.setEnabled(true);
                    feetTextView.setBackgroundColor(ContextCompat.getColor(this, R.color.background));

                    //disabling the kilo text view and setting the computation to zero
                    kiloHeader.setEnabled(false);
                    kiloTextView.setEnabled(false);
                    kiloTextView.setBackgroundColor(ContextCompat.getColor(this, R.color.disabled_color));
                    kiloTextView.setText(distanceFormat.format(0));

                    //disabling the inch text view and setting its computation to zero
                    inchHeader.setEnabled(false);
                    inchTextView.setEnabled(false);
                    inchTextView.setBackgroundColor(ContextCompat.getColor(this, R.color.disabled_color));
                    inchTextView.setText(distanceFormat.format(0));
                    showFeet();
                }else{
                    //Enabling the kilo text view and its computation
                    kiloHeader.setEnabled(true);
                    kiloTextView.setEnabled(true);
                    kiloTextView.setBackgroundColor(ContextCompat.getColor(this, R.color.background));
                    kiloTextView.setText(distanceFormat.format(kiloResults));

                    //Enabling the inch text view and its computation
                    inchHeader.setEnabled(true);
                    inchTextView.setEnabled(true);
                    inchTextView.setBackgroundColor(ContextCompat.getColor(this, R.color.background));
                    inchTextView.setText(distanceFormat.format(inchResults));
                }
                break;
            default:
                //Enabling all the text views by default7
                kiloTextView.setEnabled(true);
                kiloTextView.setBackgroundColor(ContextCompat.getColor(this,R.color.background));
                inchTextView.setEnabled(true);
                inchTextView.setBackgroundColor(ContextCompat.getColor(this,R.color.background));
                feetTextView.setEnabled(true);
                feetTextView.setBackgroundColor(ContextCompat.getColor(this,R.color.background));
                break;
        }
    }
//Displaying a Toast message for the user when the input pane is active
    public void showMessage(View view){
        Toast.makeText(MainActivity.this, "Enter Distance(m)", Toast.LENGTH_SHORT).show();
    }



    //computation for the feet, inch and the kilometers
    public void showKilometers(){
        kiloTextView.setText(distanceFormat.format(meters));
        kiloResults = (long) (meters * (1/1000));

        kiloTextView.setText(distanceFormat.format(kiloResults));

    }


    public void showInches(){
        inchTextView.setText(distanceFormat.format(meters));
        inchResults = (long) (meters * 36.37);

        inchTextView.setText(distanceFormat.format(inchResults));
    }

    public void showFeet(){
        feetTextView.setText(distanceFormat.format(meters));
        feetResults = (long) (meters * 3.218);

        feetTextView.setText(distanceFormat.format(feetResults));
    }

    public TextWatcher distanceEditTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            try{
                meters = Double.parseDouble(charSequence.toString());
               /* distanceTextView.setText(distanceFormat.format(meters));*/
                if(meters < 0){
                    Toast.makeText(MainActivity.this," Cannot Contain Negative Number",Toast.LENGTH_SHORT).show();
                }

            }catch(NumberFormatException e){
                distanceTextView.setText("");
                meters = 0.0;
            }

            //alternative codes for the radio buttons
            if(distKilo.isChecked()){
                showKilometers();
            }
            else if(distInch.isChecked()){
                showInches();
            }
            else if(distFeet.isChecked()){
                showFeet();
            }
            else if(!distFeet.isChecked() && !distInch.isChecked() && !distKilo.isChecked()){
                Toast.makeText(MainActivity.this,"No Conversion Button Is Selected",Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };


   ///METHOD TO QUIT THE APPLICATION
    public void AppExit(){
        this.finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            AppExit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
