package store.backend;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // This tells Hibernate to make a table out of this class
public class StoreOrder {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  private String userID;
  private String paymentMethod; // "1" for bank card, "2" for gift card
  private double orderTotal;
  private String orderContent; // in json
  private boolean complete;

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUserID() {
    return this.userID;
  }

  public void setUserID(String userID) {
    this.userID = userID;
  }

  public String getPaymentMethod() {
    return this.paymentMethod;
  }

  public void setPaymentMethod(String paymentMethod) {
    this.paymentMethod = paymentMethod;
  }

  public double getOrderTotal() {
    return this.orderTotal;
  }

  public void setOrderTotal(double orderTotal) {
    this.orderTotal = orderTotal;
  }

  public String getOrderContent() {
    return this.orderContent;
  }

  public void setOrderContent(String orderContent) {
    this.orderContent = orderContent;
  }

  public boolean isComplete() {
    return this.complete;
  }

  public boolean getComplete() {
    return this.complete;
  }

  public void setComplete(boolean complete) {
    this.complete = complete;
  }
}
