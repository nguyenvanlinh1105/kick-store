package com.kick_api.service;

import com.kick_api.entity.Address;
import java.util.List;

public interface AddressService {
    
    List<Address> getUserAddresses(Long userId);
    
    Address createAddress(Long userId, Address address);
    
    Address updateAddress(Long userId, Long addressId, Address addressDetails);
    
    void deleteAddress(Long userId, Long addressId);
    
    void setDefaultAddress(Long userId, Long addressId);
}
