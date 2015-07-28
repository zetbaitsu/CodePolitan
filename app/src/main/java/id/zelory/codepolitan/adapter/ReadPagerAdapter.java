package id.zelory.codepolitan.adapter;

import android.support.v4.app.FragmentManager;

import java.util.List;

import id.zelory.benih.adapters.BenihPagerAdapter;
import id.zelory.codepolitan.fragment.ReadFragment;

/**
 * Created by zetbaitsu on 7/28/15.
 */
public class ReadPagerAdapter extends BenihPagerAdapter<ReadFragment>
{
    public ReadPagerAdapter(FragmentManager fm, List<ReadFragment> readFragments)
    {
        super(fm, readFragments);
    }

    @Override
    public ReadFragment getItem(int i)
    {
        return fragments.get(i);
    }
}
