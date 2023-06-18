package com.easy.query.test.h2.domain;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;

/**
 * create time 2023/6/10 08:30
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("t_all_type")
//@Accessors(chain = true)
public class ALLTYPE {
    @Column(primaryKey = true)
    private String id;
    private BigDecimal numberDecimal;
    private Float numberFloat;
    private Double numberDouble;
    private Short numberShort;
    private Integer numberInteger;
    private Long numberLong;
    private Byte numberByte;
    private LocalDateTime timeLocalDateTime;
    private LocalDate timeLocalDate;
    private LocalTime timeLocalTime;
    private Date onlyDate;
    private java.sql.Date sqlDate;
    private Time onlyTime;
    private Boolean enable;
    //    private Blob jBlob;
//    private Clob jClob;
    private String value;
    private UUID uid;
    private float numberFloatBasic;
    private double numberDoubleBasic;
    private short numberShortBasic;
    private int numberIntegerBasic;
    private long numberLongBasic;
    private byte numberByteBasic;
    private boolean enableBasic;
}
