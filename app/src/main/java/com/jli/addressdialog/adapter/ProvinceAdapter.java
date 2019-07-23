package com.jli.addressdialog.adapter;

import android.view.View;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jli.addressdialog.R;
import com.jli.addressdialog.entity.ProvinceEntity;

import java.util.List;

public class ProvinceAdapter extends BaseQuickAdapter<ProvinceEntity, BaseViewHolder> {
    public ProvinceAdapter(int layoutResId, @Nullable List<ProvinceEntity> data) {
        super(layoutResId, data);
    }

    public ProvinceAdapter(@Nullable List<ProvinceEntity> data) {
        super(R.layout.item_citylist, data);
    }

    public ProvinceAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProvinceEntity item) {
        helper.setText(R.id.default_item_city_name_tv, item.getName());
    }
}
