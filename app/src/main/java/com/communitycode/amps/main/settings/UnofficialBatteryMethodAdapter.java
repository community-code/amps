package com.communitycode.amps.main.settings;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.communitycode.amps.main.R;

import java.util.ArrayList;

public class UnofficialBatteryMethodAdapter extends RecyclerView.Adapter<UnofficialBatteryMethodAdapter.ViewHolder> {
    private int mCheckedPosition;
    private ArrayList<MethodInfo> mDataset;
    private OnClickHandler mOnClickListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final CheckBox mCheckBox;
        // each data item is just a string in this case
        public View mView;
        public TextView mFilePath;
        public TextView mCurrentValue;
        
        public ViewHolder(View v) {
            super(v);
            mFilePath = v.findViewById(R.id.filePath);
            mCheckBox = v.findViewById(R.id.checkbox);
            mCurrentValue = v.findViewById(R.id.currentValue);
            mView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public UnofficialBatteryMethodAdapter(ArrayList<MethodInfo> myDataset, int checkedPosition) {
        mDataset = myDataset;
        mCheckedPosition = checkedPosition;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public UnofficialBatteryMethodAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                        int viewType) {
        // create a new view
        Context context = parent.getContext();
        LayoutInflater li = LayoutInflater.from(context);
        View view = li.inflate(R.layout.unofficialbatteryapi_dialog, parent, false);
        return new ViewHolder(view);
    }

    public void setOnClickListener(OnClickHandler onClickListener) {
        mOnClickListener = onClickListener;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        Context context = holder.mView.getContext();
        MethodInfo method = mDataset.get(position);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                if (mOnClickListener != null) {
                    mOnClickListener.onClick(holder.getAdapterPosition());
                }
            }
        };

        holder.mView.setOnClickListener(onClickListener);

        holder.mFilePath.setText(method.name);


        Integer val = method.value;
        if (val == null) {
            val = 0;
        }

        holder.mCurrentValue.setText(context.getString(R.string.value, val));

        holder.mCheckBox.setChecked(mCheckedPosition == position);
        holder.mCheckBox.setOnClickListener(onClickListener);
    }

    public void setCheckedPosition(int position) {
        this.notifyItemChanged(mCheckedPosition);
        mCheckedPosition = position;
        this.notifyItemChanged(position);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface OnClickHandler {
        void onClick(int position);
    }

    public static class MethodInfo {
        public String name;
        public Integer value;

        MethodInfo(String name, Integer value) {
            this.name = name;
            this.value = value;
        }
    }
}