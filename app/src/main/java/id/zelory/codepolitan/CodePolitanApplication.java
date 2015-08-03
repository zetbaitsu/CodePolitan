package id.zelory.codepolitan;

import id.zelory.benih.BenihApplication;
import timber.log.Timber;

/**
 * Created by zetbaitsu on 7/31/15.
 */
public class CodePolitanApplication extends BenihApplication
{
    private static CodePolitanApplication codePolitanApplication;

    @Override
    public void onCreate()
    {
        super.onCreate();
        codePolitanApplication = this;
        if (BuildConfig.DEBUG)
        {
            Timber.plant(new Timber.DebugTree());
        }
    }

    public static CodePolitanApplication pluck()
    {
        return codePolitanApplication;
    }
}
