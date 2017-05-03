package com.xigeng.weblaserproject.service;

import com.xigeng.weblaserproject.model.Asset;

import java.util.List;

/**
 * Created by Administrator on 2016/11/9.
 */
public interface IAssetService {

    List<Asset> selectAllAssetList();
}
