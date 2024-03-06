package org.flavorforge.flavorforge.ai.client.gpt;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "gpt", url = "${gpt.url}")
public interface GptClient {

    @PostMapping(path = "/completions")
    GptResponse generateText(@RequestBody GptRequest request,
                             @RequestHeader("Authorization") String key);
}
