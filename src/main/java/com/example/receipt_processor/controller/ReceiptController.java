package com.example.receipt_processor.controller;

import com.example.receipt_processor.model.Receipt;
import com.example.receipt_processor.processor.ReceiptProcessor;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/receipts")
public class ReceiptController {

    private final ReceiptProcessor receiptProcessor;

    public ReceiptController(ReceiptProcessor receiptProcessor) {
        this.receiptProcessor = receiptProcessor;
    }

    // POST /receipts/process
    @PostMapping("/process")
    public Map<String, String> processReceipt(@RequestBody Receipt receipt) {
        String id = receiptProcessor.processReceipt(receipt);
        Map<String, String> response = new LinkedHashMap<>();
        response.put("id", id);
        return response;
    }

    // GET /receipts/{id}/points
    @GetMapping("/{id}/points")
    public Map<String, Object> getPoints(@PathVariable String id) {
        Map<String, Object> pointsData = receiptProcessor.getPointsForReceipt(id);
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("points", pointsData.get("totalPoints"));
        response.put("breakdown", pointsData.get("breakdown"));
        return response;


    }
}
