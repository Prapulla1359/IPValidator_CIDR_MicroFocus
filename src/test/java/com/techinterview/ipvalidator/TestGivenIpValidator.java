package com.techinterview.ipvalidator;

import com.techinterview.ipvalidator.inputvalidation.InputValidation;
import com.techinterview.ipvalidator.service.Utility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.UnknownHostException;

@ExtendWith(MockitoExtension.class)
public class TestGivenIpValidator {

    @Mock
    private InputValidation inputValidation;

    @InjectMocks
    private GivenIpValidator givenIpValidator;

    @Test
    public void should_returnTrueForValidAddress() throws UnknownHostException {
        String ipAddress = "10.40.0.0";
        String cidrRange = "10.40.15.5/20";
        boolean result = GivenIpValidator.validateIpAddress(ipAddress, cidrRange);
        Assertions.assertEquals(result, true);
    }

    @Test
    public void should_returnFalseForValidAddress() throws UnknownHostException {
        String ipAddress = "10.39.0.0";
        String cidrRange = "10.40.15.5/20";
        boolean result = GivenIpValidator.validateIpAddress(ipAddress, cidrRange);
        Assertions.assertEquals(result, false);
    }

}
