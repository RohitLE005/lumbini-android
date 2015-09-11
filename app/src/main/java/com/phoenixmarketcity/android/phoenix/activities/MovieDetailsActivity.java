package com.phoenixmarketcity.android.phoenix.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.gson.annotations.SerializedName;
import com.phoenixmarketcity.android.phoenix.R;
import com.phoenixmarketcity.android.phoenix.booking.BookNowScreen;
import com.phoenixmarketcity.android.phoenix.webservices.WebService;

import org.parceler.Parcel;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MovieDetailsActivity extends BaseActivity implements YouTubePlayer.OnInitializedListener {

    private Activity activity;
    // Static method to create intent to launch this activity
    public static Intent createLaunchIntent(final Context context, final int movieId) {
        final Intent intent = new Intent(context, MovieDetailsActivity.class);
        intent.putExtra(EXTRA_MOVIE_ID, movieId);
        return intent;
    }

    public static final String EXTRA_MOVIE_ID = GenericPageActivity.class.getName()
            + ".EXTRA.movieId";

    private int mMovieId;
    private ProgressBar mProgressBar;
    private MovieDetails mMovieData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        setContentView(R.layout.moviedetailsview);
        activity=this;

        mProgressBar = (ProgressBar) findViewById(R.id.movieProgressBar);
        mMovieId = getIntent().getIntExtra(EXTRA_MOVIE_ID, 0);
        if (mMovieId == 0) {
            showEmptyPage();
            return;
        }
        WebService.getInstance().getMovieDetails(mMovieId, new Callback<MovieDetails>() {
            @Override
            public void success(MovieDetails movieObj, Response response) {
                mMovieData = movieObj;
                if (mMovieData.mMovieId == null) {
                    showEmptyPage();
                    return;
                }

                createMediaCards();
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void failure(RetrofitError error) {
                showEmptyPage();
            }
        });
        handleFooterNavigation();
    }

    // Handles footer bar navigation
    private void handleFooterNavigation() {
        // On cinema Activity
        Button footerButton = (Button) findViewById(R.id.FooterButtonCinema);
        footerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });

        // On Home Activity
        footerButton = (Button) findViewById(R.id.FooterButtonHome);
        footerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchIntent = new Intent(MovieDetailsActivity.this, LandingPageActivity.class);
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(launchIntent);
            }
        });

        // On comments Activity
        footerButton = (Button) findViewById(R.id.FooterButtonBookTicket);
        footerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchIntent = new Intent(MovieDetailsActivity.this, BookNowScreen.class);
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(launchIntent);
            }
        });

        // On Do Activity
        footerButton = (Button) findViewById(R.id.FooterButtonDo);
        footerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchIntent = new Intent(MovieDetailsActivity.this, DoActivity.class);
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(launchIntent);
            }
        });

    }

    private void showEmptyPage() {
        mProgressBar.setVisibility(View.GONE);
        findViewById(R.id.error_text_view).setVisibility(View.VISIBLE);
        findViewById(R.id.movieParentViewId).setVisibility(View.GONE);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                        YouTubePlayer player, boolean wasRestored) {
        int index = mMovieData.mTrailerUrl.lastIndexOf("/");
        if (index != -1) {
            String videoId = mMovieData.mTrailerUrl.substring(index + 1);
            if (!wasRestored) player.cueVideo(videoId);
        }
    }
    @Override
    public void onInitializationFailure(YouTubePlayer.Provider arg0,
                                        YouTubeInitializationResult arg1){
    }

    private void createMediaCards() {
        setTitle(mMovieData.mMovieName);
        YouTubePlayerFragment youTubePlayerFragment =
                (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);
        youTubePlayerFragment.initialize(getResources().getString(R.string.android_key), this);

        TextView releasedate = (TextView) findViewById(R.id.ReleaseDateText);
        releasedate.setText(mMovieData.mReleaseDate);

        TextView runtime = (TextView) findViewById(R.id.RunTimeText);
        runtime.setText(mMovieData.mRuntime);

        TextView director = (TextView) findViewById(R.id.DirectorText);
        director.setText(mMovieData.mDirector);

        TextView language=(TextView)findViewById(R.id.LanguageText);
        language.setText(mMovieData.mLanguage);

        TextView genre=(TextView)findViewById(R.id.GenreText);
        genre.setText(mMovieData.mGenre);

        TextView cast=(TextView)findViewById(R.id.CastText);
        cast.setText(mMovieData.mCast);

        TextView storyDescription=(TextView)findViewById(R.id.StoryDescription);
        storyDescription.setText(mMovieData.mStoryDescription);
    }

    @Parcel
    public  static class MovieDetails {
        @SerializedName("movie_id")
        public String mMovieId;
        @SerializedName("movie_name")
        public String mMovieName;
        @SerializedName("genre")
        public String mGenre;
        @SerializedName("release_date")
        public String mReleaseDate;
        @SerializedName("runtime")
        public String mRuntime;
        @SerializedName("director")
        public String mDirector;
        @SerializedName("language")
        public String mLanguage;
        @SerializedName("cast")
        public String mCast;
        @SerializedName("movie_photo")
        public String mMovieImage;
        @SerializedName("movie_desc")
        public String mStoryDescription;
        @SerializedName("movie_video_url")
        public String mTrailerUrl;
    }
}
