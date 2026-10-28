package com.roamio.ai;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/ai")
public class AiController {
    public record ChatRequest(String message){}
    public record ChatResponse(String reply, Map<String,Object> extracted){}

    @PostMapping("/chat")
    public ChatResponse chat(@RequestBody ChatRequest req){
        String m=Optional.ofNullable(req.message()).orElse("");
        String lower=m.toLowerCase();

        String destination = lower.contains("uganda") ? "Uganda" :
                lower.contains("india") ? "India" :
                lower.contains("japan") ? "Japan" :
                lower.contains("italy") ? "Italy" : "Not specified";

        int days=extractNumberBefore(lower,"day").orElse(0);
        int travelers=extractNumberBefore(lower,"people").orElse(
                extractNumberBefore(lower,"person").orElse(0));

        String reply = "Great! I can help turn that into a trip request. " +
                "Tell me your destination, dates/duration, number of travelers, budget, and interests. " +
                "For now I detected: destination=" + destination +
                (days>0 ? ", duration=" + days + " days" : "") + ".";

        Map<String,Object> extracted=new LinkedHashMap<>();
        extracted.put("destination",destination);
        extracted.put("durationDays",days);
        extracted.put("travelers",travelers);
        extracted.put("message",m);
        return new ChatResponse(reply,extracted);
    }

    private Optional<Integer> extractNumberBefore(String text,String word){
        String[] p=text.split("\\s+");
        for(int i=0;i<p.length-1;i++){
            try{
                if(p[i+1].replaceAll("[^a-z]","").equals(word))
                    return Optional.of(Integer.parseInt(p[i].replaceAll("[^0-9]","")));
            }catch(Exception ignored){}
        }
        return Optional.empty();
    }
}
