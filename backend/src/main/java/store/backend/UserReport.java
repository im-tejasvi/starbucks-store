package store.backend;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // This tells Hibernate to make a table out of this class
public class UserReport {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  private String username;
  private String reportContent;
  private Boolean resolved;
  private String response;

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getReportContent() {
    return this.reportContent;
  }

  public void setReportContent(String reportContent) {
    this.reportContent = reportContent;
  }

  public Boolean isResolved() {
    return this.resolved;
  }

  public Boolean getResolved() {
    return this.resolved;
  }

  public void setResolved(Boolean resolved) {
    this.resolved = resolved;
  }

  public String getResponse() {
    return this.response;
  }

  public void setResponse(String response) {
    this.response = response;
  }
}
