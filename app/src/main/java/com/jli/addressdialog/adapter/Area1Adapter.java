package com.jli.addressdialog.adapter;

import android.graphics.Color;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jli.addressdialog.R;
import com.jli.addressdialog.entity.AreaEntity;

import java.util.List;

public class Area1Adapter extends BaseQuickAdapter<AreaEntity, BaseViewHolder> {
    public Area1Adapter(int layoutResId, @Nullable List<AreaEntity> data) {
        super(layoutResId, data);
    }

    public Area1Adapter(@Nullable List<AreaEntity> data) {
        super(R.layout.item_address, data);
    }

    public Area1Adapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, AreaEntity item) {
        helper.setText(R.id.textview, item.getName());
        helper.setTextColor(R.id.textview, item.isStatus() ? Color.parseColor("#65C15C") : Color.parseColor("#444444"));
    }
}
