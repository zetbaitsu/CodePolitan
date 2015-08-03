package id.zelory.codepolitan;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import id.zelory.benih.BenihActivity;
import id.zelory.benih.fragment.BenihFragment;
import id.zelory.codepolitan.adapter.MainPagerAdapter;
import id.zelory.codepolitan.fragment.HomeFragment;

public class MainActivity extends BenihActivity
{
    @Bind(R.id.view_pager) ViewPager viewPager;
    @Bind(R.id.tab_layout) TabLayout tabLayout;
    @Bind(R.id.toolbar) Toolbar toolbar;

    @Override
    protected int getActivityView()
    {
        return R.layout.activity_main;
    }

    @Override
    protected void onViewReady(Bundle bundle)
    {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");
        setUpViewPager();
        setUpTabLayout();

    }

    private void setUpViewPager()
    {
        List<BenihFragment> fragments = new ArrayList<>();
        for (int i = 0; i < 5; i++)
        {
            fragments.add(new HomeFragment());
        }

        MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
    }

    private void setUpTabLayout()
    {
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.home);
        tabLayout.getTabAt(1).setIcon(R.drawable.category);
        tabLayout.getTabAt(2).setIcon(R.drawable.tag);
        tabLayout.getTabAt(3).setIcon(R.drawable.user);
        tabLayout.getTabAt(4).setIcon(R.drawable.setting);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        return super.onOptionsItemSelected(item);
    }
}
