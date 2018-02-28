package android.skmaury.com.onlinequiz.ViewHolder;

import android.skmaury.com.onlinequiz.Interface.ItemClickListener;
import android.skmaury.com.onlinequiz.R;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by kurosaki on 28/02/18.
 */

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView categoryName;
    public ImageView categoryImage;

    private ItemClickListener itemClickListener;

    public CategoryViewHolder(View itemView) {
        super(itemView);
        categoryImage = itemView.findViewById(R.id.category_image);
        categoryName = itemView.findViewById(R.id.category_name);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onCLick(v, getAdapterPosition(), false);
    }
}
