package com.communitycode.amps.main.settings;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.DialogPreference;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.communitycode.amps.main.R;
import com.communitycode.amps.main.battery.BatteryMethodInterface;
import com.communitycode.amps.main.battery.OfficialBatteryMethod;
import com.communitycode.amps.main.battery.UnofficialBatteryApi;
import com.communitycode.amps.main.battery.UnofficialBatteryMethod;

import java.util.ArrayList;
import java.util.List;

public class UnofficialBatteryApiPreference extends DialogPreference {

    private ArrayList<UnofficialBatteryMethodAdapter.MethodInfo> mEntries;
    private ArrayList<String> mEntryValues;
    private String mValue;
    private String mSummary;
    private int mClickedDialogEntryIndex;
    private boolean mValueSet;

    public UnofficialBatteryApiPreference(Context context, AttributeSet attrs) {
        super(context, attrs);

        mEntries = new ArrayList<>();
        mEntryValues = new ArrayList<>();

        BatteryMethodInterface official = new OfficialBatteryMethod(context);
        UnofficialBatteryMethodAdapter.MethodInfo defaultMethod =
        new UnofficialBatteryMethodAdapter.MethodInfo(context.getString(R.string.xdefault),
            official.read());
        mEntries.add(defaultMethod);
        mEntryValues.add(BatteryMethodPickler.toJson(official));

        for (UnofficialBatteryMethod method : distinct(filterApplicable(UnofficialBatteryApi.methods))) {
            mEntries.add(new UnofficialBatteryMethodAdapter.MethodInfo(method.filePath, method.read()));
            mEntryValues.add(BatteryMethodPickler.toJson(method));
        }


        setPositiveButtonText(null);
        setNegativeButtonText(android.R.string.cancel);

        setDialogIcon(null);
    }

    public static List<UnofficialBatteryMethod> filterApplicable(UnofficialBatteryMethod[] methods) {
        ArrayList<UnofficialBatteryMethod> applicableMethods = new ArrayList<>();
        for (UnofficialBatteryMethod method : methods) {
            if (method.isApplicable(UnofficialBatteryApi.BUILD_MODEL)) {
                applicableMethods.add(method);
            }
        }
        return applicableMethods;
    }

    public static List<UnofficialBatteryMethod> distinct(List<UnofficialBatteryMethod> methods) {
        ArrayList<UnofficialBatteryMethod> uniqueMethods = new ArrayList<>();
        for(UnofficialBatteryMethod a : methods) {
            boolean found = false;
            for (UnofficialBatteryMethod b : uniqueMethods) {
                if (a.equalsIgnoreTransient(b)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                uniqueMethods.add(a);
            }
        }
        return uniqueMethods;
    }

    /**
     * Sets the value of the key. This should be one of the entries in
     * entry values.
     *
     * @param value The value to set for the key.
     */
    public void setValue(String value) {
        // Always persist/notify the first time.
        final boolean changed = !TextUtils.equals(mValue, value);
        if (changed || !mValueSet) {
            mValue = value;
            mValueSet = true;
            persistString(value);
            if (changed) {
                notifyChanged();
            }
        }
    }

    /**
     * Returns the summary of this ListPreference. If the summary
     * has a {@linkplain java.lang.String#format String formatting}
     * marker in it (i.e. "%s" or "%1$s"), then the current entry
     * value will be substituted in its place.
     *
     * @return the summary with appropriate string substitution
     */
    @Override
    public CharSequence getSummary() {
        final UnofficialBatteryMethodAdapter.MethodInfo entry = getEntry();
        if (mSummary == null) {
            return super.getSummary();
        } else {
            return String.format(mSummary, entry == null ? "" : entry.name);
        }
    }

    /**
     * Sets the summary for this Preference with a CharSequence.
     * If the summary has a
     * {@linkplain java.lang.String#format String formatting}
     * marker in it (i.e. "%s" or "%1$s"), then the current entry
     * value will be substituted in its place when it's retrieved.
     *
     * @param summary The summary for the preference.
     */
    @Override
    public void setSummary(CharSequence summary) {
        super.setSummary(summary);
        if (summary == null && mSummary != null) {
            mSummary = null;
        } else if (summary != null && !summary.equals(mSummary)) {
            mSummary = summary.toString();
        }
    }

    /**
     * Returns the value of the key. This should be one of the entries in
     * entry values
     *
     * @return The value of the key.
     */
    public String getValue() {
        return mValue;
    }

    /**
     * Returns the entry corresponding to the current value.
     *
     * @return The entry corresponding to the current value, or null.
     */
    public UnofficialBatteryMethodAdapter.MethodInfo getEntry() {
        int index = getValueIndex();
        return index >= 0 && mEntries != null ? mEntries.get(index) : null;
    }

    /**
     * Returns the index of the given value (in the entry values array).
     *
     * @param value The value whose index should be returned.
     * @return The index of the value, or -1 if not found.
     */
    public int findIndexOfValue(String value) {
        if (value != null && mEntryValues != null) {
            for (int i = mEntryValues.size() - 1; i >= 0; i--) {
                if (mEntryValues.get(i).equals(value)) {
                    return i;
                }
            }
        }
        return -1;
    }

    private int getValueIndex() {
        return findIndexOfValue(mValue);
    }

    @Override
    protected void onPrepareDialogBuilder(AlertDialog.Builder builder) {
        super.onPrepareDialogBuilder(builder);

        if (mEntries == null || mEntryValues == null) {
            throw new IllegalStateException(
                    "ListPreference requires an entries array and an entryValues array.");
        }

        Context context = builder.getContext();
        RecyclerView mRecyclerView = new RecyclerView(context);
        RecyclerView.LayoutManager mLayoutManager;

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mClickedDialogEntryIndex = getValueIndex();
        final UnofficialBatteryMethodAdapter mAdapter = new UnofficialBatteryMethodAdapter(mEntries, mClickedDialogEntryIndex);
        mAdapter.setOnClickListener(new UnofficialBatteryMethodAdapter.OnClickHandler() {
            public void onClick(int position) {
                UnofficialBatteryApiPreference that = UnofficialBatteryApiPreference.this;

                mAdapter.setCheckedPosition(position);
                that.mClickedDialogEntryIndex = that.mClickedDialogEntryIndex == position ? -1 : position;

                Dialog dialog = that.getDialog();
                that.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                dialog.dismiss();
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        builder.setView(mRecyclerView);

        builder.setPositiveButton(null, null);
    }


    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);

        if (positiveResult && mClickedDialogEntryIndex >= 0 && mEntryValues != null) {
            String value = mEntryValues.get(mClickedDialogEntryIndex);
            if (callChangeListener(value)) {
                setValue(value);
            }
        }
        // Same item was clicked. Deselect item
        if (positiveResult && mClickedDialogEntryIndex == -1) {
            String value = null;
            if (callChangeListener(value)) {
                setValue(value);
            }
        }
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return a.getString(index);
    }

    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
        setValue(restoreValue ? getPersistedString(mValue) : (String) defaultValue);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        final Parcelable superState = super.onSaveInstanceState();
        if (isPersistent()) {
            // No need to save instance state since it's persistent
            return superState;
        }

        final SavedState myState = new SavedState(superState);
        myState.value = getValue();
        return myState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state == null || !state.getClass().equals(SavedState.class)) {
            // Didn't save state for us in onSaveInstanceState
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState myState = (SavedState) state;
        super.onRestoreInstanceState(myState.getSuperState());
        setValue(myState.value);
    }

    private static class SavedState extends BaseSavedState {
        String value;

        public SavedState(Parcel source) {
            super(source);
            value = source.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeString(value);
        }

        public SavedState(Parcelable superState) {
            super(superState);
        }

        public static final Parcelable.Creator<SavedState> CREATOR =
                new Parcelable.Creator<SavedState>() {
                    public SavedState createFromParcel(Parcel in) {
                        return new SavedState(in);
                    }

                    public SavedState[] newArray(int size) {
                        return new SavedState[size];
                    }
                };
    }
}