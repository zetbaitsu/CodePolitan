package id.zelory.codepolitan.controller;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import id.zelory.benih.controller.BenihController;
import id.zelory.benih.util.BenihScheduler;
import id.zelory.benih.util.BenihWorker;
import id.zelory.codepolitan.model.Article;
import id.zelory.codepolitan.network.CodePolitanService;

/**
 * Created by zetbaitsu on 7/29/15.
 */
public class ArticleController extends BenihController<ArticleController.Presenter>
{
    private Article article;
    private List<Article> articles;

    public ArticleController(Presenter presenter)
    {
        super(presenter);
    }

    public void loadArticle(int id)
    {
        CodePolitanService.pluck()
                .getApi()
                .getDetailArticle(id)
                .compose(BenihScheduler.pluck().applySchedulers(BenihScheduler.Type.IO))
                .subscribe(articleResponse -> {
                    if (articleResponse.getCode())
                    {
                        article = articleResponse.getResult();
                        presenter.showArticle(article);
                    }
                }, throwable -> {
                    log(throwable.getMessage());
                    presenter.showError(presenter, throwable);
                });
    }

    public void loadArticles(int page)
    {
        CodePolitanService.pluck()
                .getApi()
                .getLatestArticles(page)
                .compose(BenihScheduler.pluck().applySchedulers(BenihScheduler.Type.IO))
                .subscribe(articleResponse -> {
                    if (articleResponse.getCode())
                    {
                        BenihWorker.pluck()
                                .doThis(() -> {
                                    if (page == 1)
                                    {
                                        articles = articleResponse.getResult();
                                    } else
                                    {
                                        articles.addAll(articleResponse.getResult());
                                    }
                                }).subscribe(o -> presenter.showArticles(articleResponse.getResult()));
                    }
                }, throwable -> {
                    log(throwable.getMessage());
                    loadArticles(page);
                    presenter.showError(presenter, throwable);
                });
    }

    @Override
    public void loadState(Bundle bundle)
    {
        article = bundle.getParcelable("article");
        if (article != null)
        {
            presenter.showArticle(article);
        } else
        {
            presenter.showError(presenter, new Throwable("Article is null"));
        }

        articles = bundle.getParcelableArrayList("articles");
        if (articles != null)
        {
            presenter.showArticles(articles);
        } else
        {
            presenter.showError(presenter, new Throwable("List article is null"));
        }
    }

    @Override
    public void saveState(Bundle bundle)
    {
        log("saveState");
        bundle.putParcelable("article", article);
        bundle.putParcelableArrayList("articles", (ArrayList<Article>) articles);
    }

    public interface Presenter extends BenihController.Presenter
    {
        void showArticle(Article article);

        void showArticles(List<Article> articles);
    }
}
