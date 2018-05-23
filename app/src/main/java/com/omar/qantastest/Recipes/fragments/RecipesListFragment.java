package com.omar.qantastest.Recipes.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.omar.qantastest.Common.network.domain.models.RecipeResponse;
import com.omar.qantastest.Common.network.managers.RecipesManager;
import com.omar.qantastest.Common.ui.BaseFragment;
import com.omar.qantastest.R;

import java.util.ArrayList;

import butterknife.ButterKnife;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by omz on 23/5/18
 */
public class RecipesListFragment extends BaseFragment {

    private RecipesManager recipesManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recipesManager = new RecipesManager();
        searchProperties();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipes_list, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    public void searchProperties() {
        // Make the API call (retrofit and rxjava)
        recipesManager.searchProperties()
                .subscribe(getPropertiesSubscriber());
    }

    private DisposableObserver<RecipeResponse> getPropertiesSubscriber() {
        return new DisposableObserver<RecipeResponse>() {
            @Override
            public void onComplete() {
                // noop
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(RecipeResponse response) {
                Log.d("RecipeList", "API result size: "+response.getResults().size());
            }
        };
    }
}
