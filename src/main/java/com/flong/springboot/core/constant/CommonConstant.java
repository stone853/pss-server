package com.flong.springboot.core.constant;

/**
 * @Author jinshi
 * @Date 2020/11/25 17:29
 * @Version 1.0
 */
public class CommonConstant {
    public static final long EXPIRE_TIME = 24 * 60 * 60 * 1000;

    public static final String CONTRACT_SALE_PROCESS_TYPE  = "xsht";

    public static final String CONTRACT_PUR_PROCESS_TYPE  = "cght";

    public static final String ORDER_PUR_TYPE  = "cgdd";

    public static final String ORDER_OUT_TYPE  = "ckd";

    //销售合同提交状态
    public static final String CONTRACT_SALE_SUB_STATUS  = "2";

    //采购合同提交状态
    public static final String CONTRACT_PUR_SUB_STATUS  = "2";

    //合同提交状态
    public static final String ORDER_SUB_STATUS  = "2";

    //发货提交状态
    public static final String ORDER_SEND_STATUS  = "1";
}
