package com.example.rabbit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Host {

    private String serviceName;
    private String address;
    private Integer port;

    @SuppressWarnings("unused")
    private Host() {
    }

    public Host(String serviceName, String address, Integer port) {
        this.serviceName = serviceName;
        this.address = address;
        this.port = port;
    }

    @JsonIgnore
    public int getIpv4() {
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getByName(this.address);
        } catch (final UnknownHostException e) {
            throw new IllegalArgumentException(e);
        }
        return ByteBuffer.wrap(inetAddress.getAddress()).getInt();
    }

    public String getServiceName() {
        return this.serviceName;
    }

    public String getAddress() {
        return this.address;
    }

    public Integer getPort() {
        return this.port;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}