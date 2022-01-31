package com.techinterview;

import com.techinterview.ipvalidator.GivenIpValidator;

import java.net.UnknownHostException;

public class App {

    public static void main(String[] args) throws UnknownHostException {
        String ipAddress = "10.40.15.255";
        String cidrRange = "10.40.15.255/0";

        boolean givenIpValid = GivenIpValidator.validateIpAddress(ipAddress, cidrRange);
        System.out.println(givenIpValid);
    }
}
