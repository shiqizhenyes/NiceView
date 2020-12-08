package me.nice.samples.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import me.nice.samples.R;
import me.nice.samples.bean.MainView;
import me.nice.samples.inter.OnItemClickListener;

public class MainViewAdapter extends RecyclerView.Adapter<MainViewAdapter.MainViewViewHolder> {

    private List<MainView> mainViews;

    public void setMainViews(List<MainView> mainViews) {
        this.mainViews = mainViews;
    }

    private OnItemClickListener<MainView> onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener<MainView> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public MainViewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.main_view_item, viewGroup, false);
        return new MainViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewViewHolder mainViewViewHolder, int i) {
        mainViewViewHolder.mainViewName.setText(mainViews.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return mainViews == null ? 0 : mainViews.size();
    }


    class MainViewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private TextView mainViewName;
        private ImageButton mainViewButton;

        MainViewViewHolder(@NonNull View itemView) {
            super(itemView);
            mainViewButton = itemView.findViewById(R.id.mainViewButton);
            mainViewName = itemView.findViewById(R.id.mainViewName);
            mainViewButton.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            if (v.getId() == R.id.mainViewButton) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, getAdapterPosition(), mainViews.get(getAdapterPosition()));
                }
            }

        }
    }

}
