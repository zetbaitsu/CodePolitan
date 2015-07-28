package id.zelory.codepolitan.fragment;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;

import id.zelory.benih.fragments.BenihFragment;
import id.zelory.benih.utils.BenihScheduler;
import id.zelory.benih.views.BenihImageView;
import id.zelory.codepolitan.R;
import id.zelory.codepolitan.model.Article;
import id.zelory.codepolitan.network.CodePolitanService;
import rx.Subscription;

/**
 * Created by zetbaitsu on 7/28/15.
 */
public class ReadFragment extends BenihFragment<Article>
{
    private BenihImageView image;
    private TextView date;
    private TextView title;
    private WebView content;
    private Article article;
    private Subscription subscription;

    @Override
    protected int getFragmentView()
    {
        return R.layout.fragment_read;
    }

    @Override
    protected void onViewReady(Bundle bundle, View view)
    {
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
            article = bundle.getParcelable("article");
            bind(article);
        } else
        {
            getDetailArticle(data);
        }
    }

    private void getDetailArticle(Article data)
    {
        subscription = CodePolitanService.getApi()
                .getDetailArticle(data.getId())
                .compose(BenihScheduler.applySchedulers(BenihScheduler.Type.IO))
                .subscribe(articleResponse -> {
                    if (articleResponse.getCode())
                    {
                        article = articleResponse.getResult();
                        bind(article);
                    }
                }, throwable -> log(throwable.getMessage()));
    }

    private void bind(Article article)
    {
        image.setImageUrl(article.getThumbnail());
        date.setText(article.getDate());
        title.setText(article.getTitle());
        content.loadData(article.getContent(), "text/html", "UTF-8");
    }


    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        outState.putParcelable("article", article);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy()
    {
        if (subscription != null)
        {
            subscription.unsubscribe();
            subscription = null;
        }
        super.onDestroy();
    }
}
