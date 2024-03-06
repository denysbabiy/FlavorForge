package org.flavorforge.flavorforge.ai.client.dalle;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "dalle", url = "${dalle.url}")
public interface DalleClient {

    @PostMapping(path = "/generations")
    DalleImageGenerationResponse generateImage(@RequestBody DalleImageGenerationRequest request,
                                               @RequestHeader("Authorization") String key);
}
