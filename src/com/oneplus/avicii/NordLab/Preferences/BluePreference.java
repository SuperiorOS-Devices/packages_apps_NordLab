/*
 * Copyright (C) 2016 The OmniROM Project
 *               2022 The Evolution X Project
 * SPDX-License-Identifier: GPL-2.0-or-later
 */

package com.oneplus.avicii.NordLab.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceViewHolder;
import android.util.AttributeSet;

import com.oneplus.avicii.NordLab.kcal.KcalSettings;
import com.oneplus.avicii.NordLab.FileUtils;
import com.oneplus.avicii.NordLab.R;

public class BluePreference extends CustomSeekBarPreference {

    private static int mDefVal;

    private static final int NODE = R.string.node_blue_preference;

    public BluePreference(Context context, AttributeSet attrs) {
        super(context, attrs);

        mInterval = context.getResources().getInteger(R.integer.kcal_blue_preference_interval);
        mShowSign = false;
        mUnits = "";
        mContinuousUpdates = false;

        int[] mAllValues = context.getResources().getIntArray(R.array.kcal_blue_preference_array);
        mMinValue = mAllValues[1];
        mMaxValue = mAllValues[2];
        mDefaultValueExists = true;
        mDefVal = mAllValues[0];
        mDefaultValue = mDefVal;
        mValue = Integer.parseInt(loadValue(context));

        setPersistent(false);
    }

    private static String getFile(Context context) {
        String file = context.getString(NODE);
        if (FileUtils.fileWritable(file)) {
            return file;
        }
        return null;
    }

    public static boolean isSupported(Context context) {
        return FileUtils.fileWritable(getFile(context));
    }

    public static void restore(Context context) {
        if (!isSupported(context)) {
            return;
        }

        int[] mAllValues = context.getResources().getIntArray(R.array.kcal_blue_preference_array);
        mDefVal = mAllValues[0];
        String storedValue = PreferenceManager.getDefaultSharedPreferences(context).getString(KcalSettings.KEY_BLUE, String.valueOf(mDefVal));
        FileUtils.writeValue(getFile(context), storedValue);
    }

    public static String loadValue(Context context) {
        return FileUtils.getFileValue(getFile(context), String.valueOf(mDefVal));
    }

    private void saveValue(String newValue) {
        FileUtils.writeValue(getFile(getContext()), newValue);
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
        editor.putString(KcalSettings.KEY_BLUE, newValue);
        editor.apply();
    }

    @Override
    protected void changeValue(int newValue) {
        saveValue(String.valueOf(newValue));
    }
}
