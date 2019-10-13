package com.tyhgg.asr.system.entity;

public class ServerLogEntity {

    //起始行
    private long fromLineNo;
    //日志文件名
    private String fileName;
    //关键字
    private String searchKey;
   
    public long getFromLineNo() {
        return fromLineNo;
    }
    public void setFromLineNo(long fromLineNo) {
        this.fromLineNo = fromLineNo;
    }
    
    public String getSearchKey() {
        return searchKey;
    }
    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }
    
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    @Override
    public String toString() {
        StringBuffer sb=new StringBuffer();
        sb.append("fromLineNo:").append(fromLineNo)
                .append(" fileName:").append(fileName)
                .append(" searchKey:").append(searchKey);
        return sb.toString();
    }
}
