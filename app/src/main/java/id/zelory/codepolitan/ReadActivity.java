package id.zelory.codepolitan;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import id.zelory.benih.BenihActivity;
import id.zelory.benih.utils.BenihWorker;
import id.zelory.codepolitan.adapter.ReadPagerAdapter;
import id.zelory.codepolitan.fragment.ReadFragment;
import id.zelory.codepolitan.model.Article;

/**
 * Created by zetbaitsu on 7/28/15.
 */
public class ReadActivity extends BenihActivity implements ViewPager.OnPageChangeListener
{
    private ViewPager viewPager;
    private ReadPagerAdapter adapter;
    private List<ReadFragment> readFragments;
    private List<Article> articles;
    private int position;

    @Override
    protected int getActivityView()
    {
        return R.layout.activity_read;
    }

    @Override
    protected void onViewReady(Bundle bundle)
    {
        viewPager = (ViewPager) findViewById(R.id.pager);
        readFragments = new ArrayList<>();
        articles = bundle != null ? bundle.getParcelableArrayList("data") : getIntent().getParcelableArrayListExtra("data");
        position = bundle != null ? bundle.getInt("position", 0) : getIntent().getIntExtra("position", 0);

        BenihWorker.doThis(this::generateFragments)
                .subscribe(o -> {
                    setUpAdapter();
                    setUpViewPager();
                });
    }

    private void setUpViewPager()
    {
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);
        viewPager.addOnPageChangeListener(this);
    }

    private void setUpAdapter()
    {
        adapter = new ReadPagerAdapter(getSupportFragmentManager(), readFragments);
    }

    private void generateFragments()
    {
        readFragments.clear();
        for (Article article : articles)
        {
            ReadFragment readFragment = new ReadFragment();
            readFragment.setData(article);
            readFragments.add(readFragment);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState)
    {
        outState.putParcelableArrayList("data", (ArrayList<Article>) articles);
        outState.putInt("position", position);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onDestroy()
    {
        viewPager = null;
        adapter = null;
        if (readFragments != null)
        {
            readFragments.clear();
            readFragments = null;
        }
        if (articles != null)
        {
            articles.clear();
            articles = null;
        }
        super.onDestroy();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
    {

    }

    @Override
    public void onPageSelected(int position)
    {
        this.position = position;
    }

    @Override
    public void onPageScrollStateChanged(int state)
    {

    }
}
