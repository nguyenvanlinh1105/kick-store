package com.kick_api.service.impl;

import com.kick_api.entity.Brand;
import com.kick_api.constant.BrandStatus;
import com.kick_api.constant.ErrorCode;
import com.kick_api.exception.AppException;
import com.kick_api.repository.BrandRepository;
import com.kick_api.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Brand> getActiveBrands() {
        return brandRepository.findAllByStatus(BrandStatus.ACTIVE);
    }

    @Override
    @Transactional
    public Brand createBrand(Brand brand) {
        if (brand.getStatus() == null) {
            brand.setStatus(BrandStatus.ACTIVE);
        }
        return brandRepository.save(brand);
    }

    @Override
    @Transactional
    public Brand updateBrand(Long id, Brand brandDetails) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_FOUND));

        brand.setName(brandDetails.getName());
        brand.setLogo(brandDetails.getLogo());
        brand.setDescription(brandDetails.getDescription());
        brand.setStatus(brandDetails.getStatus());

        return brandRepository.save(brand);
    }
}
