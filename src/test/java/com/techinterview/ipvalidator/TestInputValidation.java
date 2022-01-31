package com.techinterview.ipvalidator;

import com.techinterview.ipvalidator.inputvalidation.InputValidation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TestInputValidation {

    @InjectMocks
    private InputValidation inputValidation;

    @Test
    public void should_throwExceptionForInValidIPAddress(){
        String ipAddress = "256.0.0.1";
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            inputValidation.isValidIpAddress(ipAddress);
        });
        String expectedMessage = "IP Address is invalid";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void should_NotThrowExceptionForValidIPAddress(){
        String ipAddress = "19.0.0.1";
        boolean result = inputValidation.isValidIpAddress(ipAddress);
        Assertions.assertEquals(result, true);
    }

    @Test
    public void should_throwExceptionForInValidCIDR(){
        String cidrRange = "192.168.0.0/35";
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            inputValidation.isValidCIDR(cidrRange);
        });
        String expectedMessage = "CIDR Range is invalid";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void should_NotThrowExceptionForValidCIDR(){
        String cidr = "192.168.0.0/24";
        boolean result = inputValidation.isValidCIDR(cidr);
        Assertions.assertEquals(result, true);
    }

}
