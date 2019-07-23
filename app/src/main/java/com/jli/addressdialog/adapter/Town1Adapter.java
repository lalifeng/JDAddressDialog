package com.jli.addressdialog.adapter;

import android.graphics.Color;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jli.addressdialog.R;
import com.jli.addressdialog.entity.TownEntity;

import java.util.List;

public class Town1Adapter extends BaseQuickAdapter<TownEntity, BaseViewHolder> {
    public Town1Adapter(int layoutResId, @Nullable List<TownEntity> data) {
        super(layoutResId, data);
    }

    public Town1Adapter(@Nullable List<TownEntity> data) {
        super(R.layout.item_address, data);
    }

    public Town1Adapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, TownEntity item) {
        helper.setText(R.id.textview, item.getName());
        helper.setTextColor(R.id.textview, item.isStatus() ? Color.parseColor("#65C15C") : Color.parseColor("#444444"));
    }
}
