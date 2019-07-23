package com.jli.addressdialog.adapter;

import android.graphics.Color;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jli.addressdialog.R;
import com.jli.addressdialog.entity.CityEntity;

import java.util.List;

public class City1Adapter extends BaseQuickAdapter<CityEntity, BaseViewHolder> {
    public City1Adapter(int layoutResId, @Nullable List<CityEntity> data) {
        super(layoutResId, data);
    }

    public City1Adapter(@Nullable List<CityEntity> data) {
        super(R.layout.item_address, data);
    }

    public City1Adapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, CityEntity item) {
        helper.setText(R.id.textview, item.getName());
        helper.setTextColor(R.id.textview, item.isStatus() ? Color.parseColor("#65C15C") : Color.parseColor("#444444"));
    }
}
