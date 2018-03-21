package com.mmall.service.impl;

import com.mmall.common.ServerResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.pojo.Category;
import com.mmall.service.ICategoryService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wang on 2018/3/21.
 */
@Service("iCategoryService")
public class CategoryServiceImpl  implements ICategoryService{

    public static Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Autowired
    private CategoryMapper categoryMapper;

    public ServerResponse<List<Category>> getChildrenCategoryById(Integer categoryId) {
        if (categoryId == null) {
            return ServerResponse.createByErrorMessage("参数错误");
        }
        List<Category> categoryList = categoryMapper.getChildrenCategoryById(categoryId);
        if (CollectionUtils.isEmpty(categoryList)) {
            logger.info("未找到分类");
        }
        return ServerResponse.createBySuccess(categoryList);
    }

    public ServerResponse<String> addCategory(String categoryName, Integer parentId){
        if (StringUtils.isBlank(categoryName) || parentId == null) {
            return ServerResponse.createByErrorMessage("参数错误");
        }
        Category category = new Category();
        category.setParentId(parentId);
        category.setName(categoryName);
        category.setStatus(true);
        int resultCount = categoryMapper.insert(category);
        if (resultCount > 0) {
            return ServerResponse.createBySuccessMessage("新增分类成功");
        }
        return ServerResponse.createByErrorMessage("新增分类失败");
    }



}
