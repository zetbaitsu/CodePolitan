package id.zelory.codepolitan.adapter;

import android.support.v4.app.FragmentManager;

import java.util.List;

import id.zelory.benih.adapter.BenihPagerAdapter;
import id.zelory.benih.fragment.BenihFragment;

/**
 * Created by zetbaitsu on 8/1/15.
 */
public class MainPagerAdapter extends BenihPagerAdapter<BenihFragment>
{
    public MainPagerAdapter(FragmentManager fm, List<BenihFragment> list)
    {
        super(fm, list);
    }

    @Override
    public BenihFragment getItem(int i)
    {
        return fragments.get(i);
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return "";
    }
}
