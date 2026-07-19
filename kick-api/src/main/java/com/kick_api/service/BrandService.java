package com.kick_api.service;

import com.kick_api.entity.Brand;
import java.util.List;

public interface BrandService {
    
    List<Brand> getActiveBrands();
    
    Brand createBrand(Brand brand);
    
    Brand updateBrand(Long id, Brand brandDetails);
}
