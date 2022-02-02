package com.techinterview;

import com.techinterview.ipvalidator.GivenIpValidator;

import java.net.UnknownHostException;

public class App {

    public static void main(String[] args) throws UnknownHostException {
        String ipAddress = "10.40.255.255";
        String cidrRange = "10.40.255.5/20";

        boolean givenIpValid = GivenIpValidator.validateIpAddress(ipAddress, cidrRange);
        System.out.println("Is IP Address in a CIDR Range: " +givenIpValid);
    }
}
