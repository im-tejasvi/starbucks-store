package store.backend;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // This means that this class is a Controller
@RequestMapping(path = "/api") // This means URL's start with /demo (after Application path)
@CrossOrigin(origins = "*")
public class MainController {

  @Autowired // This means to get the bean called userRepository
  // Which is auto-generated by Spring, we will use it to handle the data
  private UserRepository userRepository;

  @Autowired
  private StoreOrderRepository orderRepo;

  @GetMapping(path = "/ping")
  public @ResponseBody String getPing() {
    // This returns a JSON or XML with the users
    return "Hello World";
  }

  @PostMapping(path = "/create/order") // Map ONLY POST Requests
  public @ResponseBody Integer addNewStarbucksOrder(
    @RequestParam String userID,
    @RequestParam String paymentMethod,
    @RequestParam double orderTotal,
    @RequestBody String orderContent
  ) {
    // @ResponseBody means the returned String is the response, not a view name
    // @RequestParam means it is a parameter from the GET or POST request
    //System.out.println(orderContent);
    StoreOrder o = new StoreOrder();
    o.setUserID(userID);
    o.setPaymentMethod(paymentMethod);
    o.setOrderTotal(orderTotal);
    o.setOrderContent(orderContent);
    orderRepo.save(o);
    System.out.println(o.getId());
    System.out.println(o.getOrderTotal());
    System.out.println(o.getPaymentMethod());
    System.out.println(o.getOrderContent());
    return o.getId();
  }

  @GetMapping(path = "/admin/get/orders")
  public @ResponseBody Iterable<StoreOrder> getOrders() {
    // This returns a JSON or XML with the users
    return orderRepo.findAll();
  }

  @GetMapping(path = "/cashier/get/latestOrder")
  public @ResponseBody StoreOrder getOrder() {
    StoreOrder lastOrder = null;

    for (StoreOrder order : orderRepo.findAll()) {
      lastOrder = order;
    }

    return lastOrder;
  }

  @PostMapping(path = "/cashier/completeOrder") // Map ONLY POST Requests
  public @ResponseBody Boolean completeOrder(@RequestParam Integer orderID) {
    try {
      // Add auth check here?
      Iterable<StoreOrder> allOrders = orderRepo.findAll();
      for (StoreOrder order : allOrders) {
        if (order.getId().equals(orderID)) {
          order.setComplete(true);
          orderRepo.save(order);
          break;
        }
      }
      return true;
    } catch (Exception e) {
      System.out.println(e);
      return false;
    }
  }

  @GetMapping(path = "/all")
  public @ResponseBody Iterable<User> getAllUsers() {
    // This returns a JSON or XML with the users
    return userRepository.findAll();
  }

  @Autowired
  private UserReportRepository userReportRepo;

  @GetMapping(path = "/get/userReports/{username}")
  public @ResponseBody Iterable<UserReport> getReports(
    @PathVariable String username
  ) {
    Iterable<UserReport> allReports = userReportRepo.findAll();
    List<UserReport> finalReports = new ArrayList<>();
    for (UserReport report : allReports) {
      if (report.getUsername().equals(username)) {
        finalReports.add(report);
      }
    }
    if (finalReports.size() > 0) {
      return finalReports;
    } else {
      return null;
    }
  }

  @GetMapping(path = "/get/allReports")
  public @ResponseBody Iterable<UserReport> getAllReports() {
    Iterable<UserReport> allReports = userReportRepo.findAll();
    return allReports;
  }

  @PostMapping(path = "/create/userReport") // Map ONLY POST Requests
  public @ResponseBody Boolean createUserReport(
    @RequestParam String username,
    @RequestParam String reportContent
  ) {
    // @ResponseBody means the returned String is the response, not a view name
    // @RequestParam means it is a parameter from the GET or POST request
    //System.out.println(orderContent);
    try {
      // Add auth check here?
      UserReport userReport = new UserReport();
      userReport.setUsername(username);
      userReport.setReportContent(reportContent);
      userReport.setResolved(false);
      userReport.setResponse("");
      userReportRepo.save(userReport);
      return true;
    } catch (Exception e) {
      System.out.println(e);
      return false;
    }
  }

  @PostMapping(path = "/respond/userReport") // Map ONLY POST Requests
  public @ResponseBody Boolean respondToUserReport(
    @RequestParam Integer reportID,
    @RequestParam String replyContent
  ) {
    // @ResponseBody means the returned String is the response, not a view name
    // @RequestParam means it is a parameter from the GET or POST request
    //System.out.println(orderContent);
    try {
      // Add auth check here?
      Iterable<UserReport> allReports = userReportRepo.findAll();
      for (UserReport report : allReports) {
        if (report.getId().equals(reportID)) {
          report.setResponse(replyContent);
          report.setResolved(true);
          userReportRepo.save(report);
          break;
        }
      }
      return true;
    } catch (Exception e) {
      System.out.println(e);
      return false;
    }
  }
}
    try {
      // Add auth check here?
      UserReport userReport = new UserReport();
      userReport.setUsername(username);
      userReport.setReportContent(reportContent);
      userReport.setResolved(false);
      userReport.setResponse("");
      userReportRepo.save(userReport);
      return true;
    } catch (Exception e) {
      System.out.println(e);
      return false;
    }
  }

  @PostMapping(path = "/respond/userReport") // Map ONLY POST Requests
  public @ResponseBody Boolean respondToUserReport(
    @RequestParam Integer reportID,
    @RequestParam String replyContent
  ) {
    // @ResponseBody means the returned String is the response, not a view name
    // @RequestParam means it is a parameter from the GET or POST request
    //System.out.println(orderContent);
    try {
      // Add auth check here?
      Iterable<UserReport> allReports = userReportRepo.findAll();
      for (UserReport report : allReports) {
        if (report.getId().equals(reportID)) {
          report.setResponse(replyContent);
          report.setResolved(true);
          userReportRepo.save(report);
          break;
        }
      }
      return true;
    } catch (Exception e) {
      System.out.println(e);
      return false;
    }
  }
}
