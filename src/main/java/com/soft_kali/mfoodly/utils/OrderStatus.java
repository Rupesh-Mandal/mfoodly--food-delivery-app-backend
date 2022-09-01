package com.soft_kali.mfoodly.utils;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class OrderStatus {

    int STATUS_PENDING_TO_ACCEPT=1;
    int STATUS_WATING_TO_ORDER_READY=2;
    int STATUS_WATING_ACCEPT_BY_PICKUPBOY=3;
    int STATUS_ORDER_STARTED_TO_DELIVERED=4;
    int STATUS_ORDER_SUCCESSFULLY_DELIVERED=5;
    int STATUS_ORDER_CANCELLED_BY_SELLER=6;
    int STATUS_ORDER_CANCELLED_BY_USER=7;
    int STATUS_ORDER_FAILD_TO_DELIVERED=8;


    String STATUS_PENDING_TO_ACCEPT_NAME="Pending to Accept";
    String STATUS_WATING_TO_ORDER_READY_NAME="Wating to make your Order Ready";
    String STATUS_WATING_ACCEPT_BY_PICKUPBOY_NAME="Wating to start Delivered";
    String STATUS_ORDER_STARTED_TO_DELIVERED_NAME="Started to Delivered";
    String STATUS_ORDER_SUCCESSFULLY_DELIVERED_NAME="Successfully Delivered";
    String STATUS_ORDER_CANCELLED_BY_SELLER_NAME="Cancelled by Seller";
    String STATUS_ORDER_CANCELLED_BY_USER_NAME="Cancelled by You";
    String STATUS_ORDER_FAILD_TO_DELIVERED_NAME="Faild to Delivered";



    String STATUS_PENDING_TO_ACCEPT_COLORS="#50DBB4";
    String STATUS_WATING_TO_ORDER_READY_COLORS="#35BDD0";
    String STATUS_WATING_ACCEPT_BY_PICKUPBOY_COLORS="#00D84A";
    String STATUS_ORDER_STARTED_TO_DELIVERED_COLORS="#DDD101";
    String STATUS_ORDER_SUCCESSFULLY_DELIVERED_COLORS="#6EC72D";
    String STATUS_ORDER_CANCELLED_BY_SELLER_COLORS="#B4161B";
    String STATUS_ORDER_CANCELLED_BY_USER_COLORS="#FF6263";
    String STATUS_ORDER_FAILD_TO_DELIVERED_COLORS="#E03B8B";



}
