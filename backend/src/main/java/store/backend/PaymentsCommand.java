package store.backend;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@RequiredArgsConstructor
@Table(name = "Payments")
class PaymentsCommand {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private transient String action;
  private String firstname;
  private String lastname;
  private String address;
  private String city;
  private String state;
  private String zip;
  private String phonenumber;
  private String cardnumber;
  private String expmonth;
  private String expyear;
  private String cvv;
  private String email;
  private String notes;

  private String orderNumber;
  private String transactionAmount;
  private String transactionCurrency;
  private String authId;
  private String authStatus;
  private String captureId;
  private String captureStatus;

  public String getAction() {
    return action;
  }

  // public void setAction(String action) {
  // this.action = action;
  // }

  public String getFirstname() {
    return firstname;
  }

  // public void setFirstname(String firstname) {
  // this.firstname = firstname;
  // }

  public String getLastname() {
    return lastname;
  }

  // public void setLastname(String lastname) {
  // this.lastname = lastname;
  // }

  public String getCreditCard() {
    return this.cardnumber;
  }

  // public void setCreditCard(String cardnumb){
  // this.cardnumber = cardnumb;
  // }

  public String getAddress() {
    return this.address;
  }

  // public void setAddress(String addr){
  // this.address = addr;
  // }

  public String getCity() {
    return this.city;
  }

  // public void setCity(String city){
  // this.city = city;
  // }

  public String getState() {
    return this.state;
  }

  // public void setState(String state){
  // this.state = state;
  // }

  public String getZip() {
    return this.zip;
  }

  // public void setZip(String zip){
  // this.zip = zip;
  // }

  public String getPhone() {
    return this.phonenumber;
  }

  // public void setPhone(String phone){
  // this.phone = phone;
  // }

  public String getExpMonth() {
    return this.expmonth;
  }

  // public void setExpMonth(String expMonth){
  // this.expmonth = expMonth;
  // }

  public String getExpYear() {
    return this.expyear;
  }

  // public void setExpYear(String expyr){
  // this.expyear = expyr;
  // }

  public String getCVV() {
    return this.cvv;
  }

  // public void setCVV(String cvv){
  // this.cvv = cvv;
  // }

  public String getEmail() {
    return this.email;
  }

  // public void setEmail(String mail){
  // this.email = mail;
  // }

  public String getNotes() {
    return this.notes;
  }

  public PaymentsCommand(
    String firstname2,
    String lastname2,
    String address2,
    String city2,
    String state2,
    String zip2,
    String phonenumber2,
    String cardnumber2,
    String expmonth2,
    String expyear2,
    String cvv2,
    String email2,
    String notes2
  ) {
    this.firstname = firstname2;
    this.lastname = lastname2;
    this.address = address2;
    this.city = city2;
    this.state = state2;
    this.zip = zip2;
    this.phonenumber = phonenumber2;
    this.cardnumber = cardnumber2;
    this.expmonth = expmonth2;
    this.expyear = expyear2;
    this.cvv = cvv2;
    this.email = email2;
    this.notes = notes2;
  }
  // public void setNotes(String note){
  // this.notes = note;
  // }

}
  public String getCVV() {
    return this.cvv;
  }

  // public void setCVV(String cvv){
  // this.cvv = cvv;
  // }

  public String getEmail() {
    return this.email;
  }

  // public void setEmail(String mail){
  // this.email = mail;
  // }

  public String getNotes() {
    return this.notes;
  }

  public PaymentsCommand(
    String firstname2,
    String lastname2,
    String address2,
    String city2,
    String state2,
    String zip2,
    String phonenumber2,
    String cardnumber2,
    String expmonth2,
    String expyear2,
    String cvv2,
    String email2,
    String notes2
  ) {
    this.firstname = firstname2;
    this.lastname = lastname2;
    this.address = address2;
    this.city = city2;
    this.state = state2;
    this.zip = zip2;
    this.phonenumber = phonenumber2;
    this.cardnumber = cardnumber2;
    this.expmonth = expmonth2;
    this.expyear = expyear2;
    this.cvv = cvv2;
    this.email = email2;
    this.notes = notes2;
  }
  // public void setNotes(String note){
  // this.notes = note;
  // }

}
