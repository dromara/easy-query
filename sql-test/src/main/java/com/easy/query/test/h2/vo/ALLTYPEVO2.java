package com.easy.query.test.h2.vo;

import com.easy.query.core.annotation.EntityProxy;

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
@EntityProxy
public class ALLTYPEVO2 {
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
//        private Blob mlob1;
//    private Clob mlob2;
    private String value;
    private UUID uid;
    private float numberFloatBasic;
    private double numberDoubleBasic;
    private short numberShortBasic;
    private int numberIntegerBasic;
    private long numberLongBasic;
    private byte numberByteBasic;
    private boolean enableBasic;
    private boolean enableBasic1;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getNumberDecimal() {
        return numberDecimal;
    }

    public void setNumberDecimal(BigDecimal numberDecimal) {
        this.numberDecimal = numberDecimal;
    }

    public Float getNumberFloat() {
        return numberFloat;
    }

    public void setNumberFloat(Float numberFloat) {
        this.numberFloat = numberFloat;
    }

    public Double getNumberDouble() {
        return numberDouble;
    }

    public void setNumberDouble(Double numberDouble) {
        this.numberDouble = numberDouble;
    }

    public Short getNumberShort() {
        return numberShort;
    }

    public void setNumberShort(Short numberShort) {
        this.numberShort = numberShort;
    }

    public Integer getNumberInteger() {
        return numberInteger;
    }

    public void setNumberInteger(Integer numberInteger) {
        this.numberInteger = numberInteger;
    }

    public Long getNumberLong() {
        return numberLong;
    }

    public void setNumberLong(Long numberLong) {
        this.numberLong = numberLong;
    }

    public Byte getNumberByte() {
        return numberByte;
    }

    public void setNumberByte(Byte numberByte) {
        this.numberByte = numberByte;
    }

    public LocalDateTime getTimeLocalDateTime() {
        return timeLocalDateTime;
    }

    public void setTimeLocalDateTime(LocalDateTime timeLocalDateTime) {
        this.timeLocalDateTime = timeLocalDateTime;
    }

    public LocalDate getTimeLocalDate() {
        return timeLocalDate;
    }

    public void setTimeLocalDate(LocalDate timeLocalDate) {
        this.timeLocalDate = timeLocalDate;
    }

    public LocalTime getTimeLocalTime() {
        return timeLocalTime;
    }

    public void setTimeLocalTime(LocalTime timeLocalTime) {
        this.timeLocalTime = timeLocalTime;
    }

    public Date getOnlyDate() {
        return onlyDate;
    }

    public void setOnlyDate(Date onlyDate) {
        this.onlyDate = onlyDate;
    }

    public java.sql.Date getSqlDate() {
        return sqlDate;
    }

    public void setSqlDate(java.sql.Date sqlDate) {
        this.sqlDate = sqlDate;
    }

    public Time getOnlyTime() {
        return onlyTime;
    }

    public void setOnlyTime(Time onlyTime) {
        this.onlyTime = onlyTime;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public UUID getUid() {
        return uid;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public float getNumberFloatBasic() {
        return numberFloatBasic;
    }

    public void setNumberFloatBasic(float numberFloatBasic) {
        this.numberFloatBasic = numberFloatBasic;
    }

    public double getNumberDoubleBasic() {
        return numberDoubleBasic;
    }

    public void setNumberDoubleBasic(double numberDoubleBasic) {
        this.numberDoubleBasic = numberDoubleBasic;
    }

    public short getNumberShortBasic() {
        return numberShortBasic;
    }

    public void setNumberShortBasic(short numberShortBasic) {
        this.numberShortBasic = numberShortBasic;
    }

    public int getNumberIntegerBasic() {
        return numberIntegerBasic;
    }

    public void setNumberIntegerBasic(int numberIntegerBasic) {
        this.numberIntegerBasic = numberIntegerBasic;
    }

    public long getNumberLongBasic() {
        return numberLongBasic;
    }

    public void setNumberLongBasic(long numberLongBasic) {
        this.numberLongBasic = numberLongBasic;
    }

    public byte getNumberByteBasic() {
        return numberByteBasic;
    }

    public void setNumberByteBasic(byte numberByteBasic) {
        this.numberByteBasic = numberByteBasic;
    }

    public boolean isEnableBasic() {
        return enableBasic;
    }

    public void setEnableBasic(boolean enableBasic) {
        this.enableBasic = enableBasic;
    }

    public boolean isEnableBasic1() {
        return enableBasic1;
    }

    public void setEnableBasic1(boolean enableBasic1) {
        this.enableBasic1 = enableBasic1;
    }

}
