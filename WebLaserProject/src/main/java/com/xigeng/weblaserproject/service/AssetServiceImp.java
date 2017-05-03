package com.xigeng.weblaserproject.service;

import com.xigeng.weblaserproject.dao.IAssetDao;
import com.xigeng.weblaserproject.model.Asset;
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


    public List<Asset> selectAllAssetList(){
        return assetDao.selectAllAssetList();
    }
}
