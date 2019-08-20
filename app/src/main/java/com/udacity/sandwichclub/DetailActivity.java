package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;


import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView mAlsoKnownAs;
    private TextView mplaceOfOrigin;
    private TextView mdescription;
    private TextView mIngredients;
    private TextView mAlsoKnownAsBrown;
    private TextView mplaceOfOriginBrown;
    private TextView mdescriptionBrown;
    private TextView mIngredientsBrown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mAlsoKnownAs = (TextView) findViewById(R.id.also_known_tv);
        mplaceOfOrigin = (TextView) findViewById(R.id.origin_tv);
        mdescription = (TextView) findViewById(R.id.description_tv);
        mIngredients = (TextView) findViewById(R.id.ingredients_tv);
        mAlsoKnownAsBrown = (TextView) findViewById(R.id.also_known_as_brown);
        mplaceOfOriginBrown = (TextView) findViewById(R.id.place_of_origin_brown);
        mdescriptionBrown = (TextView) findViewById(R.id.description_brown);
        mIngredientsBrown = (TextView) findViewById(R.id.ingredients_brown);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        setplaceOfOrigin(sandwich.getPlaceOfOrigin());
        setdescription(sandwich.getDescription());
        setAlsoKnownAs(sandwich.getAlsoKnownAs());
        setingredients(sandwich.getIngredients());

    }

    private void setdescription(String description){
        if (!description.equals("")) {
            mdescription.setText(description);
        }
        else {
            mdescription.setVisibility(View.GONE);
            mdescriptionBrown.setVisibility(View.GONE);
        }
    }

    private void setplaceOfOrigin(String placeOfOrigin){

        if (!placeOfOrigin.equals("")) {
            mplaceOfOrigin.setText(placeOfOrigin);
        }
        else {
            mplaceOfOrigin.setVisibility(View.GONE);
            mplaceOfOriginBrown.setVisibility(View.GONE);
        }
    }

    private void setAlsoKnownAs( List<String> alsoKnownAs) {

        if(alsoKnownAs.size() != 0) {

            for (int i = 0; i < alsoKnownAs.size(); i++) {

                mAlsoKnownAs.append(alsoKnownAs.get(i) + "\n");
            }
        }
        else {
            mAlsoKnownAsBrown.setVisibility(View.GONE);
            mAlsoKnownAs.setVisibility(View.GONE);
        }
    }

    private void setingredients(List<String> ingredients){

        if(ingredients.size() != 0) {

            for (int i = 0; i < ingredients.size(); i++) {

                mIngredients.append(ingredients.get(i) + "\n");
            }
        }
        else {
            mIngredients.setVisibility(View.GONE);
            mIngredientsBrown.setVisibility(View.GONE);
        }
    }
}
