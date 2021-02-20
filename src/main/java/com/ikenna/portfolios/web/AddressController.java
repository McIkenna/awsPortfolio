package com.ikenna.portfolios.web;

import com.ikenna.portfolios.infos.Address;
import com.ikenna.portfolios.services.AddressService;
import com.ikenna.portfolios.services.MapErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    @Autowired
    AddressService addressService;

    @Autowired
    MapErrorService mapErrorService;

    @PostMapping("")
    public ResponseEntity<?> createNewAddress(@Valid @RequestBody Address address, BindingResult result){
        ResponseEntity<?> errorMap = mapErrorService.MapErrorService(result);
        if(errorMap != null) return errorMap;
        Address address1 = addressService.saveOrUpdateAddress(address);
        return new ResponseEntity<Address>(address, HttpStatus.CREATED);
    }

}
