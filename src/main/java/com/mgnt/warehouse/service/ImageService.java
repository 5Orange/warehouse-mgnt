package com.mgnt.warehouse.service;

import com.cloudinary.Cloudinary;
import com.mgnt.warehouse.modal.exception.InvalidRequestException;
import com.mgnt.warehouse.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ImageService {
    @Value("${cloudfoundry.cloud-name}")
    private String host;

    @Value("${cloudfoundry.key}")
    private String key;

    @Value("${cloudfoundry.secret}")
    private String secret;

    private final ProductRepository productRepository;

    private Cloudinary cloudinary;

    private static final List<String> VALID_FILE_TYPE = List.of("image/jpeg", "image/png", "image/gif", "image/bmp", "image/tiff", "image/svg+xml", "image/webp");
    @PostConstruct
    void init() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", host);
        config.put("api_key", key);
        config.put("api_secret", secret);
        cloudinary = new Cloudinary(config);
    }

    @SneakyThrows
    public String uploadFile(MultipartFile file, String id) {

        if (file.isEmpty()) {
            return null;
        }
        if (!VALID_FILE_TYPE.contains(file.getContentType())) {
            throw new BadRequestException("Invalid file type");
        }
        Map<String, ?> params1 = Map.of(
            "use_filename", true,
            "unique_filename", true,
            "overwrite", true,
            "public_id", id
        );
        var response = cloudinary.uploader().upload(file.getBytes(), params1);
        return (String) response.get("url");
    }

    public void updateProductImage(String productId, MultipartFile file) {
        if (productId == null) {
            throw new InvalidRequestException("Product id is null");
        }
        var product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            throw new InvalidRequestException("Product not found");
        }
        product.setImageUrl(uploadFile(file, product.getProductCode()));
        productRepository.save(product);
    }
}
