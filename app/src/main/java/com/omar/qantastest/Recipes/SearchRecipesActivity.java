package com.omar.qantastest.Recipes;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;

import com.omar.qantastest.Common.network.domain.models.Recipe;
import com.omar.qantastest.Common.ui.BaseActivity;
import com.omar.qantastest.R;
import com.omar.qantastest.Recipes.fragments.RecipeDetailsFragment;
import com.omar.qantastest.Recipes.fragments.RecipesListFragment;

import butterknife.ButterKnife;

/**
 * Created by omz on 23/5/18
 */
public class SearchRecipesActivity extends BaseActivity implements RecipesListFragment.RecipesListFragmentListener {

    FragmentManager fragmentManager;

    private String TAG_RECIPES_LIST = "rList";
    private RecipesListFragment recipesListFragment;
    private String TAG_RECIPE_DETAILS = "rDetails";
    private RecipeDetailsFragment recipeDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        fragmentManager = this.getSupportFragmentManager();

        loadFragment();
    }

    @Override
    public int getContentViewReference() {
        return R.layout.activity_recipes_search;
    }

    private void loadFragment() {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        recipesListFragment = (RecipesListFragment) fragmentManager.findFragmentByTag(TAG_RECIPES_LIST);

        if (recipesListFragment == null) {
            recipesListFragment = new RecipesListFragment();
            fragmentTransaction.add(R.id.fragmentContainer, recipesListFragment, TAG_RECIPES_LIST);
        } else {
            fragmentTransaction.replace(R.id.fragmentContainer, recipesListFragment, TAG_RECIPES_LIST);
        }

        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void recipeClicked(Recipe recipe) {
        if (recipeDetailsFragment == null) {
            recipeDetailsFragment = new RecipeDetailsFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable("recipe", recipe);
        recipeDetailsFragment.setArguments(bundle);
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.fragmentContainer, recipeDetailsFragment, TAG_RECIPE_DETAILS);
        ft.addToBackStack(null);
        ft.commitAllowingStateLoss();
    }
}
