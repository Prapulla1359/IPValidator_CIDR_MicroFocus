package com.techinterview;

import com.techinterview.ipvalidator.GivenIpValidator;

import java.net.UnknownHostException;

public class App {

    /**
     * Main method that calls validateIpAddress to verify if the given IP address is in CIDR range.
     *
     * @param args The command line arguments.
     * @throws UnknownHostException if IP address of a host could not be determined
     */
    public static void main(String[] args) throws UnknownHostException {
        String ipAddress = "10.40.255.255";
        String cidrRange = "10.40.255.255/30";

        boolean givenIpValid = GivenIpValidator.validateIpAddress(ipAddress, cidrRange);
        System.out.println("Is IP Address in a CIDR Range: " +givenIpValid);
    }
}
