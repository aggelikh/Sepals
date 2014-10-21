package gr.sepals.gui.info;

import java.util.ArrayList;
import java.util.List;

import com.espian.showcaseview.ShowcaseView;
import com.espian.showcaseview.ShowcaseView.ConfigOptions;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Display;


public class IntroSlidePagerActivity extends FragmentActivity {
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 6;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private ScreenSlidePagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_slide_activity);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPagerAdapter.init();
        mPager.setAdapter(mPagerAdapter);
        
        showHandGesture();
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }
    
    private void showHandGesture(){
        final float startX = getResources().getDimension(R.dimen.hand_start_X);
        final float startY = getResources().getDimension(R.dimen.hand_start_Y);
        final float endX = getResources().getDimension(R.dimen.hand_end_X);
        final float endY = getResources().getDimension(R.dimen.hand_end_Y);
        ConfigOptions co = new ShowcaseView.ConfigOptions();
        co.hideOnClickOutside = true;
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        ShowcaseView sv = ShowcaseView.insertShowcaseView(width, height, this, R.string.slide_left_title,
                R.string.slide_left_description, co);
        sv.setScaleMultiplier(0);
        sv.animateGesture(startX, startY, endX, endY, true);
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        
	private List<Fragment> fragments;
	
	public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }
        
        public void init(){
            fragments = new ArrayList<Fragment>();
            fragments.add(new LogoSlidePageFragment());
            fragments.add(new IntroProductsFragment());
            fragments.add(new LogoSlidePageFragment());
            fragments.add(new LogoSlidePageFragment());
            fragments.add(new LogoSlidePageFragment());
            fragments.add(new LogoSlidePageFragment());
        }
        
        @Override
        public Fragment getItem(int position) {
            return (fragments.size() >= position) ? fragments.get(position) : null;
            }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}