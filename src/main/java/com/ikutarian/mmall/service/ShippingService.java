package com.ikutarian.mmall.service;

import com.ikutarian.mmall.common.ServerResponse;
import com.ikutarian.mmall.form.ShippingForm;

import javax.servlet.http.HttpSession;

public interface ShippingService {

    ServerResponse add(HttpSession session, ShippingForm shippingForm);

    ServerResponse del(HttpSession session, Integer shippingId);

    ServerResponse update(HttpSession session, ShippingForm shippingForm);

    ServerResponse getById(HttpSession session, Integer shippingId);

    ServerResponse list(HttpSession session, int pageNum, int pageSize);
}
