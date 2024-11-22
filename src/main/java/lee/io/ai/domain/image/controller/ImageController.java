package lee.io.ai.domain.image.controller;

import jakarta.validation.Valid;
import lee.io.ai.domain.image.dto.CreateImageReqDto;
import lee.io.ai.domain.image.dto.CreateImageResDto;
import lee.io.ai.domain.image.dto.GetImageFeaturesReqDto;
import lee.io.ai.domain.image.dto.GetImageFeaturesResDto;
import lee.io.ai.domain.image.service.ImageService;
import lee.io.ai.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/features")
    public ApiResponse<GetImageFeaturesResDto> getImageFeatures(@RequestBody GetImageFeaturesReqDto request) {
        return ApiResponse.success(imageService.getImageFeatures(request));
    }

    @PostMapping
    public ApiResponse<CreateImageResDto> createImagesByFeatures(@RequestBody @Valid CreateImageReqDto request) {
        return ApiResponse.success(imageService.createImagesByFeatures(request));
    }

}