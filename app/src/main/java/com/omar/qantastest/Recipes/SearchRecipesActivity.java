package com.omar.qantastest.Recipes;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;

import com.omar.qantastest.Common.network.domain.models.Recipe;
import com.omar.qantastest.Common.ui.BaseActivity;
import com.omar.qantastest.R;
import com.omar.qantastest.Recipes.fragments.RecipesListFragment;

import butterknife.ButterKnife;

/**
 * Created by omz on 23/5/18
 */
public class SearchRecipesActivity extends BaseActivity implements RecipesListFragment.RecipesListFragmentListener {

    FragmentManager fragmentManager;

    private String TAG_RECIPES_LIST = "rList";
    private RecipesListFragment recipesListFragment;

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
        }

        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void recipeClicked(Recipe recipe) {

    }
}
