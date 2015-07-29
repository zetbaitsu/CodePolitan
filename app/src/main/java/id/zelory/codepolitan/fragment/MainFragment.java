package id.zelory.codepolitan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.ArrayList;

import id.zelory.benih.fragments.BenihFragment;
import id.zelory.benih.utils.BenihScheduler;
import id.zelory.benih.views.BenihRecyclerListener;
import id.zelory.benih.views.BenihRecyclerView;
import id.zelory.codepolitan.R;
import id.zelory.codepolitan.ReadActivity;
import id.zelory.codepolitan.adapter.ArticleAdapter;
import id.zelory.codepolitan.model.Article;
import id.zelory.codepolitan.network.CodePolitanService;
import rx.Subscription;

/**
 * Created by zetbaitsu on 7/28/15.
 */
public class MainFragment extends BenihFragment
{
    private ArticleAdapter articleAdapter;
    private BenihRecyclerView recyclerView;
    private Subscription subscription;

    @Override
    protected int getFragmentView()
    {
        return R.layout.fragment_main;
    }

    @Override
    protected void onViewReady(Bundle bundle, View view)
    {
        recyclerView = (BenihRecyclerView) view.findViewById(R.id.recycler_view);

        setUpAdapter();
        setUpRecyclerView();
        getArticles(1);
    }

    private void setUpAdapter()
    {
        articleAdapter = new ArticleAdapter(getActivity());
        articleAdapter.setOnItemClickListener(this::onItemClick);
    }

    private void setUpRecyclerView()
    {
        recyclerView.setUpAsList();
        recyclerView.setAdapter(articleAdapter);
        recyclerView.addOnScrollListener(new BenihRecyclerListener((LinearLayoutManager) recyclerView.getLayoutManager())
        {
            @Override
            public void onLoadMore(int currentPage)
            {
                getArticles(currentPage + 1);
            }
        });
    }

    private void getArticles(int page)
    {
        subscription = CodePolitanService.getApi()
                .getLatestArticles(page)
                .compose(BenihScheduler.applySchedulers(BenihScheduler.Type.IO))
                .subscribe(articleResponse -> {
                    if (articleResponse.getCode())
                    {
                        articleAdapter.add(articleResponse.getResult());
                    }
                }, throwable -> {
                    log(throwable.getMessage());
                    getArticles(page);
                });
    }

    private void onItemClick(View view, int position)
    {
        Intent intent = new Intent(getActivity(), ReadActivity.class);
        intent.putParcelableArrayListExtra("data", (ArrayList<Article>) articleAdapter.getData());
        intent.putExtra("position", position);
        startActivity(intent);
    }

    @Override
    public void onDestroy()
    {
        if (articleAdapter != null)
        {
            articleAdapter.clear();
            articleAdapter = null;
        }
        if (subscription != null)
        {
            subscription.unsubscribe();
            subscription = null;
        }
        recyclerView = null;
        super.onDestroy();
    }
}
