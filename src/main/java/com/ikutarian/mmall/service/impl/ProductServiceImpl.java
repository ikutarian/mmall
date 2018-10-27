package com.ikutarian.mmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.ikutarian.mmall.common.Config;
import com.ikutarian.mmall.common.Const;
import com.ikutarian.mmall.common.ResponseCode;
import com.ikutarian.mmall.common.ServerResponse;
import com.ikutarian.mmall.dao.CategoryMapper;
import com.ikutarian.mmall.dao.ProductMapper;
import com.ikutarian.mmall.form.ProductForm;
import com.ikutarian.mmall.model.Category;
import com.ikutarian.mmall.model.Product;
import com.ikutarian.mmall.service.CategoryService;
import com.ikutarian.mmall.service.ProductService;
import com.ikutarian.mmall.vo.ProductDetailVo;
import com.ikutarian.mmall.vo.ProductListVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("productService")
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryService categoryService;

    public ServerResponse saveOrUpdateProduct(ProductForm productForm) {
        // 对前端来说：保存和更新是两个操作
        // 但是对于后台来说，可以看作是同一个操作，只需要判断一下即可
        if (productForm == null) {
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT);
        }

        Product product = new Product();
        BeanUtils.copyProperties(productForm, product);
        if (StringUtils.isNotBlank(product.getSubImages())) {
            String[] subImageArray = product.getSubImages().split(",");
            if (subImageArray.length > 0) {
                product.setMainImage(subImageArray[0]);
            }
        }

        if (productForm.getId() != null) {
            // 说明是更新
            int updateCount = productMapper.updateByPrimaryKeySelective(product);
            if (updateCount == 1) {
                return ServerResponse.createBySuccessMsg("更新商品成功");
            } else {
                return ServerResponse.createByError(ResponseCode.UPDATE_PRODUCT_FAILURE);
            }
        } else {
            // 说明是插入
            int insertCount = productMapper.insertSelective(product);
            if (insertCount == 1) {
                return ServerResponse.createBySuccessMsg("新增商品成功");
            } else {
                return ServerResponse.createByError(ResponseCode.INSERT_PRODUCT_FAILURE);
            }
        }
    }

    @Override
    public ServerResponse setSaleStatus(Integer productId, Integer status) {
        if (productId == null || status == null) {
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT);
        }

        Product product = new Product();
        product.setId(productId);
        product.setStatus(status);
        int updateCount = productMapper.updateByPrimaryKeySelective(product);
        if (updateCount == 1) {
            return ServerResponse.createBySuccessMsg("修改产品销售状态成功");
        } else {
            return ServerResponse.createByError(ResponseCode.UPDATE_PRODUCT_STATUS_FAILURE);
        }
    }

    @Override
    public ServerResponse manageDetail(Integer productId) {
        if (productId == null) {
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT);
        }

        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null) {
            return ServerResponse.createByError(ResponseCode.PRODUCT_NOT_EXISTS);
        } else {
            return ServerResponse.createBySuccessData(assembleProductDetailVo(product));
        }
    }

    private ProductDetailVo assembleProductDetailVo(Product product) {
        ProductDetailVo vo = new ProductDetailVo();
        BeanUtils.copyProperties(product, vo);

        vo.setImageHost(Config.getImageServerHost());

        Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());
        if (category == null) {
            vo.setParentCategoryId(Integer.valueOf(Const.Category.ROOT_CATEGORY_ID));  // 默认根节点
        } else {
            vo.setParentCategoryId(category.getParentId());
        }

        return vo;
    }

    @Override
    public ServerResponse list(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        List<Product> productList = productMapper.list();
        List<ProductListVo> voList = Lists.newArrayList();
        for (Product product : productList) {
            ProductListVo vo = assembleProductListVo(product);
            voList.add(vo);
        }

        PageInfo<ProductListVo> pageInfo = new PageInfo<>(voList);
        return ServerResponse.createBySuccessData(pageInfo);
    }

    @Override
    public ServerResponse search(String productName, Integer productId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        List<Product> productList = productMapper.getProductListByIdOrName(productName, productId);
        List<ProductListVo> voList = Lists.newArrayList();
        for (Product product : productList) {
            ProductListVo vo = assembleProductListVo(product);
            voList.add(vo);
        }

        PageInfo<ProductListVo> pageInfo = new PageInfo<>(voList);
        return ServerResponse.createBySuccessData(pageInfo);
    }

    private ProductListVo assembleProductListVo(Product product) {
        ProductListVo vo = new ProductListVo();
        BeanUtils.copyProperties(product, vo);
        vo.setImageHost(Config.getImageServerHost());
        return vo;
    }

    public ServerResponse getProductDetail(Integer productId) {
        if (productId == null) {
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT);
        }

        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null) {
            return ServerResponse.createByError(ResponseCode.PRODUCT_NOT_EXISTS);
        } else {
            if (product.getStatus() != Const.ProductStatus.ON_SALE) {
                return ServerResponse.createByError(ResponseCode.PRODUCT_NOT_ON_SALE);
            } else {
                return ServerResponse.createBySuccessData(assembleProductDetailVo(product));
            }
        }
    }

    @Override
    public ServerResponse getProductByKeywordOrCategory(String keyword, Integer categoryId, int pageNum, int pageSize, String orderBy) {
        // TODO 这个方法没有写完
        if (StringUtils.isBlank(keyword) && categoryId == null) {
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT);
        }

        if (categoryId != null) {
            Category category = categoryMapper.selectByPrimaryKey(categoryId);
            if (category == null && StringUtils.isBlank(keyword)) {
                // 没有该分类并且还没有关键字。这时候返回一个空的结果集，不报错
                PageHelper.startPage(pageNum, pageSize);
                List<ProductListVo> emptyVoList = Lists.newArrayList();
                PageInfo<ProductListVo> pageInfo = new PageInfo<>(emptyVoList);
                return ServerResponse.createBySuccessData(pageInfo);
            }

            List<Integer> categoryIdList = (List<Integer>) categoryService.getCategoryAndChildrenById(categoryId).getData();
        }

        // 排序处理
        PageHelper.startPage(pageNum, pageSize);
        if (StringUtils.isNotBlank(orderBy)) {
            if (Const.ProducutListOrderBy.PRICE_ASC_DESC.contains(orderBy)) {
                String[] orderByArray = orderBy.split("_");
                PageHelper.orderBy(orderByArray[0] + " " + orderByArray[1]);
            }
        }

        return null;
    }
}
