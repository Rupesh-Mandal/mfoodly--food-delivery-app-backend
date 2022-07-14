package com.soft_kali.mfoodly.utils;

import org.springframework.data.domain.Sort;

public class AppConstants {
    public static final int PAGE_NUMBBER=0;
    public static final int PAGE_SIZE=10;

    public static final Long ROLE_ADMIN= Long.valueOf(1);
    public static final Long ROLE_SUB_ADMIN= Long.valueOf(2);
    public static final Long ROLE_OUTLET= Long.valueOf(3);
    public static final Long ROLE_SUB_OUTLET= Long.valueOf(4);
    public static final Long ROLE_PICKUP_BOY= Long.valueOf(5);
    public static final Long ROLE_CUSTOMER= Long.valueOf(6);

    public static final Sort.Direction sort=Sort.Direction.ASC;

    public static final String IMAGE_EXTENTION="jpg";

    public static final String SORT_BY_FOR_POST = "outletName";
    public static final String SORT_BY_FOR_PRODUCT = "productId";
    public static final String SORT_BY_FOR_ADDRESS_BOOK = "addressBookId";
    public static final String SORT_BY_FOR_PRODUCT_ORDER = "productOrderId";


    public static final String _outlet_banner = "_outlet_banner";
    public static final String _outlet_logo = "_outlet_logo";
    public static final String _product_image = "_product_image";
}
