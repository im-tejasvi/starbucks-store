package store.backend.springcybersource;

public class RefundRequest extends Payload {

  public String reference;
  public String captureId;
  public String transactionAmount;
  public String transactionCurrency;
}
