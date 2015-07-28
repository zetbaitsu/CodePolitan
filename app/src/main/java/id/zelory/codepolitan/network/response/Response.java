package id.zelory.codepolitan.network.response;

/**
 * Created by zetbaitsu on 7/28/15.
 */
public class Response
{
    private int code;

    public boolean getCode()
    {
        return code == 200;
    }

    public void setCode(int code)
    {
        this.code = code;
    }
}
