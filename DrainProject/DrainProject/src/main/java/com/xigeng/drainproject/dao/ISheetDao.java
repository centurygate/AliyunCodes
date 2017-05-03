package com.xigeng.drainproject.dao;

import com.xigeng.drainproject.model.SheetItem;

import java.util.List;

/**
 * Created by Administrator on 2016/11/7.
 */
public interface ISheetDao {

    int insert(SheetItem record);

    SheetItem selectByPrimaryKey(Integer id);

    List<SheetItem> selectAllItemList();

    void updateItem(SheetItem item);

    void dropItem(String sheetname);
}
