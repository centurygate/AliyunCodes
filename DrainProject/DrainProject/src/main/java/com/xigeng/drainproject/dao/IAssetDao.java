package com.xigeng.drainproject.dao;

import com.xigeng.drainproject.model.Asset;

import java.util.List;

/**
 * Created by Administrator on 2016/11/9.
 */
public interface IAssetDao {

    List<Asset> selectAllAssetList();

    List<Asset> selectAssetByType(Integer type);
}
