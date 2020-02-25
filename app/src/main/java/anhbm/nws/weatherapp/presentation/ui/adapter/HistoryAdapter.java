package anhbm.nws.weatherapp.presentation.ui.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import anhbm.nws.weatherapp.R;
import anhbm.nws.weatherapp.application.SQLiteWeather.SqlDatabase;
import anhbm.nws.weatherapp.presentation.presenters.onClickRecy.ItemOnClick;
import anhbm.nws.weatherapp.presentation.ui.screen.history.mvp.HistoryModel;
import anhbm.nws.weatherapp.presentation.ui.screen.searchCity.AboutActivity;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.holder> {
    private Context mContext;
    private List<HistoryModel> historyModelList;
    private SqlDatabase sqlDatabase;
    private String pho;
    private static final String IS_DEGREE = "IS_DEGREE";
    private static final String IS_KELVIN = "IS_KELVIN";

    public HistoryAdapter(Context mContext, List<HistoryModel> historyModelList, SqlDatabase sqlDatabase) {
        this.mContext = mContext;
        this.historyModelList = historyModelList;
        this.sqlDatabase = sqlDatabase;
    }

    @NonNull
    @Override
    public HistoryAdapter.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.history_adapter, parent, false);
        return new HistoryAdapter.holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.holder holder, final int position) {
        final HistoryModel historyModel = historyModelList.get(position);
        holder.chitiet.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        holder.xoa.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        holder.tvgio.setText(historyModel.getGiohientai());
        holder.tvngay.setText(historyModel.getNgayhientai());
        pho = historyModel.getThanhpho();
        holder.tvthanhpho.setText(pho);
        holder.tvdogio.setText(historyModel.getDogioDeg());
        holder.tvtocdogio.setText(historyModel.getTocdogioSpeed());
        holder.tvdoam.setText(historyModel.getDoamHumidity() + "%");
        holder.tvtrangthai.setText(historyModel.getTrangthaiDescription());
        String icon = historyModel.getIconSql();
        Picasso.with(mContext).load("http://api.openweathermap.org/img/w/" + icon + ".png").into(holder.iconviewHistory);

        holder.tvnhietdo.setText(String.valueOf(historyModel.getNhietDoTemp()).substring(0, 2));
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("key", mContext.MODE_PRIVATE);

        holder.xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                View view1 = LayoutInflater.from(mContext).inflate(R.layout.dialog_xoa, null);
                builder.setView(view1);
                builder.setTitle("Bạn Có Chắc  Chắn Muốn Xóa Lịch Sử Tìm Kiếm Thành Phố ");
                final AlertDialog dialog = builder.show();
                Button buttonXoa, buttonThoat;
                buttonXoa = dialog.findViewById(R.id.xoa_diag);
                buttonThoat = dialog.findViewById(R.id.thoat_diag);
                buttonXoa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sqlDatabase.deleteHistory(historyModelList.get(position).getGiohientai());
                        historyModelList.get(position);
                        historyModelList.remove(historyModel);
                        notifyDataSetChanged();
                        Toast.makeText(mContext, "Đã Xóa Thành Công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                buttonThoat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

            }
        });
        holder.chitiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                View view1 = LayoutInflater.from(mContext).inflate(R.layout.dialog_chitiet, null);
                builder.setView(view1);
                final AlertDialog dialog = builder.show();
                TextView gio, ngay, dogio, tocdogio, nhietdo, trangthai, doam, thanhpho;
                ImageView imageView;
                imageView = dialog.findViewById(R.id.thoat_chitiet);
                gio = dialog.findViewById(R.id.gio_chitiet);
                ngay = dialog.findViewById(R.id.ngay_chitiet);
                nhietdo = dialog.findViewById(R.id.nhietdo_chitiet);
                thanhpho = dialog.findViewById(R.id.thanhpho_chitiet);
                dogio = dialog.findViewById(R.id.dogio_chitiet);
                tocdogio = dialog.findViewById(R.id.tocdogio_chitiet);
                doam = dialog.findViewById(R.id.doam_chitiet);
                trangthai = dialog.findViewById(R.id.trangthai_chitiet);
                gio.setText(historyModel.getGiohientai());
                ngay.setText(historyModel.getNgayhientai());
                nhietdo.setText(String.valueOf(historyModel.getNhietDoTemp()).substring(0, 2) + "ºC");
                thanhpho.setText(historyModel.getThanhpho());
                dogio.setText(historyModel.getDogioDeg());
                tocdogio.setText(historyModel.getTocdogioSpeed() + "m/h");
                doam.setText(historyModel.getDoamHumidity() + "%");
                trangthai.setText(historyModel.getTrangthaiDescription());

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        holder.setItem(new ItemOnClick() {
            @Override
            public void onClick(View view, int i, boolean isLongClick) {
                Intent intent = new Intent(mContext, AboutActivity.class);
                intent.putExtra("timthanhpho", pho);
                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return (historyModelList != null) ? historyModelList.size() : 0;
    }

    public class holder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView tvngay, tvgio, tvdogio, tvtocdogio, tvnhietdo, tvtrangthai, tvdoam, chitiet, xoa, tvthanhpho;
        ImageView iconviewHistory;
        private ItemOnClick itemOnClick;

        public holder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            tvgio = itemView.findViewById(R.id.gio_history);
            tvngay = itemView.findViewById(R.id.ngay_history);
            tvthanhpho = itemView.findViewById(R.id.thanhpho_history);
            tvdogio = itemView.findViewById(R.id.dogioText_history);
            tvtocdogio = itemView.findViewById(R.id.tocdogioText_history);
            tvnhietdo = itemView.findViewById(R.id.nhietdo_hitory);
            tvtrangthai = itemView.findViewById(R.id.trangthai_history);
            tvdoam = itemView.findViewById(R.id.doamText_history);
            iconviewHistory = itemView.findViewById(R.id.icon_history);
            chitiet = itemView.findViewById(R.id.chitiet_history);
            xoa = itemView.findViewById(R.id.xoa_history);
        }

        public void setItem(ItemOnClick item) {
            this.itemOnClick = item;
        }

        @Override
        public void onClick(View view) {
            itemOnClick.onClick(view, getAdapterPosition(), false);

        }

        @Override
        public boolean onLongClick(View view) {
            itemOnClick.onClick(view, getAdapterPosition(), true);
            return true;
        }
    }
}
