package com.jli.addressdialog.adapter;

import android.graphics.Color;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jli.addressdialog.R;
import com.jli.addressdialog.entity.ProvinceEntity;

import java.util.List;

public class Province1Adapter extends BaseQuickAdapter<ProvinceEntity, BaseViewHolder> {
    public Province1Adapter(int layoutResId, @Nullable List<ProvinceEntity> data) {
        super(layoutResId, data);
    }

    public Province1Adapter(@Nullable List<ProvinceEntity> data) {
        super(data);
    }

    public Province1Adapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProvinceEntity item) {
        helper.setText(R.id.textview, item.getName());
        helper.setTextColor(R.id.textview, item.isStatus() ? Color.parseColor("#65C15C") : Color.parseColor("#444444"));
    }
}
