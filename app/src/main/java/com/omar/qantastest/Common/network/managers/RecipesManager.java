package com.omar.qantastest.Common.network.managers;

import com.omar.qantastest.Common.network.ServerSettings;
import com.omar.qantastest.Common.network.ServiceFactory;
import com.omar.qantastest.Common.network.domain.SearchRecipesService;
import com.omar.qantastest.Common.network.domain.models.RecipeResponse;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by omz on 23/5/18
 */
public class RecipesManager {

    protected SearchRecipesService searchRecipesService;

    public RecipesManager() {
        searchRecipesService = ServiceFactory.createService(SearchRecipesService.class, ServerSettings.Domain.BASE_URL);
    }

    public Observable<RecipeResponse> searchRecipes() {
        return searchRecipesService.searchRecipes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
