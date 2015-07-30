package id.zelory.codepolitan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import id.zelory.benih.controller.Controller;
import id.zelory.benih.fragment.BenihFragment;
import id.zelory.benih.view.BenihRecyclerListener;
import id.zelory.benih.view.BenihRecyclerView;
import id.zelory.codepolitan.R;
import id.zelory.codepolitan.ReadActivity;
import id.zelory.codepolitan.adapter.ArticleAdapter;
import id.zelory.codepolitan.controller.ArticleController;
import id.zelory.codepolitan.model.Article;

/**
 * Created by zetbaitsu on 7/28/15.
 */
public class MainFragment extends BenihFragment implements ArticleController.Presenter
{
    private ArticleController articleController;
    private ArticleAdapter articleAdapter;
    private BenihRecyclerView recyclerView;

    @Override
    protected int getFragmentView()
    {
        return R.layout.fragment_main;
    }

    @Override
    protected void onViewReady(Bundle bundle, View view)
    {
        if (articleController == null)
        {
            log("controller is null");
            articleController = new ArticleController(this);
        }

        recyclerView = (BenihRecyclerView) view.findViewById(R.id.recycler_view);

        setUpAdapter();
        setUpRecyclerView();
        if (bundle != null)
        {
            articleController.loadState(bundle);
        } else
        {
            articleController.loadArticles(1);
        }
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
                articleController.loadArticles(currentPage + 1);
            }
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
    public void onSaveInstanceState(Bundle outState)
    {
        articleController.saveState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void showArticle(Article article)
    {

    }

    @Override
    public void showArticles(List<Article> articles)
    {
        articleAdapter.add(articles);
    }

    @Override
    public void showError(Controller.Presenter presenter, Throwable throwable)
    {

    }
}
