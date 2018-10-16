package com.example.vitorizkiimanda.dictionaryvri;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DictionaryAdapter extends RecyclerView.Adapter<DictionaryAdapter.DictionaryHolder> {
    String TAG = "adapter";
    private ArrayList<DictionaryModel> mData = new ArrayList<>();
    private Context context;
    private LayoutInflater mInflater;

    public DictionaryAdapter(Context context) {
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public void setmData(ArrayList<DictionaryModel> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    @Override
    public DictionaryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dictionary_row, parent, false);
        return new DictionaryHolder(view);
    }
    public void addItem(ArrayList<DictionaryModel> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(DictionaryHolder holder, int position) {
        holder.textViewWord.setText(mData.get(position).getOriginal_word());
        holder.textViewMean.setText(mData.get(position).getContent_word());
        Log.d(TAG, "datanya");
    }
    @Override
    public int getItemViewType(int position) {
        return 0;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class DictionaryHolder extends RecyclerView.ViewHolder {
        public TextView textViewWord;
        public TextView textViewMean;
        public DictionaryHolder(View itemView) {
            super(itemView);
            textViewWord = (TextView) itemView.findViewById(R.id.word);
            textViewMean = (TextView) itemView.findViewById(R.id.mean);
        }
    }
}
