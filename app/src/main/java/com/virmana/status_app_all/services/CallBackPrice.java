package com.virmana.status_app_all.services;

import java.util.List;

public interface CallBackPrice {
    void onNotLogin();
    void onPrice(List<Billing> listBilling);
}