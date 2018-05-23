# Qantas Test - Omar Mujtaba

### Network:
* The app uses Retrofit and RxJava to fetch results from the API call
* All data models have been generated directly from http://www.jsonschema2pojo.org using the response object from the API call
* I have a code structure that I use to handle Errors and setting up everything for API calls. The RetrofitException, RxErrorHandlingCallAdapterFactory, OkHttpClientFactory, and ServiceFactory classes are directly ported from my own little code collection

### UI:
* The app uses 3 Fragments. RecipesListFragment is the one that fetches the results form the API and displays the results in two Recycler Views
* A CardView is used to design the layout of each recipe item in the Adapters (PopularRecipesListAdapter, OtherRecipesListAdapter)
* Glide is used to fetch the images and display them in the relevant image views from their URLs
* RecipeDetailsFragment is used to display the details of the recipe
* Fragments use interfaces to pass on the touch events to the SearchRecipesActivity which handles the touch events to either open the Recipe Details, or the Web View
* Animations are defined in XML files inside the "anim" folder. They are simply added to the fragment transaction before committing via the setCustomAnimations() function

### Orientation change and retaining data:
* All fragments have "setRetainInstance(true)" declared in their onCreate methods, which saves the state of the fragments, so they don't have to be created each time
* The loaded data is passed in the onSaveInstanceState bundle to repopulate on orientation change
* activity_recipes_search has one FrameLayout defined which is the placeholder for all the fragments
* There are two "config.xml" files, one in the "values" and the other in the "values-land" folder, and declares a boolean resource "landscapeMode". With this boolean we can decide how many grids we want to show in the list and detect orientation change
* SearchRecipesActivity checks if the fragment manager has instances of the fragments previously created in the onCreate method based on their tags. If they are null, a new instance is created, if not, then the previous ones are used

### Video Demo:
* [Click here for demo](https://photos.app.goo.gl/ZtYBNyWnJD1PWZgo2)