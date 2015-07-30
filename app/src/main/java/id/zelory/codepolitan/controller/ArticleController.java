package id.zelory.codepolitan.controller;

import android.os.Bundle;

import java.util.List;

import id.zelory.benih.controller.Controller;
import id.zelory.benih.util.BenihScheduler;
import id.zelory.codepolitan.model.Article;
import id.zelory.codepolitan.network.CodePolitanService;

/**
 * Created by zetbaitsu on 7/29/15.
 */
public class ArticleController extends Controller<ArticleController.Presenter>
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
                        articles = articleResponse.getResult();
                        presenter.showArticles(articles);
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
            presenter.showError(presenter, new Throwable("Error"));
        }

        articles = bundle.getParcelableArrayList("articles");
        if (articles != null)
        {
            presenter.showArticles(articles);
        } else
        {
            presenter.showError(presenter, new Throwable("Error"));
        }
    }

    @Override
    public void saveState(Bundle bundle)
    {
        log("saveState");
        bundle.putParcelable("article", article);
    }

    public interface Presenter extends Controller.Presenter
    {
        void showArticle(Article article);

        void showArticles(List<Article> articles);
    }
}
