/*
Mobile App Development I -- COMP.4630 Honor Statement
The practice of good ethical behavior is essential for
maintaining good order in the classroom, providing an
enriching learning experience for students, and training as
a practicing computing professional upon graduation. This
practice is manifested in the University's Academic
Integrity policy. Students are expected to strictly avoid
academic dishonesty and adhere to the Academic Integrity
policy as outlined in the course catalog. Violations will
be dealt with as outlined therein. All programming
assignments in this class are to be done by the student
alone unless otherwise specified. No outside help is
permitted except the instructor and approved tutors.
I certify that the work submitted with this assignment is
mine and was generated in a manner consistent with this
document, the course academic policy on the course website
on Blackboard, and the UMass Lowell academic code.
Date:John Ersen Youte
Name:October 3/2022
*/

/***********************************************
 Author: <John Ersen Youte>
 Date: <Oct 16>
 Purpose: <The purpose of this assignment is to know how to work with landscape rotation.>
 Sources of Help: <Android Doc and stackOverflow>
 Time Spent: <about 3 to 4 Hours>
 ***********************************************/


package com.example.pizzaorder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.BounceInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    //Here I declared the variable used in that program.
    private EditText user_prompt_editText;
    private RadioGroup radioGroup_for_hungerLevel;
    private Button submit_order_button;
    private TextView textView_for_guestCount;
    private TextView textView_hungerType;
    private TextView textView_for_topping;
    private TextView textView_for_amountOfPizzaNeeded;
    static List<String> listHunger;
    static List<String> list_of_toppings;
    private RadioButton RadioButton_for_hungerType;
    private int guestAmountText;
    private String guestHungertype;
    private ImageView imageView_for_transition;
    private TextView textView_for_transition;
    private ConstraintLayout layout;
    TextView textView_Error_RadioButton;
    private CheckBox checkBox_chicken;
    private CheckBox checkBox_cheese;
    private CheckBox checkBox_meat;
    private String textView_for_guestCount_for_saveInstance;
    private String textView_hungerType_for_saveInstance;
    private String textView_for_topping_for_saveInstance;
    private String textView_for_amountOfPizzaNeeded_for_saveInstance;
    private int selectedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getSupportActionBar().hide(); // hide the title bar
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        //Initialized These variable above
        user_prompt_editText = findViewById(R.id.editUserPromp);
        radioGroup_for_hungerLevel = findViewById(R.id.radioGroup);
        submit_order_button = findViewById(R.id.SubmitOrderButton);
        textView_for_guestCount = findViewById(R.id.textGuestCount);
        textView_hungerType = findViewById(R.id.textHungerTypeNumber);
        textView_for_topping = findViewById(R.id.textHungerToppingNumber);
        textView_for_amountOfPizzaNeeded = findViewById(R.id.textPizzaNeededNumber);
        imageView_for_transition = findViewById(R.id.Transition);
        textView_for_transition = findViewById(R.id.Thankyou);
        layout = findViewById(R.id.layout);
        listHunger = Arrays.asList(getResources().getStringArray(R.array.hungerLevel));
        list_of_toppings = Arrays.asList(getResources().getStringArray(R.array.chosen_Topping));
        textView_Error_RadioButton = findViewById(R.id.textHungerLevel);


        //restoring the data from the saved instances and i added some logic
        if (savedInstanceState != null) {
            textView_for_guestCount.setText(savedInstanceState.getString("textView_for_guestCount"));
            textView_hungerType.setText(savedInstanceState.getString("textView_hungerType"));
            textView_for_topping.setText(savedInstanceState.getString("textView_for_topping"));
            textView_for_amountOfPizzaNeeded.setText(savedInstanceState.getString("textView_for_amountOfPizzaNeeded"));
            radioGroup_for_hungerLevel.check(savedInstanceState.getInt("selected_Radio_button_id"));
        }


        //This (add Text Changed Listener) basically a textWatcher, To see when the text is being changed.
        //so this way I set the appropriate value to a textview
        user_prompt_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textView_for_guestCount.setText(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        //listener for the Radio group buttons, it checks for the button ID, and pass the value to a variable.
        radioGroup_for_hungerLevel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                selectedId = radioGroup_for_hungerLevel.getCheckedRadioButtonId();

                RadioButton_for_hungerType = findViewById(selectedId);
                textView_hungerType.setText(RadioButton_for_hungerType.getText().toString());
                guestHungertype = RadioButton_for_hungerType.getText().toString();
                if (user_prompt_editText.getText().toString().trim().length() <= 0) {
                    guestAmountText = 1;
                    user_prompt_editText.setError(getText(R.string.error2));
                } else if (user_prompt_editText.getText().toString().trim().length() > 0) {
                    guestAmountText = new Integer(user_prompt_editText.getText().toString()).intValue();
                    textView_for_amountOfPizzaNeeded.setText(pizzaCal(guestAmountText, guestHungertype) + "");
                }
            }
        });


        //Button listener, that makes sure that the button is click, and when it gets clicked to do some tasks.
        submit_order_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //This statement is validating user's impute to see if the field are filled out or not.
                //if not the user will get new hint on what to do.
                if (user_prompt_editText.getText().toString().trim().length() <= 0 || radioGroup_for_hungerLevel.getCheckedRadioButtonId() == -1) {
                    user_prompt_editText.setHint(R.string.error2);
                    user_prompt_editText.setHintTextColor(Color.RED);
                    textView_Error_RadioButton.setText(R.string.Selectionerroe);
                    textView_Error_RadioButton.setTextColor(Color.RED);


                } else {
                    //this has a simple animation for the Confirmation order.
                    submit_order_button.setVisibility(View.INVISIBLE);
                    //this is an animation for the user to see at the end of an order
                    ObjectAnimator moveAnim = ObjectAnimator.ofFloat(textView_for_transition, "Y", 500);
                    moveAnim.setDuration(2000);
                    moveAnim.setInterpolator(new BounceInterpolator());
                    moveAnim.start();
                    // Create a snack, and has a button to start a new order.
                    Snackbar snackbar = Snackbar.make(layout, R.string.pizzaConfirmation, Snackbar.LENGTH_INDEFINITE).setAction(
                            R.string.newOrder, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    finish();
                                    startActivity(getIntent());
                                    overridePendingTransition(3000, 9000);
                                    String time = System.currentTimeMillis() + "";
                                }
                            });
                    submit_order_button.setVisibility(View.INVISIBLE);
                    imageView_for_transition.setVisibility(View.VISIBLE);
                    textView_for_transition.setVisibility(View.VISIBLE);
                    snackbar.show();
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        //TODO: save the values for UI components
        //super.onSaveInstanceState(savedInstanceState);
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("textView_for_guestCount", textView_for_guestCount.getText().toString());
        savedInstanceState.putString("textView_hungerType", textView_hungerType.getText().toString());
        savedInstanceState.putString("textView_for_topping", textView_for_topping.getText().toString());
        savedInstanceState.putString("textView_for_amountOfPizzaNeeded", textView_for_amountOfPizzaNeeded.getText().toString());
        savedInstanceState.putInt("selected_Radio_button_id", selectedId);
    }

    //method with 4 if statement, verifying the user input inside of the spinner
    //the method takes two parameters, guest amount and hunger type
    //it has also some local variable.
    //The Logic is take the amount of guest and multiply it by the the number chose inside of the spinner
    //(light=2, medium=3, ravenous=4), and it will devide it by pizzaSlices=12, and check if there rest, it will make it equal with
    //one whole pizza. bc the pizzeria does not sell slices.
    protected int pizzaCal(int guestAmount, String hungerType) {
        final int pizzaSlices = 12;
        int multiplier = 0;
        int devid = 0;
        int pizzaAmount = 0;

        if (hungerType.equalsIgnoreCase(listHunger.get(1))) {
            multiplier = 2 * guestAmount;
            devid = multiplier % pizzaSlices;
            if (devid == 0) {
                pizzaAmount = multiplier / pizzaSlices;
            } else {
                pizzaAmount = (multiplier / pizzaSlices) + 1;
            }
        } else if (hungerType.equalsIgnoreCase(listHunger.get(2))) {
            multiplier = 3 * guestAmount;
            devid = multiplier % pizzaSlices;
            if (devid == 0) {
                pizzaAmount = multiplier / pizzaSlices;
            } else {
                pizzaAmount = (multiplier / pizzaSlices) + 1;
            }
        } else if (hungerType.equalsIgnoreCase(listHunger.get(3))) {
            multiplier = 4 * guestAmount;
            devid = multiplier % pizzaSlices;
            if (devid == 0) {
                pizzaAmount = multiplier / pizzaSlices;
            } else {
                pizzaAmount = (multiplier / pizzaSlices) + 1;
            }
        }
        return pizzaAmount;
    }

    //This method works with the Checkboxes, I had to change my code last minute. it might be messy.
    public void onCheckboxClicked(View view) {
        checkBox_chicken = findViewById(R.id.checkbox_chicken);
        checkBox_cheese = findViewById(R.id.checkbox_cheese);
        checkBox_meat = findViewById(R.id.checkbox_meat);
        String msg = "";
        if (checkBox_meat.isChecked())
            msg = msg + list_of_toppings.get(2);
        if (checkBox_chicken.isChecked())
            msg = msg + " " + list_of_toppings.get(0);
        if (checkBox_cheese.isChecked())
            msg = msg + " " + list_of_toppings.get(1);
        if (msg.isEmpty()) {
            textView_for_topping.setTextColor(Color.RED);
            textView_for_topping.setText(R.string.errorTopping);
        } else {
            textView_for_topping.setText(msg);
            textView_for_topping.setTextColor(Color.CYAN);
        }
    }
}
