package id.zelory.codepolitan;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import id.zelory.benih.BenihActivity;
import id.zelory.codepolitan.fragment.MainFragment;

public class MainActivity extends BenihActivity
{
    @Override
    protected int getActivityView()
    {
        return R.layout.activity_main;
    }

    @Override
    protected void onViewReady(Bundle bundle)
    {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, new MainFragment())
                .commit();
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
        int id = item.getItemId();

        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }
}
