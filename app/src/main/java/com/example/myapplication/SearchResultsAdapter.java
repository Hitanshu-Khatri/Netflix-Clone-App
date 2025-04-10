package com.example.myapplication;
// Replace with your package name

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.ResultViewHolder> {

    private final Context context;
    private List<SearchResult> results;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(SearchResult item);
    }

    public SearchResultsAdapter(Context context, OnItemClickListener listener) {
        this.context = context;
        this.results = new ArrayList<>(); // Initialize with empty list
        this.listener = listener;
    }

    public void updateData(List<SearchResult> newResults) {
        if (newResults == null) {
            this.results = new ArrayList<>();
        } else {
            // Filter out results without posters or essential info if desired
            List<SearchResult> filteredResults = new ArrayList<>();
            for(SearchResult result : newResults) {
                // Example filter: Only show movies/TV with posters
                if (("movie".equals(result.getMediaType()) || "tv".equals(result.getMediaType()))
                        && !TextUtils.isEmpty(result.getPosterPath())) {
                    filteredResults.add(result);
                }
                // You could add 'person' types too if you handle them in the layout
            }
            this.results = filteredResults;
            // this.results = new ArrayList<>(newResults); // Or just take all results
        }
        notifyDataSetChanged(); // Basic notification, consider DiffUtil for better performance
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the list item layout
        View view = LayoutInflater.from(context).inflate(R.layout.item_search_result_list, parent, false);
        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int position) {
        SearchResult item = results.get(position);
        holder.bind(item, listener);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    // --- ViewHolder ---
    class ResultViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewPoster;
        TextView textViewTitle;
        TextView textViewMediaType;
        TextView textViewOverview;

        ResultViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewPoster = itemView.findViewById(R.id.imageViewPoster);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewMediaType = itemView.findViewById(R.id.textViewMediaType);
            textViewOverview = itemView.findViewById(R.id.textViewOverview);
        }

        void bind(final SearchResult item, final OnItemClickListener listener) {
            textViewTitle.setText(item.getDisplayName());
            textViewMediaType.setText(item.getFormattedMediaType());
            textViewOverview.setText(item.getOverview());

            // Load image using Glide
            Glide.with(context)
                    .load(item.getPosterUrl())
                    .placeholder(R.drawable.ic_launcher_background) // Add a placeholder drawable
                    .error(R.drawable.ic_launcher_foreground) // Add an error drawable
                    .transition(DrawableTransitionOptions.withCrossFade()) // Optional fade animation
                    .into(imageViewPoster);

            itemView.setOnClickListener(v -> listener.onItemClick(item));
        }
    }
}
