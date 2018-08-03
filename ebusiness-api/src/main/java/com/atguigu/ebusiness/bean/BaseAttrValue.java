package com.atguigu.ebusiness.bean;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * @param
 * @return
 */
public class BaseAttrValue implements Serializable {
    @Id
    @Column
    private String id;
    @Column
    private String valueName;
    @Column
    private String attrId;
    @Column
    private String isEnabled;

    @Transient
    private String urlParam;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValueName() {
        return valueName;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
    }

    public String getAttrId() {
        return attrId;
    }

    public void setAttrId(String attrId) {
        this.attrId = attrId;
    }

    public String getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(String isEnabled) {
        this.isEnabled = isEnabled;
    }

    public String getUrlParam() {
        return urlParam;
    }

    public void setUrlParam(String urlParam) {
        this.urlParam = urlParam;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj == null)
            return false;
        if(this.getClass() != obj.getClass())
            return false;
        final BaseAttrValue student = (BaseAttrValue) obj;
        if(this.getValueName() != student.getValueName())
            return false;
        if(this.getAttrId() != student.getAttrId())
            return false;
        if(this.getIsEnabled() != student.getIsEnabled())
            return false;
        if(this.getUrlParam() != student.getUrlParam())
            return false;
        return true;
    }
}
