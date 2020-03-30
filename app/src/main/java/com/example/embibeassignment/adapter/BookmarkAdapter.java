package com.example.embibeassignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.embibeassignment.R;
import com.example.embibeassignment.model.Result;
import com.example.embibeassignment.ui.MainActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder> {

    private List<Result> data;
    private Context context;
    public static final String IMAGE_URL_BASE_PATH = "http://image.tmdb.org/t/p/w342//";


    public BookmarkAdapter(Context context, List<Result> list) {

        this.context = context;
        this.data = list;
        setHasStableIds(true);
    }

        @NonNull
        @Override
        public BookmarkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.view_repo_list_item, parent, false);
            return new BookmarkViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull BookmarkViewHolder holder, int position) {
            holder.bind(data.get(position));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

    class BookmarkViewHolder extends RecyclerView.ViewHolder {

        TextView textTitle;
        ImageView imagePoster;
        ImageView imageBookmark;

        BookmarkViewHolder(View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.text_title);
            imagePoster = itemView.findViewById(R.id.image_poster);
            imageBookmark = itemView.findViewById(R.id.image_bookmark);
        }

        void bind(final Result result) {
            textTitle.setText(result.getTitle());
            String imageUrl = IMAGE_URL_BASE_PATH + result.getPoster_path();
            Glide.with(context).load(imageUrl).override(200, 100).into(imagePoster);
            imageBookmark.setVisibility(View.GONE);
        }
    }

}
