package com.ikutarian.mmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.ikutarian.mmall.common.ResponseCode;
import com.ikutarian.mmall.common.ServerResponse;
import com.ikutarian.mmall.dao.ShippingMapper;
import com.ikutarian.mmall.form.ShippingForm;
import com.ikutarian.mmall.model.Shipping;
import com.ikutarian.mmall.service.ShippingService;
import com.ikutarian.mmall.util.SessionUtils;
import com.ikutarian.mmall.vo.ShippingVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Service("shippingServiceImpl")
public class ShippingServiceImpl implements ShippingService {

    @Autowired
    private ShippingMapper shippingMapper;

    @Override
    public ServerResponse add(HttpSession session, ShippingForm shippingForm) {
        Integer userId = SessionUtils.getUserId(session);
        if (userId == null) {
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN);
        }

        Shipping newShipping = new Shipping();
        BeanUtils.copyProperties(shippingForm, newShipping);
        newShipping.setUserId(userId);
        int insertCount = shippingMapper.insertSelective(newShipping);
        if (insertCount == 1) {
            Map<String, Integer> result = Maps.newHashMap();
            result.put("shippingId", newShipping.getId());
            return ServerResponse.createBySuccessMsgData("新增收货地址成功", result);
        } else {
            return ServerResponse.createByError(ResponseCode.ADD_SHIPPING_FAIL);
        }
    }

    @Override
    public ServerResponse del(HttpSession session, Integer shippingId) {
        Integer userId = SessionUtils.getUserId(session);
        if (userId == null) {
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN);
        }

        int deleteCount = shippingMapper.deleteByShippingIdAndUserId(shippingId, userId);  // 为了防止横向越权，不能调用shippingMapper.deleteByPrimaryKey(shippingId)
        if (deleteCount == 1) {
            return ServerResponse.createBySuccessMsg("删除地址成功");
        } else {
            return ServerResponse.createByError(ResponseCode.DEL_SHIPPING_FAIL);
        }
    }

    @Override
    public ServerResponse update(HttpSession session, ShippingForm shippingForm) {
        Integer userId = SessionUtils.getUserId(session);
        if (userId == null) {
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN);
        }

        Shipping shipping = new Shipping();
        BeanUtils.copyProperties(shippingForm, shipping);
        shipping.setUserId(userId);
        int updateCount = shippingMapper.updateByShippingIdAndUserId(shipping);  // 防止横向越权
        if (updateCount == 1) {
            return ServerResponse.createBySuccessMsg("更新收货地址成功");
        } else {
            return ServerResponse.createByError(ResponseCode.UPDATE_SHIPPING_FAIL);
        }
    }

    @Override
    public ServerResponse getById(HttpSession session, Integer shippingId) {
        Integer userId = SessionUtils.getUserId(session);
        if (userId == null) {
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN);
        }

        Shipping shipping = shippingMapper.getByShippingIdAndUserId(shippingId, userId);  // 防止横向越权
        if (shipping == null) {
            return ServerResponse.createByError(ResponseCode.SHIPPING_NOT_EXISTS);
        } else {
            ShippingVo vo = new ShippingVo();
            BeanUtils.copyProperties(shipping, vo);
            return ServerResponse.createBySuccessData(vo);
        }
    }

    @Override
    public ServerResponse list(HttpSession session, int pageNum, int pageSize) {
        Integer userId = SessionUtils.getUserId(session);
        if (userId == null) {
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN);
        }

        PageHelper.startPage(pageNum, pageSize);
        List<Shipping> shippingList = shippingMapper.getByUserId(userId);
        PageInfo<Shipping> pageInfo = new PageInfo<>(shippingList);
        return ServerResponse.createBySuccessData(pageInfo);
    }
}
