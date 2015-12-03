package github2b2b.razz;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * Created by Katipo on 10/4/2015.
 */
public class ContinueOrFinish extends AppCompatActivity {

    private ListView lv;                //declare listview object to populate listview
    private TextView tp; //Declare TextView object to for Total Price

    public double totalPrice = 0;

    public void toExitScreen(View v){
        Intent intent = new Intent(this, OrderExit.class);
        startActivity(intent);
    }


    public void onClickAddMore(View v){
        Intent intent = new Intent(this, ChooseItemType.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continue_or_finish);
        Intent intent = getIntent();
        populateListView();
    }



    public void populateListView(){
        lv = (ListView) findViewById(R.id.lvDisplayCart);                   //Create listview object and link it with listView in .xml
        final ArrayList<String> curString = new ArrayList<>();               //Array list full of string representations of all of Carts contents
        for(int i = 0; i < Cart.itemsInCart; i++) {                              //Loop through the Cart
            ArrayList<Ingredient> curCartItem = Cart.chosenItems.get(i).ingredientList;     //fill tempArrayList with current itteration cart contents
            String sandwich = "";                                                           //String to hold contents of Cart item current on

            for (int j = 0; j < curCartItem.size(); j++) {                                  //loop to add contents to above string
                if (j == 0) {
                    sandwich += curCartItem.get(j).getIngredientName();
                } else {
                    sandwich += ", " + curCartItem.get(j).getIngredientName();
                }
            }
            String itemPriceString = Cart.currencyFormat.format(Cart.chosenItems.get(i).itemPrice);  //Set the string itemPriceString to be the itemPrice, formatted as the US Dollar
            sandwich += " - " + itemPriceString;    //Add this to the sandwich string

            curString.add(sandwich);                                                        //Add completed string to curString

            totalPrice = totalPrice + Cart.chosenItems.get(i).itemPrice; //Add this price of the item to the totalPrice
        }

        final myOwnArrayAdapter adapter = new myOwnArrayAdapter(this,                                 //Put in your list of strings here
                android.R.layout.simple_list_item_1, curString);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String clickedField = (String) parent.getItemAtPosition(position);
                int toBeErasedIndex = curString.indexOf(clickedField);                          //get index of button clicked
                totalPrice = totalPrice - Cart.chosenItems.get(toBeErasedIndex).itemPrice;  //Decrement the total price variable
                String totalPriceString = Cart.currencyFormat.format(totalPrice);  //Set the totalPriceString to totalPrice formatted as US currency
                tp = (TextView) findViewById(R.id.textPrice);  //Set tp to the text view textPrice
                tp.setText(totalPriceString);  //Change the text view for

                view.animate().setDuration(1000).alpha(0)                                               //fade out animation
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                int toBeErasedIndex = curString.indexOf(clickedField);                          //get index of button clicked
                                Cart.chosenItems.remove(toBeErasedIndex);                               //remove that from the cart
                                Cart.itemsInCart = (Cart.itemsInCart - 1);                                //decrement itemsInCart counter
                                curString.remove(clickedField);                                                 //Remove item clicked from display list
                                adapter.notifyDataSetChanged();                                            //Tell list item was removed
                                view.setAlpha(1);
                            }
                        });
            }

        });

        String totalPriceString = Cart.currencyFormat.format(totalPrice);
        tp = (TextView) findViewById(R.id.textPrice);
        tp.setText(totalPriceString);
    }

    private class myOwnArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> IdMap = new HashMap<String, Integer>();

        public myOwnArrayAdapter(Context context, int textViewResourceId,
                                 List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                IdMap.put(objects.get(i), i);
            }
        }



        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return IdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }



}


