package id.zelory.codepolitan.fragment;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;

import java.util.List;

import id.zelory.benih.controller.Controller;
import id.zelory.benih.fragment.BenihFragment;
import id.zelory.benih.view.BenihImageView;
import id.zelory.codepolitan.R;
import id.zelory.codepolitan.controller.ArticleController;
import id.zelory.codepolitan.model.Article;

/**
 * Created by zetbaitsu on 7/28/15.
 */
public class ReadFragment extends BenihFragment<Article> implements ArticleController.Presenter
{
    private ArticleController articleController;
    private BenihImageView image;
    private TextView date;
    private TextView title;
    private WebView content;

    @Override
    protected int getFragmentView()
    {
        return R.layout.fragment_read;
    }

    @Override
    protected void onViewReady(Bundle bundle, View view)
    {
        articleController = new ArticleController(this);
        image = (BenihImageView) view.findViewById(R.id.image);
        date = (TextView) view.findViewById(R.id.date);
        title = (TextView) view.findViewById(R.id.title);
        content = (WebView) view.findViewById(R.id.content);
        content.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        content.setVerticalScrollBarEnabled(false);
        content.setHorizontalScrollBarEnabled(false);
        content.setWebChromeClient(new WebChromeClient());

        if (bundle != null)
        {
            articleController.loadState(bundle);
        } else
        {
            articleController.loadArticle(data.getId());
        }
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
        image.setImageUrl(article.getThumbnail());
        date.setText(article.getDate());
        title.setText(article.getTitle());
        content.loadData(article.getContent(), "text/html", "UTF-8");
    }

    @Override
    public void showArticles(List<Article> articles)
    {

    }

    @Override
    public void showError(Controller.Presenter presenter, Throwable throwable)
    {

    }
}
