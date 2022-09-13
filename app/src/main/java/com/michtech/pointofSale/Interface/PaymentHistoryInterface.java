package com.michtech.pointofSale.Interface;

import com.michtech.pointofSale.pojo.PojoPaymentHistory;

import java.util.List;

public interface PaymentHistoryInterface {
    public List<PojoPaymentHistory> setHistoryData();
    public void viewPaymentHistoryData();
}
