package store.backend;

import java.net.InetAddress;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.*;
import java.util.ArrayList;
import java.util.Base64.Encoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
// import org.h2.util.json.JSONArray;
import org.json.simple.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import store.backend.springcybersource.*;

@Slf4j
@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*")
public class PaymentsController {

  @Autowired
  private PaymentRepository paymentRepository;

  private static boolean DEBUG = true;

  @Value("${cybersource.apihost}")
  private String apiHost;

  @Value("${cybersource.merchantkeyid}")
  private String merchantKeyId;

  @Value("${cybersource.merchantsecretkey}")
  private String merchantsecretKey;

  @Value("${cybersource.merchantid}")
  private String merchantId;

  private CyberSourceAPI api = new CyberSourceAPI();

  private static Map<String, String> months = new HashMap<>();

  static {
    months.put("January", "01");
    months.put("February", "02");
    months.put("March", "03");
    months.put("April", "04");
    months.put("May", "05");
    months.put("June", "06");
    months.put("July", "07");
    months.put("August", "08");
    months.put("September", "09");
    months.put("October", "10");
    months.put("November", "11");
    months.put("December", "12");
  }

  @Getter
  @Setter
  class Message {

    private String msg;

    public Message(String m) {
      msg = m;
    }
  }

  class ErrorMessages {

    private ArrayList<Message> messages = new ArrayList<>();

    public void add(String m) {
      messages.add(new Message(m));
    }

    public ArrayList<Message> getMessages() {
      return messages;
    }

    public void print() {
      for (Message m : messages) {
        System.out.println(m);
      }
    }
  }

  @GetMapping(path = "/allPayments")
  public @ResponseBody Iterable<PaymentsCommand> getAction() {
    return paymentRepository.findAll();
  }

  /*
   * private String firstname ; private String lastname ; private String address;
   * private String city; private String state; private String zip; private String
   * phonenumber; private String cardnumber; private String expmonth; private
   * String expyear; private String cvv; private String email; private String
   * notes;
   *
   * private String orderNumber; private String transactionAmount; private String
   * transactionCurrency; private String authId; private String authStatus;
   * private String captureId; private String captureStatus;
   */

  @PostMapping(path = "/process")
  public @ResponseBody JSONObject postAction(
    @RequestParam String firstname,
    @RequestParam String lastname,
    @RequestParam String address,
    @RequestParam String city,
    @RequestParam String state,
    @RequestParam String zip,
    @RequestParam String phonenumber,
    @RequestParam String cardnumber,
    @RequestParam String expmonth,
    @RequestParam String expyear,
    @RequestParam String cvv,
    @RequestParam String email,
    @RequestParam String notes,
    @RequestParam String orderNumber,
    @RequestParam String transactionAmount,
    @RequestParam String transactionCurrency
  ) {
    JSONObject returnObject = new JSONObject();
    JSONArray errArray = new JSONArray();

    CyberSourceAPI.setHost(apiHost);
    CyberSourceAPI.setKey(merchantKeyId);
    CyberSourceAPI.setSecret(merchantsecretKey);
    CyberSourceAPI.setMerchant(merchantId);

    CyberSourceAPI.debugConfig();

    PaymentsCommand command = new PaymentsCommand(
      firstname,
      lastname,
      address,
      city,
      state,
      zip,
      phonenumber,
      cardnumber,
      expmonth,
      expyear,
      cvv,
      email,
      notes
    );
    log.info("Command: " + command);
    ErrorMessages msgs = new ErrorMessages();

    boolean hasErrors = false;

    if (command.getFirstname().equals("")) {
      hasErrors = true;
      errArray.add("First Name is required");
    }
    if (command.getLastname().equals("")) {
      hasErrors = true;
      errArray.add("Last Name is required");
    }
    if (command.getAddress().equals("")) {
      hasErrors = true;
      errArray.add("Address is required");
    }
    if (command.getCity().equals("")) {
      hasErrors = true;
      errArray.add("City is required");
    }
    if (command.getState().equals("")) {
      hasErrors = true;
      errArray.add("State is required");
    }
    if (command.getZip().equals("")) {
      hasErrors = true;
      errArray.add("Zip Code is required");
    }
    if (command.getPhonenumber().equals("")) {
      hasErrors = true;
      msgs.add("Phone Number is required");
    }
    if (command.getCreditCard().equals("")) {
      hasErrors = true;
      errArray.add("Card Number is required");
    }
    if (command.getExpMonth().equals("")) {
      hasErrors = true;
      errArray.add("Card Exp Month is required");
    }
    if (command.getExpYear().equals("")) {
      hasErrors = true;
      errArray.add("Card Exp Year is required");
    }
    if (command.getCVV().equals("")) {
      hasErrors = true;
      errArray.add("Card CVV is required");
    }
    if (command.getEmail().equals("")) {
      hasErrors = true;
      errArray.add("Email is required");
    }

    if (!command.getZip().matches("\\d{5}")) {
      hasErrors = true;
      errArray.add("Invalid Zip Code: " + command.getZip());
    }
    if (!command.getPhonenumber().matches("[(]\\d{3}[)]\\d{3}-\\d{4}")) {
      hasErrors = true;
      errArray.add("Invalid Phone Number: " + command.getPhonenumber());
    }
    if (!command.getCVV().matches("\\d{3}")) {
      hasErrors = true;
      errArray.add("Invalid CVV: " + command.getCVV());
    }
    if (
      !command
        .getCreditCard()
        .matches("((?:(?:\\d{4}[-]){3}\\d{4}|\\d{16}))(?![\\d])")
    ) {
      hasErrors = true;
      errArray.add("Invalid Card Number: " + command.getCreditCard());
    }
    // if (!command.getExpYear().matches("\\d{2}")) {
    // hasErrors = true;
    // errArray.add("Invalid Card Expiration Year: " + command.getExpYear());
    // }

    if (hasErrors) {
      msgs.print();
      System.out.println("MSGS: " + msgs);
      // model.addAttribute("messages", msgs.getMessages());
      returnObject.put("hasErrors", errArray.toString());
      return returnObject;
    }

    int min = 1239871;
    int max = 9999999;
    int random_int = (int) Math.floor(Math.random() * (max - min + 1) + min);
    String order_num = String.valueOf(random_int);
    AuthRequest auth = new AuthRequest();
    auth.reference = order_num;
    auth.billToFirstName = command.getFirstname();
    auth.billToLastName = command.getLastname();
    auth.billToAddress = command.getAddress();
    auth.billToCity = command.getCity();
    auth.billToState = command.getState();
    auth.billToZipCode = command.getZip();
    auth.billToPhone = command.getPhonenumber();
    auth.billToEmail = command.getEmail();
    auth.transactionAmount = transactionAmount;
    auth.transactionCurrency = "USD";
    auth.cardNumnber = command.getCreditCard();
    auth.cardExpMonth = months.get(command.getExpMonth());
    auth.cardExpYear = command.getExpYear();
    auth.cardCVV = command.getCVV();
    auth.cardType = CyberSourceAPI.getCardType(auth.cardNumnber);
    System.out.println("Auth card type" + auth.cardType);

    if (auth.cardType.equals("ERROR")) {
      System.out.println("Unsupported Credit Card Type.");
      // model.addAttribute("message", "Unsupported Credit Card Type.");
      returnObject.put("ERROR", "Unsupported Credit Card Type");
      return returnObject;
    }
    boolean authValid = true;
    AuthResponse authResponse = new AuthResponse();
    System.out.println("\n\nAuth Request: " + auth.toJson());
    authResponse = api.authorize(auth);
    System.out.println("\n\nAuth Response: " + authResponse.toJson());
    if (!authResponse.status.equals("AUTHORIZED")) {
      authValid = false;
      System.out.println(authResponse.message);
      // model.addAttribute("message", authResponse.message);
      returnObject.put("UNAUTHORIZED", "Credit Card Type");
      return returnObject;
    }

    boolean captureValid = true;
    CaptureRequest capture = new CaptureRequest();
    CaptureResponse captureResponse = new CaptureResponse();
    if (authValid) {
      capture.reference = order_num;
      capture.paymentId = authResponse.id;
      capture.transactionAmount = transactionAmount;
      capture.transactionCurrency = "USD";
      System.out.println("\n\nCapture Request: " + capture.toJson());
      captureResponse = api.capture(capture);
      System.out.println("\n\nCapture Response: " + captureResponse.toJson());
      if (!captureResponse.status.equals("PENDING")) {
        captureValid = false;
        System.out.println(captureResponse.message);
        // model.addAttribute("message", captureResponse.message);
        errArray.add("PENDING Credit Card Type");
      }
    }

    /* Render View */
    if (authValid && captureValid) {
      command.setOrderNumber(order_num);
      command.setTransactionAmount(transactionAmount);
      command.setTransactionCurrency("USD");
      command.setAuthId(authResponse.id);
      command.setAuthStatus(authResponse.status);
      command.setCaptureId(captureResponse.id);
      command.setCaptureStatus(captureResponse.status);

      paymentRepository.save(command);
      returnObject.put("Status", "201");
      returnObject.put("Message", "Payment Process Successful");
      returnObject.put("Payment ID", authResponse.id);
      // model.addAttribute("message", "Thank You for Your Payment! Your Order
      // Numberis: " + order_num);
    }

    return returnObject;
  }
}
    @RequestParam String lastname,
    @RequestParam String address,
    @RequestParam String city,
    @RequestParam String state,
    @RequestParam String zip,
    @RequestParam String phonenumber,
    @RequestParam String cardnumber,
    @RequestParam String expmonth,
    @RequestParam String expyear,
    @RequestParam String cvv,
    @RequestParam String email,
    @RequestParam String notes,
    @RequestParam String orderNumber,
    @RequestParam String transactionAmount,
    @RequestParam String transactionCurrency
  ) {
    JSONObject returnObject = new JSONObject();
    JSONArray errArray = new JSONArray();

    CyberSourceAPI.setHost(apiHost);
    CyberSourceAPI.setKey(merchantKeyId);
    CyberSourceAPI.setSecret(merchantsecretKey);
    CyberSourceAPI.setMerchant(merchantId);

    CyberSourceAPI.debugConfig();

    PaymentsCommand command = new PaymentsCommand(
      firstname,
      lastname,
      address,
      city,
      state,
      zip,
      phonenumber,
      cardnumber,
      expmonth,
      expyear,
      cvv,
      email,
      notes
    );
    log.info("Command: " + command);
    ErrorMessages msgs = new ErrorMessages();

    boolean hasErrors = false;

    if (command.getFirstname().equals("")) {
      hasErrors = true;
      errArray.add("First Name is required");
    }
    if (command.getLastname().equals("")) {
      hasErrors = true;
      errArray.add("Last Name is required");
    }
    if (command.getAddress().equals("")) {
      hasErrors = true;
      errArray.add("Address is required");
    }
    if (command.getCity().equals("")) {
      hasErrors = true;
      errArray.add("City is required");
    }
    if (command.getState().equals("")) {
      hasErrors = true;
      errArray.add("State is required");
    }
    if (command.getZip().equals("")) {
      hasErrors = true;
      errArray.add("Zip Code is required");
    }
    if (command.getPhonenumber().equals("")) {
      hasErrors = true;
      msgs.add("Phone Number is required");
    }
    if (command.getCreditCard().equals("")) {
      hasErrors = true;
      errArray.add("Card Number is required");
    }
    if (command.getExpMonth().equals("")) {
      hasErrors = true;
      errArray.add("Card Exp Month is required");
    }
    if (command.getExpYear().equals("")) {
      hasErrors = true;
      errArray.add("Card Exp Year is required");
    }
    if (command.getCVV().equals("")) {
      hasErrors = true;
      errArray.add("Card CVV is required");
    }
    if (command.getEmail().equals("")) {
      hasErrors = true;
      errArray.add("Email is required");
    }

    if (!command.getZip().matches("\\d{5}")) {
      hasErrors = true;
      errArray.add("Invalid Zip Code: " + command.getZip());
    }
    if (!command.getPhonenumber().matches("[(]\\d{3}[)]\\d{3}-\\d{4}")) {
      hasErrors = true;
      errArray.add("Invalid Phone Number: " + command.getPhonenumber());
    }
    if (!command.getCVV().matches("\\d{3}")) {
      hasErrors = true;
      errArray.add("Invalid CVV: " + command.getCVV());
    }
    if (
      !command
        .getCreditCard()
        .matches("((?:(?:\\d{4}[-]){3}\\d{4}|\\d{16}))(?![\\d])")
    ) {
      hasErrors = true;
      errArray.add("Invalid Card Number: " + command.getCreditCard());
    }
    // if (!command.getExpYear().matches("\\d{2}")) {
    // hasErrors = true;
    // errArray.add("Invalid Card Expiration Year: " + command.getExpYear());
    // }

    if (hasErrors) {
      msgs.print();
      System.out.println("MSGS: " + msgs);
      // model.addAttribute("messages", msgs.getMessages());
      returnObject.put("hasErrors", errArray.toString());
      return returnObject;
    }

    int min = 1239871;
    int max = 9999999;
    int random_int = (int) Math.floor(Math.random() * (max - min + 1) + min);
    String order_num = String.valueOf(random_int);
    AuthRequest auth = new AuthRequest();
    auth.reference = order_num;
    auth.billToFirstName = command.getFirstname();
    auth.billToLastName = command.getLastname();
    auth.billToAddress = command.getAddress();
    auth.billToCity = command.getCity();
    auth.billToState = command.getState();
    auth.billToZipCode = command.getZip();
    auth.billToPhone = command.getPhonenumber();
    auth.billToEmail = command.getEmail();
    auth.transactionAmount = transactionAmount;
    auth.transactionCurrency = "USD";
    auth.cardNumnber = command.getCreditCard();
    auth.cardExpMonth = months.get(command.getExpMonth());
    auth.cardExpYear = command.getExpYear();
    auth.cardCVV = command.getCVV();
    auth.cardType = CyberSourceAPI.getCardType(auth.cardNumnber);
    System.out.println("Auth card type" + auth.cardType);

    if (auth.cardType.equals("ERROR")) {
      System.out.println("Unsupported Credit Card Type.");
      // model.addAttribute("message", "Unsupported Credit Card Type.");
      returnObject.put("ERROR", "Unsupported Credit Card Type");
      return returnObject;
    }
    boolean authValid = true;
    AuthResponse authResponse = new AuthResponse();
    System.out.println("\n\nAuth Request: " + auth.toJson());
    authResponse = api.authorize(auth);
    System.out.println("\n\nAuth Response: " + authResponse.toJson());
    if (!authResponse.status.equals("AUTHORIZED")) {
      authValid = false;
      System.out.println(authResponse.message);
      // model.addAttribute("message", authResponse.message);
      returnObject.put("UNAUTHORIZED", "Credit Card Type");
      return returnObject;
    }

    boolean captureValid = true;
    CaptureRequest capture = new CaptureRequest();
    CaptureResponse captureResponse = new CaptureResponse();
    if (authValid) {
      capture.reference = order_num;
      capture.paymentId = authResponse.id;
      capture.transactionAmount = transactionAmount;
      capture.transactionCurrency = "USD";
      System.out.println("\n\nCapture Request: " + capture.toJson());
      captureResponse = api.capture(capture);
      System.out.println("\n\nCapture Response: " + captureResponse.toJson());
      if (!captureResponse.status.equals("PENDING")) {
        captureValid = false;
        System.out.println(captureResponse.message);
        // model.addAttribute("message", captureResponse.message);
        errArray.add("PENDING Credit Card Type");
      }
    }

    /* Render View */
    if (authValid && captureValid) {
      command.setOrderNumber(order_num);
      command.setTransactionAmount(transactionAmount);
      command.setTransactionCurrency("USD");
      command.setAuthId(authResponse.id);
      command.setAuthStatus(authResponse.status);
      command.setCaptureId(captureResponse.id);
      command.setCaptureStatus(captureResponse.status);

      paymentRepository.save(command);
      returnObject.put("Status", "201");
      returnObject.put("Message", "Payment Process Successful");
      returnObject.put("Payment ID", authResponse.id);
      // model.addAttribute("message", "Thank You for Your Payment! Your Order
      // Numberis: " + order_num);
    }

    return returnObject;
  }
}
