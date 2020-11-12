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

/*  @GetMapping("/{phoneNo}")
    public ResponseEntity<?> getAddressByPhoneNumber(@PathVariable String phoneNo){
        Address address = addressService.findAddressByPhoneNumber(phoneNo);
        return new ResponseEntity<Address>(address, HttpStatus.OK);
    }
    @GetMapping("/{identifier}")
    public ResponseEntity<?> getAddressByIdentifier(@PathVariable String identifier){
        Address address = addressService.findAddressByIdentifier(identifier);
        return new ResponseEntity<Address>(address, HttpStatus.OK);
    }

    @GetMapping("/{schoolName}")
    public ResponseEntity<?> getAddressBySchoolName(@PathVariable String schoolName){
        Address address = addressService.findAddressBySchool(schoolName);
        return new ResponseEntity<Address>(address, HttpStatus.OK);
    }

   */
}
