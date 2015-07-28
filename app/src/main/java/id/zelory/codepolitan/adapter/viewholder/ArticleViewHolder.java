package id.zelory.codepolitan.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import id.zelory.benih.adapters.BenihRecyclerAdapter.OnItemClickListener;
import id.zelory.benih.adapters.BenihRecyclerAdapter.OnLongItemClickListener;
import id.zelory.benih.adapters.BenihViewHolder;
import id.zelory.benih.views.BenihImageView;
import id.zelory.codepolitan.R;
import id.zelory.codepolitan.model.Article;

/**
 * Created by zetbaitsu on 7/28/15.
 */
public class ArticleViewHolder extends BenihViewHolder
{
    private final TextView title;
    private final TextView date;
    private final BenihImageView thumbnail;

    public ArticleViewHolder(View itemView, OnItemClickListener itemClickListener, OnLongItemClickListener longItemClickListener)
    {
        super(itemView, itemClickListener, longItemClickListener);
        title = (TextView) itemView.findViewById(R.id.title);
        date = (TextView) itemView.findViewById(R.id.date);
        thumbnail = (BenihImageView) itemView.findViewById(R.id.thumbnail);
    }

    public void load(Article article)
    {
        title.setText(article.getTitle());
        date.setText(article.getDate());
        thumbnail.setImageUrl(article.getThumbnail());
    }
}
