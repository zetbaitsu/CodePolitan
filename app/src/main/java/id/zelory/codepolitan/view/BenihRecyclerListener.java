package id.zelory.codepolitan.view;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by zetbaitsu on 7/28/15.
 */
public abstract class BenihRecyclerListener extends RecyclerView.OnScrollListener
{
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 3;
    private int firstVisibleItem;
    private int visibleItemCount;
    private int totalItemCount;
    private int currentPage = 0;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;

    public BenihRecyclerListener(LinearLayoutManager linearLayoutManager)
    {
        this.linearLayoutManager = linearLayoutManager;
    }

    public BenihRecyclerListener(GridLayoutManager gridLayoutManager)
    {
        this.gridLayoutManager = gridLayoutManager;
    }

    public BenihRecyclerListener(LinearLayoutManager linearLayoutManager, int visibleThreshold)
    {
        this.linearLayoutManager = linearLayoutManager;
        this.visibleThreshold = visibleThreshold;
    }

    public BenihRecyclerListener(GridLayoutManager gridLayoutManager, int visibleThreshold)
    {
        this.gridLayoutManager = gridLayoutManager;
        this.visibleThreshold = visibleThreshold;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy)
    {
        super.onScrolled(recyclerView, dx, dy);

        visibleItemCount = recyclerView.getChildCount();
        if (gridLayoutManager == null)
        {
            totalItemCount = linearLayoutManager.getItemCount();
            firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
        } else if (linearLayoutManager == null)
        {
            totalItemCount = gridLayoutManager.getItemCount();
            firstVisibleItem = gridLayoutManager.findFirstVisibleItemPosition();
        }

        if (loading && totalItemCount > previousTotal)
        {
            loading = false;
            previousTotal = totalItemCount;
        }

        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold))
        {
            currentPage++;
            onLoadMore(currentPage);
            loading = true;
        }
    }

    public abstract void onLoadMore(int currentPage);
}
