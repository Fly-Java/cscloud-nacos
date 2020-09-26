package com.zzp.common.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class BusOrder implements Serializable {
    private Long id;

    private BigDecimal orderMoney;

    private String orderSn;

    private String receiverAddress;

    private String receiverName;

    private String receiverPhone;

    private Integer payStatus;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderTime;

}