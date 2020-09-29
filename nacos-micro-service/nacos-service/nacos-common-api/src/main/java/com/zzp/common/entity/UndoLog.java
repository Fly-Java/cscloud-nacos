package com.zzp.common.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class UndoLog implements Serializable {
    private Long id;

    private Long branchId;

    private String xid;

    private String context;

    private String rollbackInfo;

    private Integer logStatus;

    private LocalDateTime logCreated;

    private LocalDateTime logModified;

    private String ext;


}