package com.xigeng.drainproject.service;

import com.xigeng.drainproject.model.Asset;

import java.util.List;

/**
 * Created by Administrator on 2016/11/9.
 */
public interface IAssetService {

    List<Asset> selectAllAssetList();

    List<Asset> selectAssetByType(Integer type);
}
