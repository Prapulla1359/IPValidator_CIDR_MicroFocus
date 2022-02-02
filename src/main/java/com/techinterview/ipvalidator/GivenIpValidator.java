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

    /**
     * Constant to define CIDR default Range if not provided in CIDR input.
     */
    public static final String DEFAULT_RANGE = "32";

    /**
     * Constant to define the delimiter to split CIDR Range.
     */
    public static final String RANGE_DELIMITER = "/";

    /**
     * Function to implement validation of given IP address if is in the range defined by cidrRange parameter.
     *
     * Splitting the CIDR range based on "/" delimiter and storing it in String array.
     * If the length after split is less than 2, it does not have /n.
     * For example: a.b.c.d/n is the CIDR range, but the input parameter is without /n. In this case it suffixes with default value /32.
     *
     * Invoking the methods isValidIpAddress and isValidCIDR of class InputValidation which returns true if both are valid, otherwise throws IllegalArgumentException.
     *
     * Invoking Utility class convertCidrToBinary method to convert CIDR IP to binary format.
     *
     * Invoking Utility class getCidrRangeInteger method to find CIDR range.
     * Formula: range = address length(32 bits) - range provided in CIDR.
     * For ex: CIDR range = a.b.c.d/n, range = 32 - n
     *
     * Evaluate minimum range by replacing range number of bits with "0" and maximum range by replacing range number of bits with "1"
     * For ex: 10.40.15.2/29
     * binary format of 10.40.15.2 : 0000 0101. 0010 1000. 0000 1111. 0000 0010
     * range = 32-29 =3, minRange = replace right most 3 bits with 0 and maxRange = replace right most 3 bits with 1 .
     *
     * Convert minRange, maxRange and given ipAddress to Long.
     *
     * If ipAddress lies between minRange and maxRange, ipAddress is valid otherwise invalid.
     *
     * @param ipAddress String representation of IP address
     * @param cidrRange Classless Inter-Domain Routing (CIDR) definition that defines range of allowed IP addresses.
     * @return true if provided IP address is covered by the CIDR range; false otherwise.
     * @throws UnknownHostException if IP address of a host could not be determined
     */
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

    /**
     * Converts ipAddress to Long.
     *
     * For Example: 192.168.1.2
     * 192 * (256)^3 + 168 * (256)^2 + 1 * (256)^1 + 2 * (256)^0 =
     * 3221225472 + 11010048 + 256 + 2 = 3232235778
     *
     * @param ipAddress
     * @return resultIP of type long.
     */
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


    /**
     * Function to evaluate range. Passing range For ex: "%3s", as String formatter to replace "range : 3" number of bits.
     * String formatter appends space so replacing it with "0" or "1". Ex: 111
     *
     * Replace binary IP provided start index(32-3 = 29), end index (Binary ip Length : 32) and String "111" to replace
     * Returns binaryIP with min range/max range.
     *
     * @param binaryIP that holds Binary Format of CIDR IP
     * @param range that holds the range = 32 - CIDR range to find thenumber of bits
     * @param minOrMax replaces with 0 for minRange and 1 for maxRange
     * @return binaryRange of type String
     */
    private static String evaluateRange(StringBuffer binaryIP, Integer range, String minOrMax){
        String binaryRange = binaryIP.toString();
        if(range >0) {
            String formatter = String.format("%" + range + "s", minOrMax).replaceAll(" ", minOrMax);
            binaryRange = binaryIP.replace((binaryIP.length() - range), (binaryIP.length()),formatter).toString();
        }
        return binaryRange;
    }
}
