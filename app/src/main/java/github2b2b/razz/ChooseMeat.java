package github2b2b.razz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class ChooseMeat extends AppCompatActivity {

    int meatCounter = 0;
    TextView tp;

    /** @author -  Shane Demskie, 10/09
     * When a view is passed  ( when clicked ), we get the tag of the object ( an integer ) which
     * corresponds to the location in the ingredientManager list and instantiate the matching
     * locations object. We then check if the toggleButton is clicked for the view passed in, if it
     * is not, we add the ingredient to the current sandwich, if it is, we remove the item.
     *
     * @param v - Takes in a view as a parameter.
     */
    public void onClickMeat(View v){

        int curMeatTag = Integer.parseInt(v.getTag().toString());                                   //Look at the tag of the item and find the corresponding tag in ingredient manager. **Ask Shane
        Meat curMeat = IngredientManager.meatList.get(curMeatTag);                                  //Create object
        String curString = (String) ((ToggleButton) v).getText();


        if(((ToggleButton) v).isChecked()){
            //on
            Cart.addIngredToCur(curMeat);                                                           //Add current ingredient (Button is highlighted)

            if (meatCounter == 0 && Cart.chosenItems.get(0).meatBool == false){
                Cart.chosenItems.get(0).meatBool = true;
                Toast.makeText(getApplicationContext(), "First meat is free, additional ones cost $1.50", Toast.LENGTH_LONG).show();
            }

            if (meatCounter != 0){
                Cart.chosenItems.get(0).itemPrice = Cart.chosenItems.get(0).itemPrice + 1.50;
                updateItemPrice();
            }

            meatCounter++;
        }else{
            //off
            Cart.removeIngredFromCur(curMeat);                                                      //Remove current ingredient (Button is not highlighted anymore)
            if (meatCounter != 1){
                Cart.chosenItems.get(0).itemPrice = Cart.chosenItems.get(0).itemPrice - 1.50;
                updateItemPrice();
            }
            meatCounter--;
        }

    }

    public void updateItemPrice(){
        String itemPriceString = Cart.currencyFormat.format(Cart.chosenItems.get(0).itemPrice);
        tp = (TextView) findViewById(R.id.itemPrice);  //Set tp to the text view textPrice
        tp.setText(itemPriceString);
    }

    /** @author -  Shane Demskie, 10/09
     * When a view is passed  ( when clicked ), we proceed to the next activity ( ChooseCheese )
     * using intent
     *
     * @param v - Takes in a view as a parameter.
     */
    public void nextPage(View v){

        Intent intent = new Intent(this, ChooseCheese.class);
        startActivity(intent);
    }

    /** @author -  Shane Demskie, 10/09
     * When a view is passed  ( when clicked ), we remove all ingredients from the current sandwich,
     * decrement items in cart and revert to the last activity.
     *
     * @param v - Takes in a view as a parameter.
     */
    public void backToPrev(View v){

        Cart.chosenItems.get(Cart.itemsInCart-1).removeAllIngredients();                            //wipe all ingredients.
        Log.i("Log itemincart XXX", String.valueOf(Cart.itemsInCart));                                //print # of ingred in cart
        Cart.itemsInCart = (Cart.itemsInCart-1);
        Log.i("Log itemincart XXX", String.valueOf(Cart.itemsInCart));                                //print # of ingred in cart
        super.onBackPressed();
    }



    /** @author -  Shane Demskie, 10/09
     * When a view is passed  ( when clicked ), we call static method cancelOrder which wipes the
     * Cart. We then return to the start screen using intent
     *
     * @param v - Takes in a view as a parameter.
     */
    public void exitToStart(View v){
        Cart.cancelOrder();                                                                         //Clear the cart and the current sandwich if applicable

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_meat);

        Intent intent = getIntent();

        updateItemPrice();
    }
}
