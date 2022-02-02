package com.techinterview.ipvalidator.inputvalidation;

import org.apache.commons.net.util.SubnetUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InputValidation {

    /**
     * Function to validate IP Address.
     *
     * @param ipAddress String representation of IP address that needs to be validated.
     *                  Function must verify that IP address definition itself is valid.
     * @throws IllegalArgumentException if ipAddress is invalid.
     * @return true if provided IP address is valid.
     */

    public boolean isValidIpAddress(final String ipAddress) throws IllegalArgumentException
    {
        try {
            InetAddress.getByName(ipAddress);
            return true;
        } catch (UnknownHostException e) {
            throw new IllegalArgumentException("IP Address is invalid");
        }
    }

    /**
     * Function to validate IP Address.
     *
     * @param cidrRange String representation of IP address that needs to be validated.
     *                  Function must verify that IP address definition itself is valid.
     * @throws IllegalArgumentException if ipAddress is invalid.
     * @return true if provided IP address is valid.
     */

    public boolean isValidCIDR(final String cidrRange) throws IllegalArgumentException
    {
        try {
            SubnetUtils subnetUtils = new SubnetUtils(cidrRange);
            return true;
        }
        catch (Exception e){
            throw new IllegalArgumentException("CIDR Range is invalid");
        }
    }
}
