package com.tyhgg.asr.system.entity;

import com.tyhgg.core.framework.entity.PageEntity;

public class SqlEntity extends PageEntity{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String sqlContent;

    public String getSqlContent() {
        return sqlContent;
    }

    public void setSqlContent(String sqlContent) {
        this.sqlContent = sqlContent;
    }
    

    
}
