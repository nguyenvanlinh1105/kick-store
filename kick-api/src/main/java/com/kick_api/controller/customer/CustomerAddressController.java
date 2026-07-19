package com.kick_api.controller.customer;

import com.kick_api.dto.ApiResponse;
import com.kick_api.entity.Address;
import com.kick_api.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer/addresses")
@RequiredArgsConstructor
public class CustomerAddressController {

    private final AddressService addressService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Address>>> getAddresses(@RequestParam Long userId) {
        List<Address> addresses = addressService.getUserAddresses(userId);
        return ResponseEntity.ok(ApiResponse.success(addresses));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Address>> createAddress(
            @RequestParam Long userId,
            @Valid @RequestBody Address address) {
        Address created = addressService.createAddress(userId, address);
        return new ResponseEntity<>(ApiResponse.success("Thêm địa chỉ nhận hàng thành công!", created), HttpStatus.CREATED);
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<ApiResponse<Address>> updateAddress(
            @RequestParam Long userId,
            @PathVariable Long addressId,
            @Valid @RequestBody Address addressDetails) {
        Address updated = addressService.updateAddress(userId, addressId, addressDetails);
        return ResponseEntity.ok(ApiResponse.success("Cập nhật địa chỉ thành công!", updated));
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<ApiResponse<Void>> deleteAddress(
            @RequestParam Long userId,
            @PathVariable Long addressId) {
        addressService.deleteAddress(userId, addressId);
        return ResponseEntity.ok(ApiResponse.success("Xóa địa chỉ thành công!", null));
    }

    @PutMapping("/{addressId}/default")
    public ResponseEntity<ApiResponse<Void>> setDefaultAddress(
            @RequestParam Long userId,
            @PathVariable Long addressId) {
        addressService.setDefaultAddress(userId, addressId);
        return ResponseEntity.ok(ApiResponse.success("Đặt địa chỉ mặc định thành công!", null));
    }
}
