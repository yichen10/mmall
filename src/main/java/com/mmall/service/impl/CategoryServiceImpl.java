package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
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
import sun.awt.geom.AreaOp;

import java.util.List;
import java.util.Set;

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

    /**
     *
     * @param categoryId
     * @return
     */
    public ServerResponse<List<Integer>> getDeepCategoryById(Integer categoryId) {
        if (categoryId == null) {
            return ServerResponse.createByErrorMessage("参数错误");
        }
        Set<Category> categorySet = Sets.newHashSet();
        findChildCategory(categorySet,categoryId);

        List<Integer> categoryIdList = Lists.newArrayList();
        if (categoryId != null) {
            for (Category category : categorySet) {
                categoryIdList.add(category.getId());
            }
        }
        return ServerResponse.createBySuccess(categoryIdList);
    }

    private Set<Category> findChildCategory(Set<Category> categorySet ,Integer categoryId) {
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if (category != null) {
            categorySet.add(category);
        }
        List<Category> categoryList = categoryMapper.getChildrenCategoryById(categoryId);
        for (Category item: categoryList) {
            findChildCategory(categorySet, item.getId());
        }
        return categorySet;
    }


    public ServerResponse<String> updateCategoryName(String categoryName, Integer categoryId){
        if (StringUtils.isBlank(categoryName) || categoryId == null) {
            return ServerResponse.createByErrorMessage("参数错误");
        }
        Category category = new Category();
        category.setId(categoryId);
        category.setName(categoryName);
        category.setStatus(true);
        int resultCount = categoryMapper.updateByPrimaryKeySelective(category);
        if (resultCount > 0) {
            return ServerResponse.createBySuccessMessage("修改分类成功");
        }
        return ServerResponse.createByErrorMessage("修改分类失败");
    }




}
