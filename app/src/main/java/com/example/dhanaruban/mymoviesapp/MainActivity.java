package com.example.dhanaruban.mymoviesapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dhanaruban.mymoviesapp.utilities.MovieJsonUtils;
import com.example.dhanaruban.mymoviesapp.utilities.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements AsyncTaskCompleteListener<String> {

    private static int mSortOption = 1;

    private TextView mErrorMessageDisplay;

    private ProgressBar mLoadingIndicator;

    private MoviesDB movies = new MoviesDB();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        //mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        GridView mGridView = findViewById(R.id.flavors_grid);
        mGridView.setOnItemClickListener(moviePosterClickListener);

        makeMovieDBSearchQuery();


    }

    /**
     * This method retrieves the search text from the EditText, constructs the
     * URL (using {@link NetworkUtils}) for the github repository you'd like to find, displays
     * that URL in a TextView, and finally fires off an AsyncTask to perform the GET request using
     * our {@link MovieDBQueryTask}
     */
    private void makeMovieDBSearchQuery() {
        String apiKey = getString(R.string.api_key);

        int mPageNumber = 1;
        URL MovieDBSearchUrl = NetworkUtils.buildUrl(Integer.toString(mPageNumber),apiKey );
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if(isConnected) {
            //new MovieDBQueryTask().execute(MovieDBSearchUrl);
            new MovieDBQueryTask(this, this ).execute(MovieDBSearchUrl);
        } else{
            Toast.makeText(this, R.string.NoNetwork, Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * This method will make the View for the JSON data visible and
     * hide the error message.
     * <p>
     * Since it is okay to redundantly set the visibility of a View, we don't
     * need to check whether each view is currently visible or invisible.
     */
    private void showJsonDataView() {
        // First, make sure the error is invisible
        //mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        // Then, make sure the JSON data is visible
        //mGridView.setVisibility(View.VISIBLE);
    }

    /**
     * This method will make the error message visible and hide the JSON
     * View.
     * <p>
     * Since it is okay to redundantly set the visibility of a View, we don't
     * need to check whether each view is currently visible or invisible.
     */
    private void showErrorMessage() {
        // First, hide the currently visible data
        //mGridView.setVisibility(View.INVISIBLE);
        // Then, show the error
        //mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    @Override
    public void onTaskComplete(String searchResults) {

        //mLoadingIndicator.setVisibility(View.INVISIBLE);
        if (searchResults != null && !searchResults.equals("")) {

            showJsonDataView();
            //MoviesDB movies = new MoviesDB();
            try {
                movies = MovieJsonUtils.getMovieContentValuesFromJson(searchResults);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            assert movies != null;
            MoviesDBAdapter moviesDBAdapter = new MoviesDBAdapter(MainActivity.this, movies.getResults());

            // Get a reference to the ListView, and attach this adapter to it.
            GridView gridView = findViewById(R.id.flavors_grid);
            gridView.setAdapter(moviesDBAdapter);

        } else {

            showErrorMessage();
        }

    }





    private final GridView.OnItemClickListener moviePosterClickListener = new GridView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Movie movie = (Movie) parent.getItemAtPosition(position);

            Intent intent = new Intent(getApplicationContext(), MovieDetailsActivity.class);
            intent.putExtra(getResources().getString(R.string.parcel_movie), movie);

            startActivity(intent);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.most_popular) {
            Context context = MainActivity.this;
            String textToShow = "Most popular";
            mSortOption = 1;
            Toast.makeText(context, textToShow, Toast.LENGTH_SHORT).show();
            makeMovieDBSearchQuery();
            return true;
        } else if (itemThatWasClickedId == R.id.top_rated) {
            Context context = MainActivity.this;
            String textToShow = "Top rated";
            mSortOption = 2;
            Toast.makeText(context, textToShow, Toast.LENGTH_SHORT).show();
            makeMovieDBSearchQuery();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static int getmSortOption() {
        return mSortOption;
    }

    public static void setmSortOption(int mSortOption) {
        MainActivity.mSortOption = mSortOption;
    }
}
