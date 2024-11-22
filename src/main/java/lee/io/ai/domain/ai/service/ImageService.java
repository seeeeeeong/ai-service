package lee.io.ai.domain.ai.service;

import lee.io.ai.domain.ai.dto.CreateImageReqDto;
import lee.io.ai.domain.ai.dto.CreateImageResDto;
import lee.io.ai.domain.ai.dto.GetImageFeaturesReqDto;
import lee.io.ai.domain.ai.dto.GetImageFeaturesResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.model.Media;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final OpenAiChatModel openAiChatModel;
    private final OpenAiImageModel openAiImageModel;

    @Value("${image.features.prompt}")
    private String featuresPrompt;

    @Value("${image.create.prompt}")
    private String createPrompt;

    @Value("${spring.task.execution.pool.core-size}")
    private Integer poolCoreSize;


    public GetImageFeaturesResDto getImageFeatures(GetImageFeaturesReqDto request) {

        String prompt = featuresPrompt;
        UserMessage userMessage = new UserMessage(prompt, Collections.singletonList(new Media(MediaType.IMAGE_JPEG, request.getImageUrl())));

        String features = openAiChatModel.call(userMessage);

        return GetImageFeaturesResDto.of(features);
    }

    public CreateImageResDto createImagesByFeatures(CreateImageReqDto request) {
        ExecutorService executorService = Executors.newFixedThreadPool(poolCoreSize);
        String prompt = createPrompt + request.getFeatures();

        OpenAiImageOptions options = OpenAiImageOptions.builder()
                .withQuality("hd")
                .withN(1)
                .withHeight(1024)
                .withWidth(1024)
                .build();

        List<CompletableFuture<String>> futureImageUrls = new ArrayList<>();

        for (int i=0; i < request.getNumberOfImages(); i++) {
            CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                ImagePrompt imagePrompt = new ImagePrompt(prompt, options);
                return openAiImageModel.call(imagePrompt).getResult().getOutput().getUrl();
            });
            futureImageUrls.add(future);
        }

        List<String> imageUrls = futureImageUrls.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
        executorService.shutdown();

        return CreateImageResDto.of(imageUrls, request.getFeatures());
    }

}

