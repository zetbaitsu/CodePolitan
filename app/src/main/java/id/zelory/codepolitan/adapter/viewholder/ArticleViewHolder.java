package id.zelory.codepolitan.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import id.zelory.benih.adapter.BenihRecyclerAdapter.OnItemClickListener;
import id.zelory.benih.adapter.BenihRecyclerAdapter.OnLongItemClickListener;
import id.zelory.benih.adapter.viewholder.BenihViewHolder;
import id.zelory.benih.view.BenihImageView;
import id.zelory.codepolitan.R;
import id.zelory.codepolitan.model.Article;

/**
 * Created by zetbaitsu on 7/28/15.
 */
public class ArticleViewHolder extends BenihViewHolder<Article>
{
    @Bind(R.id.title) TextView title;
    @Bind(R.id.date) TextView date;
    @Bind(R.id.thumbnail) BenihImageView thumbnail;

    public ArticleViewHolder(View itemView, OnItemClickListener itemClickListener, OnLongItemClickListener longItemClickListener)
    {
        super(itemView, itemClickListener, longItemClickListener);
    }

    @Override
    public void bind(Article article)
    {
        title.setText(article.getTitle());
        date.setText(article.getDate());
        thumbnail.setImageUrl(article.getThumbnail());
    }
}
