package com.cognizant.Backend.service;

import com.cognizant.Backend.util.GeoCoder;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    public double[] getCoordinates(String address) {
        return GeoCoder.getCoordinates(address);
    }
}