package com.abdeveloper.library;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

class MutliSelectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<MultiSelectModel> mDataSet = new ArrayList<>();
    private String mSearchQuery = "";
    private Context mContext;
    int TYPE_ITEM = 0;
    int TYPE_HEADER = 1;

    MutliSelectAdapter(ArrayList<MultiSelectModel> dataSet, Context context) {
        this.mDataSet = dataSet;
        this.mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0)
            return TYPE_HEADER;
        else
            return TYPE_ITEM;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType==TYPE_HEADER){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_item, parent, false);
            AppCompatCheckBox v = (AppCompatCheckBox) view.findViewById(R.id.dialog_item_checkbox);
            return new HeaderViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.multi_select_item, parent, false);
            AppCompatCheckBox v = (AppCompatCheckBox) view.findViewById(R.id.dialog_item_checkbox);
            return new MultiSelectDialogViewHolder(view);
        }


    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int pos) {
        int position = pos-1;
        if (getItemViewType(pos)==TYPE_HEADER){
            final HeaderViewHolder holder = (HeaderViewHolder) viewHolder;
            holder.dialog_item_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        selectAll();
                    } else {
                        unSelectAll();
                    }
                }
            });

        } else {
            final MultiSelectDialogViewHolder holder = (MultiSelectDialogViewHolder) viewHolder;
        if (!mSearchQuery.equals("") && mSearchQuery.length() > 1) {
            setHighlightedText(position, holder.dialog_name_item);
        } else {
            holder.dialog_name_item.setText(mDataSet.get(position).getName());
        }

        if (mDataSet.get(position).getSelected()) {

            if (!MultiSelectDialog.selectedIdsForCallback.contains(mDataSet.get(position).getId())) {
                MultiSelectDialog.selectedIdsForCallback.add(mDataSet.get(position).getId());
            }
        }

        if (checkForSelection(mDataSet.get(position).getId())) {
            holder.dialog_item_checkbox.setChecked(true);
        } else {
            holder.dialog_item_checkbox.setChecked(false);
        }

        /*holder.dialog_item_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (holder.dialog_item_checkbox.isChecked()) {
                    MultiSelectDialog.selectedIdsForCallback.add(mDataSet.get(holder.getAdapterPosition()).getId());
                    holder.dialog_item_checkbox.setChecked(true);
                } else {
                    removeFromSelection(mDataSet.get(holder.getAdapterPosition()).getId());
                    holder.dialog_item_checkbox.setChecked(false);
                }
            }
        });*/


        holder.main_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               int adapterposition = holder.getAdapterPosition()-1;
                if (!holder.dialog_item_checkbox.isChecked()) {
                    MultiSelectDialog.selectedIdsForCallback.add(mDataSet.get(adapterposition)
                            .getId());
                    holder.dialog_item_checkbox.setChecked(true);
                    mDataSet.get(adapterposition).setSelected(true);
                    notifyItemChanged(adapterposition);
                } else {
                    removeFromSelection(mDataSet.get(adapterposition).getId());
                    holder.dialog_item_checkbox.setChecked(false);
                    mDataSet.get(adapterposition).setSelected(false);
                    notifyItemChanged(adapterposition);
                }
            }
        });
        }
    }

    private void setHighlightedText(int position, TextView textview) {
        String name = mDataSet.get(position).getName();
        SpannableString str = new SpannableString(name);
        int endLength = name.toLowerCase().indexOf(mSearchQuery) + mSearchQuery.length();
        ColorStateList highlightedColor = new ColorStateList(new int[][]{new int[]{}}, new int[]{ContextCompat.getColor(mContext, R.color.colorAccent)});
        TextAppearanceSpan textAppearanceSpan = new TextAppearanceSpan(null, Typeface.NORMAL, -1, highlightedColor, null);
        str.setSpan(textAppearanceSpan, name.toLowerCase().indexOf(mSearchQuery), endLength, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textview.setText(str);
    }

    private void removeFromSelection(String id) {
        for (int i = 0; i < MultiSelectDialog.selectedIdsForCallback.size(); i++) {
            if (id.equals(MultiSelectDialog.selectedIdsForCallback.get(i))) {
                MultiSelectDialog.selectedIdsForCallback.remove(i);
            }
        }
    }


    private boolean checkForSelection(String id) {
        for (int i = 0; i < MultiSelectDialog.selectedIdsForCallback.size(); i++) {
            if (id.equals(MultiSelectDialog.selectedIdsForCallback.get(i))) {
                return true;
            }
        }
        return false;
    }

    private void selectAll(){
        MultiSelectDialog.selectedIdsForCallback.clear();

        for (MultiSelectModel selectModel : mDataSet){
            selectModel.setSelected(true);
            MultiSelectDialog.selectedIdsForCallback.add(selectModel.getId());
        }
        notifyDataSetChanged();
    }

    private void unSelectAll(){
        MultiSelectDialog.selectedIdsForCallback.clear();

        for (MultiSelectModel selectModel:mDataSet){
            selectModel.setSelected(false);
        }
        notifyDataSetChanged();
    }



    /*//get selected name string seperated by coma
    public String getDataString() {
        String data = "";
        for (int i = 0; i < mDataSet.size(); i++) {
            if (checkForSelection(mDataSet.get(i).getId())) {
                data = data + ", " + mDataSet.get(i).getName();
            }
        }
        if (data.length() > 0) {
            return data.substring(1);
        } else {
            return "";
        }
    }

    //get selected name ararylist
    public ArrayList<String> getSelectedNameList() {
        ArrayList<String> names = new ArrayList<>();
        for (int i = 0; i < mDataSet.size(); i++) {
            if (checkForSelection(mDataSet.get(i).getId())) {
                names.add(mDataSet.get(i).getName());
            }
        }
        //  return names.toArray(new String[names.size()]);
        return names;
    }*/


    @Override
    public int getItemCount() {
        return mDataSet.size()+1;
    }


    void setData(ArrayList<MultiSelectModel> data, String query, MutliSelectAdapter mutliSelectAdapter) {
        this.mDataSet = data;
        this.mSearchQuery = query;
        mutliSelectAdapter.notifyDataSetChanged();
    }

    class MultiSelectDialogViewHolder extends RecyclerView.ViewHolder {
        private TextView dialog_name_item;
        private AppCompatCheckBox dialog_item_checkbox;
        private LinearLayout main_container;



        MultiSelectDialogViewHolder(View view) {
            super(view);
            dialog_name_item = (TextView) view.findViewById(R.id.dialog_item_name);
            dialog_item_checkbox = (AppCompatCheckBox) view.findViewById(R.id.dialog_item_checkbox);
            main_container = (LinearLayout) view.findViewById(R.id.main_container);
        }

    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        private TextView dialog_name_item;
        private AppCompatCheckBox dialog_item_checkbox;
        private LinearLayout main_container;

        HeaderViewHolder(View view) {
            super(view);
            dialog_name_item = (TextView) view.findViewById(R.id.dialog_item_name);
            dialog_item_checkbox = (AppCompatCheckBox) view.findViewById(R.id.dialog_item_checkbox);
            main_container = (LinearLayout) view.findViewById(R.id.main_container);
        }
    }
}
