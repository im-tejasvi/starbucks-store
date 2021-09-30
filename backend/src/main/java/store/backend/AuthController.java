package store.backend;

import com.fasterxml.jackson.databind.util.JSONPObject;
import java.net.InetAddress;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.*;
import java.util.ArrayList;
import java.util.Base64.Encoder;
import java.util.HashMap;
import java.util.List;
// import javax.json;
import java.util.Map;
import java.util.Optional;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
// import org.h2.util.json.JSONObject;
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
@RequestMapping("/api/Auth")
@CrossOrigin(origins = "*")
public class AuthController {

  @Autowired
  private UserRepository userRepository;

  @Getter
  @Setter
  class Message {

    private String msg;

    public Message(String m) {
      msg = m;
    }
  }

  @GetMapping(path = "/allusers")
  public @ResponseBody Iterable<User> getUsers() {
    // This returns a JSON or XML with the users
    return userRepository.findAll();
  }

  @PostMapping(path = "/login/userName")
  public @ResponseBody JSONObject login(
    @RequestParam String userName,
    @RequestParam String password
  ) {
    Iterable<User> list = userRepository.findAll();

    JSONObject returnObject = new JSONObject();

    for (User u : list) {
      if (u.getName().equals(userName) && u.getPassword().equals(password)) {
        returnObject.put("Status", "200");
        returnObject.put("Message", "Successful Login");
        return returnObject;
      }
    }

    returnObject.put("Status", "400");
    returnObject.put("Message", "Bad Credentials Or User Not Registered");
    return returnObject;
  }

  @PostMapping(path = "/login/email")
  public @ResponseBody JSONObject loginEmail(
    @RequestParam String email,
    @RequestParam String password
  ) {
    Iterable<User> list = userRepository.findAll();

    JSONObject returnObject = new JSONObject();
    // User find = userRepository.findByEmail(email);
    for (User u : list) {
      if (u.getEmail().equals(email) && u.getPassword().equals(password)) {
        returnObject.put("Status", "200");
        returnObject.put("Message", "Successful Login");
        return returnObject;
      }
    }

    returnObject.put("Status", "400");
    returnObject.put("Message", "Bad Credentials Or User Not Registered");
    return returnObject;
  }

  @PostMapping(path = "/newUser")
  public @ResponseBody JSONObject addNewUser(
    @RequestParam String userName,
    @RequestParam String email,
    @RequestParam String password
  ) {
    Iterable<User> list = userRepository.findAll();
    JSONObject returnObject = new JSONObject();

    for (User u : list) {
      if (u.getEmail().equals(email)) {
        returnObject.put("Status", "400");
        returnObject.put(
          "Message",
          "User with the given email already exists in the system, Please Log In"
        );
        return returnObject;
      }
      if (u.getName().equals(userName)) {
        returnObject.put("Status", "400");
        returnObject.put(
          "Message",
          "User with the given Name already exists in the system, Please Log In"
        );
        return returnObject;
      }
    }

    User newUser = new User();
    newUser.setName(userName);
    newUser.setEmail(email);
    newUser.setPassword(password);
    newUser = userRepository.save(newUser);

    returnObject.put("Status", "200");
    returnObject.put("Message", "Registration Successful");
    returnObject.put("UserID", newUser.getId());
    log.info("New User: " + newUser.getId());
    log.info("Json: " + returnObject.toString());

    return returnObject;
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

  @GetMapping
  public String getAction() {
    return "home";
  }
}
        returnObject.put(
          "Message",
          "User with the given Name already exists in the system, Please Log In"
        );
        return returnObject;
      }
    }

    User newUser = new User();
    newUser.setName(userName);
    newUser.setEmail(email);
    newUser.setPassword(password);
    newUser = userRepository.save(newUser);

    returnObject.put("Status", "200");
    returnObject.put("Message", "Registration Successful");
    returnObject.put("UserID", newUser.getId());
    log.info("New User: " + newUser.getId());
    log.info("Json: " + returnObject.toString());

    return returnObject;
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

  @GetMapping
  public String getAction() {
    return "home";
  }
}
