package id.zelory.codepolitan.network.response;

import java.util.List;

/**
 * Created by zetbaitsu on 7/28/15.
 */
public class ListResponse<Data> extends Response
{
    private List<Data> result;

    public List<Data> getResult()
    {
        return result;
    }

    public void setResult(List<Data> result)
    {
        this.result = result;
    }
}
