package com.cognizant.Backend.controller;

import com.cognizant.Backend.exception.InvalidAddressException;
import com.cognizant.Backend.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @PostMapping("/location")
    public ResponseEntity<?> transformAddressInCoordinates(@RequestBody String address) {
        try {
            double[] coordinates = addressService.getCoordinates(address);
            return new ResponseEntity<>(coordinates, HttpStatus.OK);
        }
        catch (InvalidAddressException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
