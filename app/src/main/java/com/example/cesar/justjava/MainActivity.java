package com.example.cesar.justjava;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void increment(View view){
        if (quantity == 100){
            Toast.makeText(this, "you cannot have more  than 100 coffees",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity ++;
        displayQuantity(quantity);
    }

    public void decrement(View view){
        if (quantity == 1){
            Toast.makeText(this, "you cannot have less  than  1 coffee",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity--;
        displayQuantity(quantity);
    }

    public void submitOrder(View view) {
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();
        Log.v("MainActivity", "Name: "+name);

        CheckBox whippedCreamCheckBox = (CheckBox)findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox)findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSumary(name, price, hasWhippedCream, hasChocolate);

        Intent intent =  new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(intent.EXTRA_SUBJECT,"Just Java order for "+name);
        intent.putExtra(intent.EXTRA_TEXT,priceMessage);
        if (intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);
        }


    }

    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int basePrice = 5;

        if (addWhippedCream){
            basePrice = basePrice+1;
        }

        if (addChocolate){
            basePrice = basePrice + 2;
        }
        return quantity * basePrice;
    }

    private void displayQuantity(int cantidad) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + cantidad);
    }

     private String createOrderSumary(String name, int price ,boolean addWhippedCream, boolean addChocolate){
        String priceMessage = "Name: "+ name;
         priceMessage += "\nAdd Whipped cream? "+addWhippedCream;
         priceMessage += "\nAdd Chocolate? "+ addChocolate;
         priceMessage += "\nQuantity "+ quantity;
         priceMessage += "\nTotal: $"+price;
         priceMessage += "\nThank you!";
        return priceMessage;
    }

}
