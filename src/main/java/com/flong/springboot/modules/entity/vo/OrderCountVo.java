package com.flong.springboot.modules.entity.vo;


import com.baomidou.mybatisplus.core.metadata.IPage;

public class OrderCountVo {

        private int orderCount;

        private String statusName;

        private String status;



        public void setOrderCount(int orderCount) {
            this.orderCount = orderCount;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getOrderCount() {
            return orderCount;
        }

        public void setStatusName(String statusName) {
            this.statusName = statusName;
        }

        public String getStatusName() {
            return statusName;
        }
}
