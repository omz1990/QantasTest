package com.omar.qantastest.Recipes.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.omar.qantastest.Common.network.domain.models.Recipe;
import com.omar.qantastest.Common.ui.BaseFragment;
import com.omar.qantastest.Common.utils.AppClickableSpan;
import com.omar.qantastest.R;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by omz on 23/5/18
 */
public class RecipeDetailsFragment extends BaseFragment {
    
    @BindView(R.id.title) protected TextView title;
    @BindView(R.id.thumbnail) protected ImageView thumbnail;
    @BindView(R.id.ingredients) protected TextView ingredients;
    @BindView(R.id.link) protected TextView link;

    Recipe recipeData;

    private RecipeDetailsFragmentListener listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Keep data saved while screen orientation changes
        setRetainInstance(true);

        // Repopulate previously loaded data to handle not losing data on screen orientation if there is any available
        if (savedInstanceState != null) {
            repopulateData((Recipe) savedInstanceState.getSerializable("recipe"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipes_details, container, false);
        ButterKnife.bind(this, view);

        // Get the data through the bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            recipeData = (Recipe) bundle.getSerializable("recipe");
        }

        /*
            We check for the saved data in the onCreate function which is called before onCreateView and assign the data to a global
            recipeData object. So if it is null, it means no item has been clicked yet. If it has been clicked, simply use that data
            to update the view. Saving data on orientation changed
         */
        if (recipeData == null) {
            title.setText("");
        } else {
            displayData();
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // Initialize the listener
        if (context instanceof RecipeDetailsFragment.RecipeDetailsFragmentListener) {
            listener = (RecipeDetailsFragment.RecipeDetailsFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement RecipeDetailsFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public void repopulateData(Recipe recipeData) {
        this.recipeData = recipeData;
        displayData();
    }

    private void displayData() {
        title.setText(recipeData.getTitle());
        ingredients.setText(recipeData.getIngredients());
        if (!recipeData.getThumbnail().isEmpty()) {
            Glide.with(getActivity())
                    .load(recipeData.getThumbnail())
                    .placeholder(R.drawable.ic_recipe_placeholder)
                    .into(thumbnail);
        } else {
            thumbnail.setVisibility(View.VISIBLE);
        }
        link.setText(recipeData.getHref());
        initLinkSpan();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save currently loaded data into the bundle for reloading
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable("recipe", (Serializable) recipeData);
    }

    private void initLinkSpan() {
        // Take the link text in the TextView and attach a clickable span on it to open our in app web view
        if (link != null) {
            String fullText = link.getText().toString();
            String linkText = recipeData.getHref();
            Spannable span = Spannable.Factory.getInstance().newSpannable(fullText);
            final int color = Color.parseColor("#0000ff");
            AppClickableSpan termsOfServiceSpan = new AppClickableSpan(color) {
                @Override
                public void onClick(View v) {
                    listener.openUrl(recipeData.getHref());

                }
                @Override
                public void updateDrawState(TextPaint ds) {
                    ds.setUnderlineText(false);
                    ds.setColor(color);
                }
            };
            int linkIndex = fullText.indexOf(linkText);
            span.setSpan(termsOfServiceSpan, linkIndex, linkIndex + linkText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            // update view with span
            link.setText(span);
            link.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    // Notify the activity that the link span was clicked
    public interface RecipeDetailsFragmentListener {
        void openUrl(String url);
    }
}
