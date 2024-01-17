package com.example.cookitright.callback;

import com.example.cookitright.datamodels.AllCookData;

public interface CooksCallback {
    void onReadClick(AllCookData cookData, boolean isChecked);
    void onCurrentlyClick(AllCookData cookData, boolean b);
    void onFinishedClick(AllCookData cookData, boolean b);
    void onCookClick(AllCookData cookData);
}
