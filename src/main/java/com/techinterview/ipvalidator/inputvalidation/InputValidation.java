package com.techinterview.ipvalidator.inputvalidation;

import org.apache.commons.net.util.SubnetUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InputValidation {

    public boolean isValidIpAddress(final String ipAddress) throws IllegalArgumentException
    {
        try {
            InetAddress.getByName(ipAddress);
            return true;
        } catch (UnknownHostException e) {
            throw new IllegalArgumentException("IP Address is invalid");
        }
    }

    public boolean isValidCIDR(final String cidrRange){
        try {
            SubnetUtils subnetUtils = new SubnetUtils(cidrRange);
            return true;
        }
        catch (Exception e){
            throw new IllegalArgumentException("CIDR Range is invalid");
        }
    }
}
