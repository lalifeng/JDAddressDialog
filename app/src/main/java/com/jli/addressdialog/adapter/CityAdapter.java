package com.jli.addressdialog.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jli.addressdialog.R;
import com.jli.addressdialog.entity.CityEntity;

import java.util.List;

public class CityAdapter extends BaseQuickAdapter<CityEntity, BaseViewHolder> {
    public CityAdapter(int layoutResId, @Nullable List<CityEntity> data) {
        super(layoutResId, data);
    }

    public CityAdapter(@Nullable List<CityEntity> data) {
        super(R.layout.item_citylist, data);
    }

    public CityAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, CityEntity item) {
        helper.setText(R.id.default_item_city_name_tv, item.getName());
    }
}
