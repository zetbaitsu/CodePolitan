package id.zelory.codepolitan;

import com.squareup.leakcanary.LeakCanary;

import id.zelory.benih.BenihApplication;

/**
 * Created by zetbaitsu on 7/31/15.
 */
public class CodePolitanApplication extends BenihApplication
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        LeakCanary.install(this);
    }
}
