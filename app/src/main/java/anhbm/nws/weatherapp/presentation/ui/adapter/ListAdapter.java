package anhbm.nws.weatherapp.presentation.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import anhbm.nws.weatherapp.R;
import anhbm.nws.weatherapp.domains.model.modeltest;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<modeltest> listtest;
    private Context mContext;

    public ListAdapter(List<modeltest> listtest, Context mContext) {
        this.listtest = listtest;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_number, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        modeltest movie = listtest.get(position);
        holder.txt.setText(movie.getNumber()+"");


    }

    @Override
    public int getItemCount() {
        return listtest.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txt = (TextView) itemView.findViewById(R.id.number);
        }
    }

}
