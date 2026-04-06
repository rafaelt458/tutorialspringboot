package com.laboratorio.springboot69.service;

import com.laboratorio.springboot69.model.ImageParam;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.stabilityai.StabilityAiImageModel;
import org.springframework.ai.stabilityai.api.StabilityAiApi;
import org.springframework.ai.stabilityai.api.StabilityAiImageOptions;
import org.springframework.stereotype.Service;

@Service
public class ImageService {
    private ImageModel createImageModel(ImageParam imageParam) {
        String apiKey = System.getenv("STABILITYAI_API_KEY");

        StabilityAiApi stabilityAiApi = new StabilityAiApi(apiKey, imageParam.model());

        StabilityAiImageOptions imageOptions = StabilityAiImageOptions.builder()
                .model(imageParam.model())
                .N(imageParam.n())
                .width(imageParam.width())
                .height(imageParam.height())
                .cfgScale(imageParam.cfgscale())
                .steps(imageParam.steps())
                .stylePreset(imageParam.stylePreset())
                .build();

        return new StabilityAiImageModel(stabilityAiApi, imageOptions);
    }

    public ImageResponse createImage(ImageParam imageParam) {
        ImagePrompt imagePrompt = new ImagePrompt(imageParam.prompt());
        ImageModel imageModel = this.createImageModel(imageParam);

        return imageModel.call(imagePrompt);
    }
}