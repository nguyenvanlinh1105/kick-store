package com.kick_api.service.impl;

import com.kick_api.entity.Address;
import com.kick_api.entity.User;
import com.kick_api.constant.ErrorCode;
import com.kick_api.exception.AppException;
import com.kick_api.repository.AddressRepository;
import com.kick_api.repository.UserRepository;
import com.kick_api.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Address> getUserAddresses(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        return addressRepository.findAllByUserIdAndIsDeletedFalse(userId);
    }

    @Override
    @Transactional
    public Address createAddress(Long userId, Address address) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        List<Address> existing = addressRepository.findAllByUserIdAndIsDeletedFalse(userId);
        
        // Nếu là địa chỉ đầu tiên của user, đặt làm mặc định
        if (existing.isEmpty()) {
            address.setDefault(true);
        } else if (address.isDefault()) {
            // Nếu đặt địa chỉ mới làm mặc định, hủy mặc định của các địa chỉ cũ
            for (Address oldAddr : existing) {
                oldAddr.setDefault(false);
            }
            addressRepository.saveAll(existing);
        }

        address.setUser(user);
        address.setDeleted(false);
        return addressRepository.save(address);
    }

    @Override
    @Transactional
    public Address updateAddress(Long userId, Long addressId, Address addressDetails) {
        Address address = addressRepository.findByIdAndUserIdAndIsDeletedFalse(addressId, userId)
                .orElseThrow(() -> new AppException(ErrorCode.ADDRESS_NOT_FOUND));

        if (addressDetails.isDefault() && !address.isDefault()) {
            List<Address> existing = addressRepository.findAllByUserIdAndIsDeletedFalse(userId);
            for (Address oldAddr : existing) {
                oldAddr.setDefault(false);
            }
            addressRepository.saveAll(existing);
            address.setDefault(true);
        } else if (!addressDetails.isDefault()) {
            address.setDefault(false);
        }

        address.setLabel(addressDetails.getLabel());
        address.setReceiverName(addressDetails.getReceiverName());
        address.setPhone(addressDetails.getPhone());
        address.setProvince(addressDetails.getProvince());
        address.setDistrict(addressDetails.getDistrict());
        address.setWard(addressDetails.getWard());
        address.setAddressDetail(addressDetails.getAddressDetail());
        address.setPostalCode(addressDetails.getPostalCode());

        return addressRepository.save(address);
    }

    @Override
    @Transactional
    public void deleteAddress(Long userId, Long addressId) {
        Address address = addressRepository.findByIdAndUserIdAndIsDeletedFalse(addressId, userId)
                .orElseThrow(() -> new AppException(ErrorCode.ADDRESS_NOT_FOUND));

        address.setDeleted(true);
        if (address.isDefault()) {
            address.setDefault(false);
        }
        addressRepository.save(address);
    }

    @Override
    @Transactional
    public void setDefaultAddress(Long userId, Long addressId) {
        Address target = addressRepository.findByIdAndUserIdAndIsDeletedFalse(addressId, userId)
                .orElseThrow(() -> new AppException(ErrorCode.ADDRESS_NOT_FOUND));

        List<Address> existing = addressRepository.findAllByUserIdAndIsDeletedFalse(userId);
        for (Address addr : existing) {
            addr.setDefault(addr.getId().equals(target.getId()));
        }
        addressRepository.saveAll(existing);
    }
}
