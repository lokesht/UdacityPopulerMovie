package in.udacity.learning.populermovie.app.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import in.udacity.learning.constant.AppConstant;
import in.udacity.learning.model.MovieItem;
import in.udacity.learning.populermovie.app.R;
import in.udacity.learning.populermovie.app.fragment.DetailFragment;
import in.udacity.learning.populermovie.app.fragment.MainFragment;

public class MainActivity extends AppCompatActivity implements MainFragment.Callback {

    private final String TAG = getClass().getName();
    private Toolbar mToolbar;

    /*Track Application is running on Tab or mobile*/
    private boolean mTwoPane = false;

    private static final String DETAIL_FRAGMENT_TAG = "DFTAG";

    /*For back button click,If user is clicking by mistake app should not close*/
    private Handler handler;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize(savedInstanceState);
        handler = new Handler();
    }

    public void initialize(Bundle savedInstanceState) {
        mToolbar = (Toolbar) findViewById(R.id.tb_main);
        setSupportActionBar(mToolbar);

        if (findViewById(R.id.detail_container) != null) {
            // The detail container view will be present only in the large-screen layouts
            // (res/layout-sw600dp). If this view is present, then the activity should be
            // in two-pane mode.
            mTwoPane = true;
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.detail_container, new DetailFragment(), DETAIL_FRAGMENT_TAG)
                        .commit();
            }
        } else {
            mTwoPane = false;
            getSupportActionBar().setElevation(0f);
        }
    }

    @Override
    public void onItemSelected(MovieItem item, View view) {
        if (mTwoPane) {
            if (AppConstant.DEBUG_DONE) {
                String title = item == null ? "Null Object" : item.getTitle();
                Toast.makeText(MainActivity.this, title, Toast.LENGTH_SHORT).show();
            }
            Bundle b = new Bundle();
            b.putParcelable(AppConstant.OBJECT, item);

            DetailFragment detailFragment = new DetailFragment();
            detailFragment.setArguments(b);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_container, detailFragment, DETAIL_FRAGMENT_TAG)
                    .commit();

        } else {
            Intent in = new Intent(this, MovieDetailActivityMobile.class);
            in.putExtra(Intent.EXTRA_TEXT, item);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                String transition = getString(R.string.transition_thumbnail_to_poster);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, view, transition);
                startActivity(in, options.toBundle());
            } else {
                startActivity(in);
            }
        }
    }

    public boolean ismTwoPane() {
        return mTwoPane;
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
