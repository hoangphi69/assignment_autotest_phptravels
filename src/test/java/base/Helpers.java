package base;

import java.util.Map;

public class Helpers {
  // Giá trị quy đổi tiền tệ
  private static final Map<String, Double> exchangeRates = Map.of(
      "USD", 1.0, // Base currency
      "EUR", 0.9397,
      "GBP", 0.8032,
      "SAR", 3.75);

  // Đổi đơn vị tiền tệ
  public static String convertCurrency(String input, String toCurrency) {
    String[] parts = input.split(" ");
    if (parts.length != 2) {
      throw new IllegalArgumentException("Invalid input format");
    }

    String fromCurrency = parts[0];
    double amount = Double.parseDouble(parts[1]);

    if (!exchangeRates.containsKey(fromCurrency) || !exchangeRates.containsKey(toCurrency)) {
      throw new IllegalArgumentException("Invalid currency code");
    }

    double amountInUSD = amount / exchangeRates.get(fromCurrency);
    double convertedAmount = amountInUSD * exchangeRates.get(toCurrency);

    return String.format("%s %.2f", toCurrency, convertedAmount);
  }
}
