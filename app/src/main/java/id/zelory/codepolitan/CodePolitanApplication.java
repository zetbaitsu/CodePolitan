package id.zelory.codepolitan;

import id.zelory.benih.BenihApplication;

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
    }

    public static CodePolitanApplication pluck()
    {
        return codePolitanApplication;
    }
}
