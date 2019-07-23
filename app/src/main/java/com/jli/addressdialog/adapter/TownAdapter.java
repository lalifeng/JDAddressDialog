package com.jli.addressdialog.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jli.addressdialog.R;
import com.jli.addressdialog.entity.TownEntity;

import java.util.List;

public class TownAdapter extends BaseQuickAdapter<TownEntity, BaseViewHolder> {
    public TownAdapter(int layoutResId, @Nullable List<TownEntity> data) {
        super(layoutResId, data);
    }

    public TownAdapter(@Nullable List<TownEntity> data) {
        super(R.layout.item_citylist, data);
    }

    public TownAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, TownEntity item) {
        helper.setText(R.id.default_item_city_name_tv, item.getName());
    }
}
