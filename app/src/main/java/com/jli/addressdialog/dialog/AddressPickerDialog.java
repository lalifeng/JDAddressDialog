package com.jli.addressdialog.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.jli.addressdialog.R;
import com.jli.addressdialog.adapter.Area1Adapter;
import com.jli.addressdialog.adapter.City1Adapter;
import com.jli.addressdialog.adapter.Province1Adapter;
import com.jli.addressdialog.adapter.Town1Adapter;
import com.jli.addressdialog.adapter.ViewPagerAdapter;
import com.jli.addressdialog.entity.AreaEntity;
import com.jli.addressdialog.entity.CityEntity;
import com.jli.addressdialog.entity.ProvinceEntity;
import com.jli.addressdialog.entity.TownEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 仿京东实际地址选择弹窗
 */
public class AddressPickerDialog extends Dialog {
    private AreaPickerViewCallback areaPickerViewCallback;
    private Context mContext;
    private List<ProvinceEntity> mProvinceList;//省列表
    private List<CityEntity> mCityList = new ArrayList<>();//市列表
    private List<AreaEntity> mAreaList = new ArrayList<>();//区列表
    private List<TownEntity> mTownList = new ArrayList<>();//街道列表
    private List<String> titles;//tab集合
    private ViewPager mVp;
    private TabLayout mTabLayout;
    private ViewPagerAdapter viewPagerAdapter;
    private Province1Adapter province1Adapter;
    /**
     * 选中的区域下标 默认-1
     */
    private int provinceSelected = -1;
    private int citySelected = -1;
    private int areaSelected = -1;
    private int townSelected = -1;
    /**
     * 历史选中的区域下标 默认-1
     */
    private int oldProvinceSelected = -1;
    private int oldCitySelected = -1;
    private int oldAreaSelected = -1;
    private int oldTownSelected = -1;
    private City1Adapter cityAdapter;
    private Area1Adapter areaAdapter;
    private Town1Adapter townAdapter;
    private List<CityEntity> cities;
    private List<AreaEntity> areas;
    private List<TownEntity> towns;
    private RecyclerView provinceRv;
    private RecyclerView cityRv;
    private RecyclerView areaRv;
    private RecyclerView townRv;

    public interface AreaPickerViewCallback {
        void callback(int... value);

        void addressCallBack(String... value);
    }

    public AddressPickerDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    @Override
    @SuppressLint("InflateParams")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_area_pickerview);
        Window window = this.getWindow();
        //位于底部
        assert window != null;
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
        // 设置弹出动画
        window.setWindowAnimations(R.style.PickerAnim);
        titles = new ArrayList<>();
        titles.add("请选择");
        findViewById(R.id.iv_btn).setOnClickListener(view -> dismiss());
        mVp = findViewById(R.id.viewpager);
        mTabLayout = findViewById(R.id.tablayout);

        View provinceView = LayoutInflater.from(mContext)
                .inflate(R.layout.layout_recyclerview, null, false);
        View cityView = LayoutInflater.from(mContext)
                .inflate(R.layout.layout_recyclerview, null, false);
        View areaView = LayoutInflater.from(mContext)
                .inflate(R.layout.layout_recyclerview, null, false);
        View townView = LayoutInflater.from(mContext)
                .inflate(R.layout.layout_recyclerview, null, false);

        provinceRv = provinceView.findViewById(R.id.recyclerview);
        cityRv = cityView.findViewById(R.id.recyclerview);
        areaRv = areaView.findViewById(R.id.recyclerview);
        townRv = townView.findViewById(R.id.recyclerview);

        //view 集合
        List<View> views = new ArrayList<>();
        views.add(provinceView);
        views.add(cityView);
        views.add(areaView);
        views.add(townView);

        // 配置adapter
        viewPagerAdapter = new ViewPagerAdapter(views, titles);
        mVp.setAdapter(viewPagerAdapter);
        mTabLayout.setupWithViewPager(mVp);

        province1Adapter = new Province1Adapter(R.layout.item_address, mProvinceList);
        LinearLayoutManager provinceManager = new LinearLayoutManager(mContext);
        provinceRv.setLayoutManager(provinceManager);
        provinceRv.setAdapter(province1Adapter);

        cityAdapter = new City1Adapter(R.layout.item_address, mCityList);
        LinearLayoutManager cityListManager = new LinearLayoutManager(mContext);
        cityRv.setLayoutManager(cityListManager);
        cityRv.setAdapter(cityAdapter);

        areaAdapter = new Area1Adapter(R.layout.item_address, mAreaList);
        LinearLayoutManager areaListManager = new LinearLayoutManager(mContext);
        areaRv.setLayoutManager(areaListManager);
        areaRv.setAdapter(areaAdapter);

        townAdapter = new Town1Adapter(R.layout.item_address, mTownList);
        LinearLayoutManager townListManager = new LinearLayoutManager(mContext);
        townRv.setLayoutManager(townListManager);
        townRv.setAdapter(townAdapter);
        setProvinceListener();
        setCityListener();
        setAreaListener();
        setTownListener();
        addViewPagerListener();
    }

    private void addViewPagerListener() {
        mVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case 0:
                        provinceRv.scrollToPosition(oldProvinceSelected == -1 ? 0 : oldProvinceSelected);
                        break;
                    case 1:
                        cityRv.scrollToPosition(oldCitySelected == -1 ? 0 : oldCitySelected);
                        break;
                    case 2:
                        areaRv.scrollToPosition(oldAreaSelected == -1 ? 0 : oldAreaSelected);
                        break;
                    case 3:
                        townRv.scrollToPosition(oldTownSelected == -1 ? 0 : oldTownSelected);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }


    private void setProvinceListener() {
        province1Adapter.setOnItemClickListener((adapter, view, position) -> {
            mProvinceList.get(position).setStatus(true);
            titles.set(0, mProvinceList.get(position).getName());
            provinceSelected = position;
            if (oldProvinceSelected == -1) {
                titles.add(1, "请选择");
                mCityList.clear();
                mCityList = getCityList(mProvinceList.get(position).getCode());
            }
            if (oldProvinceSelected != -1 && oldProvinceSelected != provinceSelected) {
                switch (titles.size()) {
                    case 1:
                        titles.add("请选择");
                        break;
                    case 2:
                        titles.set(1, "请选择");
                        break;
                    case 3:
                        titles.set(1, "请选择");
                        titles.remove(2);
                        break;
                    case 4:
                        titles.set(1, "请选择");
                        titles.remove(3);
                        titles.remove(2);
                        break;
                }
                mCityList.clear();
                mAreaList.clear();
                mTownList.clear();
                mCityList = getCityList(mProvinceList.get(position).getCode());
                oldTownSelected = -1;
                oldAreaSelected = -1;
                oldCitySelected = -1;
                mProvinceList.get(oldProvinceSelected).setStatus(false);
            }
            oldProvinceSelected = provinceSelected;
            province1Adapter.notifyDataSetChanged();
            cityAdapter.notifyDataSetChanged();
            areaAdapter.notifyDataSetChanged();
            townAdapter.notifyDataSetChanged();
            mTabLayout.setupWithViewPager(mVp);
            viewPagerAdapter.notifyDataSetChanged();
            Objects.requireNonNull(mTabLayout.getTabAt(1)).select();
        });
    }

    private void setCityListener() {
        cityAdapter.setOnItemClickListener((adapter, view, position) -> {
            mCityList.get(position).setStatus(true);
            citySelected = position;
            titles.set(1, mCityList.get(position).getName());
            if (oldCitySelected == -1) {
                titles.add(2, "请选择");
                mAreaList.clear();
                mAreaList = getAreaList(mCityList.get(position).getCode());
            }
            if (oldCitySelected != -1 && oldCitySelected != citySelected) {
                switch (titles.size()) {
                    case 2:
                        titles.add("请选择");
                        break;
                    case 3:
                        titles.set(2, "请选择");
                        break;
                    case 4:
                        titles.set(2, "请选择");
                        titles.remove(3);
                        break;
                }
                mAreaList.clear();
                mTownList.clear();
                mAreaList = getAreaList(mCityList.get(position).getCode());
                oldTownSelected = -1;
                oldAreaSelected = -1;
                mCityList.get(oldCitySelected).setStatus(false);
            }
            oldCitySelected = citySelected;
            cityAdapter.notifyDataSetChanged();
            areaAdapter.notifyDataSetChanged();
            townAdapter.notifyDataSetChanged();
            mTabLayout.setupWithViewPager(mVp);
            viewPagerAdapter.notifyDataSetChanged();
            Objects.requireNonNull(mTabLayout.getTabAt(2)).select();
        });

    }

    private void setAreaListener() {
        areaAdapter.setOnItemClickListener((adapter, view, position) -> {
            mAreaList.get(position).setStatus(true);
            areaSelected = position;
            titles.set(2, mAreaList.get(position).getName());
            if (oldAreaSelected == -1) {
                titles.add(3, "请选择");
                mTownList.clear();
                mTownList = getTownList(mAreaList.get(position).getCode());
            }
            if (oldAreaSelected != -1 && oldAreaSelected != areaSelected) {
                switch (titles.size()) {
                    case 3:
                        titles.add("请选择");
                        break;
                    case 4:
                        titles.set(3, "请选择");
                        break;
                }
                mTownList.clear();
                mTownList = getTownList(mAreaList.get(position).getCode());
                oldTownSelected = -1;
                mAreaList.get(oldAreaSelected).setStatus(false);
            }
            oldAreaSelected = areaSelected;
            areaAdapter.notifyDataSetChanged();
            townAdapter.notifyDataSetChanged();
            mTabLayout.setupWithViewPager(mVp);
            viewPagerAdapter.notifyDataSetChanged();
            Objects.requireNonNull(mTabLayout.getTabAt(3)).select();
        });
    }

    private void setTownListener() {
        townAdapter.setOnItemClickListener((adapter, view, position) -> {
            mTownList.get(position).setStatus(true);
            townSelected = position;
            titles.set(3, mTownList.get(position).getName());
            if (oldTownSelected != -1 && oldTownSelected != townSelected) {
                mTownList.get(oldTownSelected).setStatus(false);
            }
            townAdapter.notifyDataSetChanged();
            mTabLayout.setupWithViewPager(mVp);
            viewPagerAdapter.notifyDataSetChanged();
            oldTownSelected = townSelected;
            areaPickerViewCallback.callback(provinceSelected, citySelected, areaSelected, townSelected);
            areaPickerViewCallback.addressCallBack(mProvinceList.get(provinceSelected).getName(), mCityList.get(citySelected).getName(),
                    mAreaList.get(areaSelected).getName(), mTownList.get(townSelected).getName());
            dismiss();
        });
    }

    public void initData(List<ProvinceEntity> provinces, List<CityEntity> cities, List<AreaEntity> areas, List<TownEntity> towns) {
        this.mProvinceList = provinces;
        this.cities = cities;
        this.areas = areas;
        this.towns = towns;
    }


    public void setAreaPickerViewCallback(AreaPickerViewCallback areaPickerViewCallback) {
        this.areaPickerViewCallback = areaPickerViewCallback;
    }

    private List<CityEntity> getCityList(String code) {
        for (CityEntity cityEntity : cities) {
            cityEntity.setStatus(false);
            if (TextUtils.equals(cityEntity.getProvinceCode(), code))
                mCityList.add(cityEntity);
        }
        return mCityList;
    }

    private List<AreaEntity> getAreaList(String code) {
        for (AreaEntity areaEntity : areas) {
            areaEntity.setStatus(false);
            if (TextUtils.equals(areaEntity.getCityCode(), code))
                mAreaList.add(areaEntity);
        }
        return mAreaList;
    }

    private List<TownEntity> getTownList(String code) {
        for (TownEntity townEntity : towns) {
            townEntity.setStatus(false);
            if (TextUtils.equals(townEntity.getAreaCode(), code))
                mTownList.add(townEntity);
        }
        return mTownList;
    }

}
