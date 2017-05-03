package com.xigeng.drainproject.service;

import com.xigeng.drainproject.dao.IAssetDao;
import com.xigeng.drainproject.model.Asset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/11/9.
 */
@Service("assetService")
public class AssetServiceImp implements IAssetService {

    @Autowired
    private IAssetDao assetDao;


    public List<Asset> selectAllAssetList(){ return assetDao.selectAllAssetList(); }

    public List<Asset> selectAssetByType(Integer type){ return assetDao.selectAssetByType(type); }

}
