package com.example.receipt_processor.processor;

import com.example.receipt_processor.exception.ReceiptNotFoundException;
import com.example.receipt_processor.model.Receipt;
import com.example.receipt_processor.util.PointCalculator;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class ReceiptProcessor {

    private final Map<String, Receipt> receiptStore = new HashMap<>();

    public String processReceipt(Receipt receipt) {
        String receiptId = UUID.randomUUID().toString();
        receiptStore.put(receiptId, receipt);
        return receiptId;
    }

    public Map<String, Object> getPointsForReceipt(String receiptId) {
        Receipt receipt = receiptStore.get(receiptId);
        if (receipt == null) {
            throw new ReceiptNotFoundException("Receipt not found for ID: " + receiptId);
        }
        return PointCalculator.calculatePointsWithBreakdown(receipt);
    }
}
