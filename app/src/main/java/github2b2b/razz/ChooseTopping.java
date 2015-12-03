package github2b2b.razz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class ChooseTopping extends AppCompatActivity {

    TextView tp;

    /**
     *  @param tags - holds the tags for the buttons that were clicked on current page
     */
    ArrayList<Integer> tags = new ArrayList<>();

    /** @author -  Shane Demskie, 10/09
     * When a view is passed  ( when clicked ), we get the tag of the object ( an integer ) which
     * corresponds to the location in the ingredientManager list and instantiate the matching
     * locations object. We then check if the toggleButton is clicked for the view passed in, if it
     * is not, we add the ingredient to the current sandwich and add the views tag  to the tags
     * arraylist, if it is, we remove the item and remove the views tag from the tags arraylist.
     *
     * @param v - Takes in a view as a parameter.
     */
    public void onClickTopping(View v){
        Integer curToppingTag = Integer.parseInt(v.getTag().toString());                            //Look at the tag of the item and find the corresponding tag in ingredient manager. **Ask Shane
        Topping curTopping = IngredientManager.toppingList.get(curToppingTag);                      //Create object
        if(((ToggleButton) v).isChecked()){
            //on
            tags.add(curToppingTag);
            Cart.addIngredToCur(curTopping);                                                        //Add current ingredient (Button is highlighted)
        }else{
            //off
            tags.remove(curToppingTag);
            Cart.removeIngredFromCur(curTopping);                                                   //Remove current ingredient (Button is not highlighted anymore)
        }
    }

    public void updateItemPrice(){
        String itemPriceString = Cart.currencyFormat.format(Cart.chosenItems.get(0).itemPrice);
        tp = (TextView) findViewById(R.id.itemPrice);  //Set tp to the text view textPrice
        tp.setText(itemPriceString);
    }

    /** @author -  Shane Demskie, 10/09
     * When a view is passed  ( when clicked ), we proceed to the next activity ( ContinueOrFinish )
     * using intent
     *
     * @param v - Takes in a view as a parameter.
     */
    public void onClickCompleteSandwich(View v){
        Intent intent = new Intent(this, ContinueOrFinish.class);
        startActivity(intent);
    }

    /** @author -  Shane Demskie, 10/09
     * When a view is passed  ( when clicked ), we remove all sandwich ingredients from the current
     * page using the tags list, and revert to the last activity.
     *
     * @param v - Takes in a view as a parameter.
     */
    public void backToPrev(View v){
        if(tags.size()!=0) {
            for (int i = 0; i < tags.size(); i++) {
                Topping curToppimg = IngredientManager.toppingList.get(tags.get(i));                //wipe all cheese ingredients from cur sandwich
                Cart.removeIngredFromCur(curToppimg);
            }
        }
        super.onBackPressed();
    }

    /** @author -  Shane Demskie, 10/09
     * When a view is passed  ( when clicked ), we call static method cancelOrder which wipes the
     * Cart. We then return to the start screen using intent
     *
     * @param v - Takes in a view as a parameter.
     */
    public void exitToStart(View v){
        Cart.cancelOrder();                     //Clear the cart and the current sandwich if applicable
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_topping);
        if(tags.size()!=0){
            tags.clear();
        }
        Intent intent = getIntent();

        updateItemPrice();
    }
}
