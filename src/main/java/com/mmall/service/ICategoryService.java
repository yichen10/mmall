package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Category;

import java.util.List;

/**
 * Created by wang on 2018/3/21.
 */
public interface ICategoryService {

    ServerResponse<List<Category>> getChildrenCategoryById(Integer categoryId);

    ServerResponse<String> addCategory(String categoryName, Integer parentId);

    ServerResponse<List<Integer>> getDeepCategoryById(Integer categoryId);

    ServerResponse<String> updateCategoryName(String categoryName, Integer categoryId);
}
