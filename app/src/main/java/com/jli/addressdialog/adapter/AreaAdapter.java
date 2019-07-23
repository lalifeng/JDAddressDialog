package com.jli.addressdialog.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jli.addressdialog.R;
import com.jli.addressdialog.entity.AreaEntity;

import java.util.List;

public class AreaAdapter extends BaseQuickAdapter<AreaEntity, BaseViewHolder> {
    public AreaAdapter(int layoutResId, @Nullable List<AreaEntity> data) {
        super(layoutResId, data);
    }

    public AreaAdapter(@Nullable List<AreaEntity> data) {
        super(R.layout.item_citylist, data);
    }

    public AreaAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, AreaEntity item) {
        helper.setText(R.id.default_item_city_name_tv, item.getName());
    }
}
