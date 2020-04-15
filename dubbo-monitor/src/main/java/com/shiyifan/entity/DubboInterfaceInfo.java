package com.shiyifan.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (DubboInterfaceInfo)实体类
 * @since 2020-03-27 10:27:04
 */
public class DubboInterfaceInfo implements Serializable {
    private static final long serialVersionUID = -13053744063341612L;
    
    private Integer id;
    
    private String host;
    
    private String ip;
    
    private Integer port;
    
    private String interfacename;
    
    private String methodname;
    
    private String protocol;
    
    private Date createtime;
    
    private Date endtime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getInterfacename() {
        return interfacename;
    }

    public void setInterfacename(String interfacename) {
        this.interfacename = interfacename;
    }

    public String getMethodname() {
        return methodname;
    }

    public void setMethodname(String methodname) {
        this.methodname = methodname;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    @Override
    public String toString() {
        return "DubboInterfaceInfo{" +
                "id=" + id +
                ", host='" + host + '\'' +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                ", interfacename='" + interfacename + '\'' +
                ", methodname='" + methodname + '\'' +
                ", protocol='" + protocol + '\'' +
                ", createtime=" + createtime +
                ", endtime=" + endtime +
                '}';
    }
}