/**This file is part of Sepals.
 *
 * Sepals is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * Sepals is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Sepals.  If not, see <http://www.gnu.org/licenses/>.
 */
package gr.sepals.eshop.info.gui;

import java.util.ArrayList;
import java.util.List;

import com.espian.showcaseview.OnShowcaseEventListener;
import com.espian.showcaseview.ShowcaseView;
import com.espian.showcaseview.ShowcaseView.ConfigOptions;

import gr.sepals.eshop.R;
import gr.sepals.eshop.persistence.InternalDatabaseKeys;
import gr.sepals.eshop.util.db.Settings;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Display;

public class InfoSlidePagerActivity extends FragmentActivity {

    /**
     * The pager widget, which handles animation and allows swiping horizontally
     * to access previous and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private ScreenSlidePagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_slide_activity);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(mPager,
                getSupportFragmentManager());
        mPagerAdapter.init();
        mPager.setAdapter(mPagerAdapter);

        // hide initial info
        if (Settings.getBoolean(this, InternalDatabaseKeys.HIDE_INTRO_INFO, false)) {
            mPager.setCurrentItem(6);
        }

    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the
            // system to handle the
            // Back button. This calls finish() on this activity and pops the
            // back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    @SuppressWarnings("deprecation")
    private void showHandGesture() {
        final float startX = getResources().getDimension(R.dimen.hand_start_X);
        final float startY = getResources().getDimension(R.dimen.hand_start_Y);
        final float endX = getResources().getDimension(R.dimen.hand_end_X);
        final float endY = getResources().getDimension(R.dimen.hand_end_Y);
        Display display = getWindowManager().getDefaultDisplay();
        //ConfigOptions co = new ShowcaseView.ConfigOptions();
        //co.hideOnClickOutside = true;
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        ShowcaseView sv = ShowcaseView.insertShowcaseView(width, height, this,
                R.string.slide_left_title, R.string.slide_left_description, null);//, co);
        sv.setScaleMultiplier(0);
        sv.animateGesture(startX, startY, endX, endY, true);
        sv.setOnShowcaseEventListener(new OnShowcaseEventListener() {

            @Override
            public void onShowcaseViewShow(ShowcaseView showcaseView) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onShowcaseViewHide(ShowcaseView showcaseView) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onShowcaseViewDidHide(ShowcaseView showcaseView) {
                // TODO Auto-generated method stub
                Settings.setBoolean(getApplicationContext(),
                        InternalDatabaseKeys.SHOW_SLIDE_HAND, false);
            }
        });
    }

    /**
     * A simple pager adapter that represents 7 ScreenSlidePageFragment objects,
     * in sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        private List<Fragment> fragments;
        private ViewPager pager;

        public ScreenSlidePagerAdapter(ViewPager pager, FragmentManager fm) {
            super(fm);
            this.pager = pager;
        }

        public void init() {
            fragments = new ArrayList<Fragment>();
            fragments.add(new LogoSlidePageFragment());
            fragments.add(new ProductsSlidePageFragment());
            fragments.add(new NetworkSlidePageFragment());
            fragments.add(new OrderSlidePageFragment());
            fragments.add(new BalconySlidePageFragment());
            fragments.add(new ThanksSlidePageFragment());
            fragments.add(new SyncSlidePageFragment());
        }

        @Override
        public Fragment getItem(int position) {
            Fragment current = null;
            if (fragments.size() >= position) {
                current = fragments.get(position);
            }
            // I wish I could find a better way to update 1st fragment!
            if (position == 0
                    && pager.getCurrentItem() == 0
                    && Settings.getBoolean(getApplicationContext(),
                            InternalDatabaseKeys.SHOW_SLIDE_HAND, true)) {
                showHandGesture();
            }

            return current;
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
