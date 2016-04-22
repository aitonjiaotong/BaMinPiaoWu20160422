package bamin.com.kepiao.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import bamin.com.kepiao.R;
import bamin.com.kepiao.customView.ViewPagerIndicator;
import bamin.com.kepiao.fragment.GuideFragment;

public class GuideActivity extends FragmentActivity
{
    private int[] guideImg = new int[]{
            R.mipmap.gui01,
            R.mipmap.gui02,
            R.mipmap.gui03,
    };
    private ViewPagerIndicator mViewPagerIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ViewPager myPager = (ViewPager) findViewById(R.id.myPager);
        myPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        mViewPagerIndicator = (ViewPagerIndicator) findViewById(R.id.ViewPagerIndicator);
        myPager.addOnPageChangeListener(new MyPageChangerListener());
    }

    class MyPageChangerListener implements ViewPager.OnPageChangeListener
    {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
        {
//            mViewPagerIndicator.move(positionOffset, position);
            mViewPagerIndicator.move( position);
        }

        @Override
        public void onPageSelected(int position)
        {

        }

        @Override
        public void onPageScrollStateChanged(int state)
        {

        }
    }

    class MyAdapter extends FragmentPagerAdapter
    {

        public MyAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            GuideFragment guideFragment = new GuideFragment();
            guideFragment.setImg(guideImg[position], guideImg.length - 1, position);
            return guideFragment;
        }

        @Override
        public int getCount()
        {
            return guideImg.length;
        }
    }

}
