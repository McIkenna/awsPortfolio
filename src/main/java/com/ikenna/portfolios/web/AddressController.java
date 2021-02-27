package com.ikenna.portfolios.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/address")
public class AddressController {

  /*  @Autowired
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
*/
}
