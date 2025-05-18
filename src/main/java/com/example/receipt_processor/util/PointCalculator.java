package com.example.receipt_processor.util;

import com.example.receipt_processor.model.Item;
import com.example.receipt_processor.model.Receipt;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class PointCalculator {

    private PointCalculator() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static Map<String, Object> calculatePointsWithBreakdown(Receipt receipt) {
        int totalPoints = 0;
        List<String> breakdown = new ArrayList<>();

        int retailerPoints = countAlphanumerics(receipt.getRetailer());
        totalPoints += retailerPoints;
        breakdown.add(retailerPoints + " points - retailer name has " + retailerPoints + " alphanumeric characters");

        double total = parseDouble(receipt.getTotal());
        if (total % 1 == 0) {
            totalPoints += 50;
            breakdown.add("50 points - total is a round dollar amount with no cents");
        }

        if (total * 100 % 25 == 0) {
            totalPoints += 25;
            breakdown.add("25 points - total is a multiple of 0.25");
        }

        int itemPairs = (receipt.getItems().size() / 2) * 5;
        totalPoints += itemPairs;
        breakdown.add(itemPairs + " points - " + receipt.getItems().size() + " items ("
                + (receipt.getItems().size() / 2) + " pairs @ 5 points each)");

        for (Item item : receipt.getItems()) {
            String desc = item.getShortDescription().trim();
            if (desc.length() % 3 == 0) {
                double itemPrice = parseDouble(item.getPrice());
                double rawPointsCalc = itemPrice * 0.2;
                int itemPoints = (int) Math.ceil(rawPointsCalc);
                totalPoints += itemPoints;

                BigDecimal priceBD = BigDecimal.valueOf(itemPrice).setScale(2, RoundingMode.HALF_UP);
                BigDecimal calcBD = BigDecimal.valueOf(rawPointsCalc).setScale(2, RoundingMode.HALF_UP);

                breakdown.add(
                        itemPoints + " points - " + desc + " is " + desc.length() + " characters (a multiple of 3)");
                breakdown.add("                item price of " + priceBD.toPlainString() + " * 0.2 = "
                        + calcBD.toPlainString() + ", rounded up is " + itemPoints + " points");
            }
        }

        LocalDate date = LocalDate.parse(receipt.getPurchaseDate());
        if (date.getDayOfMonth() % 2 != 0) {
            totalPoints += 6;
            breakdown.add("6 points - purchase day is odd");
        }

        LocalTime time = LocalTime.parse(receipt.getPurchaseTime());
        if (time.isAfter(LocalTime.of(14, 0)) && time.isBefore(LocalTime.of(16, 0))) {
            totalPoints += 10;
            breakdown.add("10 points - time of purchase is between 2:00pm and 4:00pm");
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("totalPoints", totalPoints);
        result.put("breakdown", breakdown);
        return result;
    }

    private static int countAlphanumerics(String s) {
        int count = 0;
        for (char c : s.toCharArray()) {
            if (Character.isLetterOrDigit(c))
                count++;
        }
        return count;
    }

    private static double parseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            return 0.0;
        }
    }
}
