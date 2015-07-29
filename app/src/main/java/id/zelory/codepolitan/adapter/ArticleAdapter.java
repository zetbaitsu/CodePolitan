package id.zelory.codepolitan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import id.zelory.benih.adapters.BenihRecyclerAdapter;
import id.zelory.codepolitan.R;
import id.zelory.codepolitan.adapter.viewholder.ArticleViewHolder;
import id.zelory.codepolitan.model.Article;

/**
 * Created by zetbaitsu on 7/28/15.
 */
public class ArticleAdapter extends BenihRecyclerAdapter<Article, ArticleViewHolder>
{
    public ArticleAdapter(Context context)
    {
        super(context);
    }

    @Override
    protected int getItemView(int i)
    {
        return R.layout.item_article;
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        return new ArticleViewHolder(getView(viewGroup, i), itemClickListener, longItemClickListener);
    }
}
