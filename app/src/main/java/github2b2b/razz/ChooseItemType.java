package github2b2b.razz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ChooseItemType extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_item_type);
    }


    public void sandwichButtonClick(View v)
    {
        Intent intent = new Intent(this, ChooseBread.class);
        startActivity(intent);
    }

    public void saladButtonClick(View v)
    {
        Intent intent = new Intent(this, ChooseSalad.class);
        startActivity(intent);
    }

    public void dessertButtonClick(View v)
    {
        Intent intent = new Intent(this, ChooseDessert.class);
        startActivity(intent);
    }
}
