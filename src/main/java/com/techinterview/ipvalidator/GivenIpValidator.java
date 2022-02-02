package com.techinterview.ipvalidator;

import com.techinterview.ipvalidator.inputvalidation.InputValidation;
import com.techinterview.ipvalidator.service.Utility;
import org.apache.commons.net.util.SubnetUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

public class GivenIpValidator {

    /**
     * Implement the function that validates if provided IP address is in the range defined by cidrRange parameter.
     * You can read about CIDR here https://en.wikipedia.org/wiki/Classless_Inter-Domain_Routing
     * Also, you can use this page https://www.ipaddressguide.com/cidr to understand better CIDR concept.
     *
     * Code organization, implementing other functions that are called from validateAddress - all of this up to you.
     * The final code must be compilable, buildable and tested.
     *
     * You are free to use any build framework to build the code (maven, gradle, ant, etc.).
     *
     * You can use any java version for development (8, 9, 10, 11).
     *
     * You can use any libraries to simplify any auxiliary logic (e.g. string parsing).
     * But you cannot use any 3rd party libraries to implement validation itself.
     *
     * Testing framework selection is up to you. Testing the logic from "public static void main" is also OK.
     *
     * If you are familiar with Git, do your work in a branch and create pull request.
     *
     * @param ipAddress String representation of IP address that needs to be validated.
     *                  Function must verify that IP address definition itself is valid.
     *                  If IP address format is invalid, function must throw IllegalArgumentException.
     *                  E.g. 192.168.0.1 is correct definition of IP address.
     *                  256.0.0.1, 192,168.o.1, 192,168.0.1 are examples of invalid IP addresses.
     * @param cidrRange Classless Inter-Domain Routing (CIDR) definition that defines range of allowed IP addresses.
     *                  For example 11.1.0.0/24 CIDR defines IP addresses range from 11.1.0.0 to 11.1.0.255
     *                  Function must verify that cidrRange definition itself is valid.
     *                  If cidrRange format is invalid, function must throw IllegalArgumentException.
     *                  E.g. 192.168.0.0/24, 100.0.0.0/16, 10.10.0.0/32, 10.10.0.1/16 are valid definitions.
     *                  192.168.0.0/35, 300.0.0.0/16, 10.10,0.0/32, 10.10.0.256/16 are invalid definitions
     *                  If slash is omitted in cidrRange definition, you can assume that / 32 is used.
     *                  E.g. cidrRange 10.10.0.1 can be treated as 10.10.0.1/32
     * @throws IllegalArgumentException if either ipAddress or cidrRange definitions is invalid.
     * @return true if provided IP address is covered by the CIDR range; false otherwise.
     */
    public static final String DEFAULT_RANGE = "32";

    public static final String RANGE_DELIMITER = "/";

    public static boolean validateIpAddress(String ipAddress, String cidrRange) throws UnknownHostException
    {
        InputValidation inputValidation = new InputValidation();
        String[] cidr = cidrRange.split(RANGE_DELIMITER);
        String defaultCIDRRange = null;
        if(cidr.length < 2){
            defaultCIDRRange = cidrRange + RANGE_DELIMITER + DEFAULT_RANGE;
        } else  {
            defaultCIDRRange = cidrRange;
        }
        if (inputValidation.isValidIpAddress(ipAddress) &&
                inputValidation.isValidCIDR(defaultCIDRRange)) {
            StringBuffer binaryIP = Utility.convertCidrToBinary(defaultCIDRRange);
            Integer range = Utility.getCidrRangeInteger(defaultCIDRRange);
            String minRange = evaluateRange(binaryIP, range, "0");
            String maxRange = evaluateRange(binaryIP, range, "1");
            Long startIPAddress = Long.parseLong(minRange, 2);
            Long endIPAddress = Long.parseLong(maxRange, 2);
            long inputIPAddress = ipToLongInt(ipAddress);
            return (inputIPAddress >= startIPAddress && inputIPAddress <= endIPAddress);
        }
        return false;
    }


    public static long ipToLongInt (String ipAddress) {
        long resultIP = 0;
        String[] ipAddressArray = ipAddress.split("\\.");
        for (int i = 0; i < ipAddressArray.length; i++) {
            int power = 3 - i;
            int ip = Integer.parseInt(ipAddressArray[i]);
            resultIP += ip * Math.pow(256, power);
        }
        return resultIP;
    }


    private static String evaluateRange(StringBuffer binaryIP, Integer range, String minOrMax){
        String binaryRange = binaryIP.toString();
        if(range >0) {
            String formatter = String.format("%" + range + "s", minOrMax).replaceAll(" ", minOrMax);
            binaryRange = binaryIP.replace((binaryIP.length() - range), (binaryIP.length()),formatter).toString();
        }
        return binaryRange;
    }
}
