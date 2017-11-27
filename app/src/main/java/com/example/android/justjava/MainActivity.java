package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
            boolean hasWhippedCream = ((CheckBox)findViewById(R.id.checkbox_whippedcream)).isChecked();
            boolean hasChocolate = ((CheckBox) findViewById(R.id.checkbox_chocolete)).isChecked();
            String name =((EditText) findViewById(R.id.name_edit_text)).getText().toString();
            int price = calculatePrice(hasWhippedCream, hasChocolate);
            String message = creatOrderSummery(name, price, hasWhippedCream, hasChocolate);
            String subject = getString(R.string.email_subject, name);
            //displayMessage(message);
            composeEmail(subject, message);



           }

    private void composeEmail(String subject, String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate){
        int priceWhippedCream = 1;
        int priceChocolate = 2;
        int basePrice = 5;

        if (hasWhippedCream)
            basePrice+= priceWhippedCream;
        if (hasChocolate)
            basePrice+= priceChocolate;

        return basePrice * quantity;
    }

    private String creatOrderSummery(String name, int price, boolean hasWhippedCream, boolean hasChocolate){
        String priceMessage = getString(R.string.user_name, name)+"\n";
        priceMessage+= getString(R.string.add_whippedcream) + (hasWhippedCream?getString(R.string.yes):getString(R.string.no)) + "\n";
        priceMessage+= getString(R.string.add_chocolate) + (hasChocolate?getString(R.string.yes):getString(R.string.no)) + "\n";
        priceMessage+= getString(R.string.quantity1) +quantity+ "\n";
        priceMessage+= getString(R.string.sum)+ price + getString(R.string.ft)+"\n";
        priceMessage+= getString(R.string.thank_you);
        return priceMessage;
    }

    /**
     * This method displays the given text on the screen.
     */
//    private void displayMessage(String message) {
//            TextView orderSummeryTextView = (TextView) findViewById(R.id.order_summery_text_view);
//            orderSummeryTextView.setText(message);
//    }
//
//    /**
//     * This method displays the given quantity value on the screen.
//     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    public void increment(View view) {
        if(quantity<100)
        quantity++;
        else
            Toast.makeText(this, "Nem lehet 100 kávénál többet rendelni.", Toast.LENGTH_SHORT).show();
        displayQuantity(quantity);

    }

    public void decrement(View view) {
        if (quantity>1)
        quantity--;
        else
            Toast.makeText(this, "Nem lehet 1 kávénál kevesebbet rendelni.", Toast.LENGTH_SHORT).show();
        displayQuantity(quantity);
    }

}