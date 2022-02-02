package com.techinterview.ipvalidator.service;

import java.util.Arrays;

public final class Utility {

    public static final String RANGE_DELIMITER = "/";

    public static final String IP_DELIMITER = "\\.";

    /**
     * Method to convert CIDR IP to binary format.
     *
     * Splitting based on "/" to get CIDR range
     * Spliiting based on "." to get each element of CIDR IP.
     * Iterate over each element of CIDR IP and convert that to binary using String formatter to set to %8s.
     * %8s sets to space by default so replacing space with "0" thus sets 8 number of bits for each element framing octet.
     *
     * @param cidrRange Classless Inter-Domain Routing (CIDR) definition that defines range of allowed IP addresses.
     * @return binaryIP of type StringBuffer
     */
    public static final StringBuffer convertCidrToBinary(final String cidrRange){
        StringBuffer binaryIP = new StringBuffer();
        String[] cidr = cidrRange.split(RANGE_DELIMITER);
        String[] cidrIP = cidr[0].split(IP_DELIMITER);

        Arrays.stream(cidrIP).forEach(
                address -> {
                    int add = Integer.parseInt(address);
                    String binary = String.format("%8s", Integer.toBinaryString(add)).
                            replaceAll(" ", "0");
                    binaryIP.append(binary);
                }
        );
        return binaryIP;
    }

    /**
     * Method to find CIDR range.
     * Splitting based on "/" and evaluating range.
     *
     * Formula: range = address length(32 bits) - range provided in CIDR.
     * For ex: CIDR range = a.b.c.d/n, range = 32 - n
     *
     * @param cidrRange
     * @return range of type Integer
     */
    public static final Integer getCidrRangeInteger(final String cidrRange){
        String[] cidr = cidrRange.split(RANGE_DELIMITER);
        Integer range = 32 - Integer.parseInt(cidr[1]);
        return range;
    }
}
