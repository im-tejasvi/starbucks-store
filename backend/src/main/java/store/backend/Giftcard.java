package store.backend;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // This tells Hibernate to make a table out of this class
public class Giftcard {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  private String cardNumber;
  private String cardCode;
  private double balance;
  private boolean activated;

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getCardNumber() {
    return this.cardNumber;
  }

  public void setCardNumber(String cardNumber) {
    this.cardNumber = cardNumber;
  }

  public String getCardCode() {
    return this.cardCode;
  }

  public void setCardCode(String cardCode) {
    this.cardCode = cardCode;
  }

  public double getBalance() {
    return this.balance;
  }

  public void setBalance(double balance) {
    this.balance = balance;
  }

  public boolean isActivated() {
    return this.activated;
  }

  public boolean getActivated() {
    return this.activated;
  }

  public void setActivated(boolean activated) {
    this.activated = activated;
  }
}
