package id.zelory.codepolitan.network.response;

/**
 * Created by zetbaitsu on 7/28/15.
 */
public class ObjectResponse<Data> extends Response
{
    Data result;

    public Data getResult()
    {
        return result;
    }

    public void setResult(Data result)
    {
        this.result = result;
    }
}
