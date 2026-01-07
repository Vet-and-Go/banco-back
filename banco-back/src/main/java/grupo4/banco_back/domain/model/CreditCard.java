package grupo4.banco_back.domain.model;

import java.util.Date;

public class CreditCard {
  Long id;
  Long cardNumber;
  Date expirationDate;
  int cvv;
  String completeName;

  public CreditCard(long id, Long cardNumber, Date expirationDate, int cvv, String completeName) {
    this.id = id;
    this.cardNumber = cardNumber;
    this.expirationDate = expirationDate;
    this.cvv = cvv;
    this.completeName = completeName;
  }

  public CreditCard() {
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Long getCardNumber() {
    return cardNumber;
  }

  public void setCardNumber(Long cardNumber) {
    this.cardNumber = cardNumber;
  }

  public Date getExpirationDate() {
    return expirationDate;
  }

  public void setExpirationDate(Date expirationDate) {
    this.expirationDate = expirationDate;
  }

  public int getCvv() {
    return cvv;
  }

  public void setCvv(int cvv) {
    this.cvv = cvv;
  }

  public String getCompleteName() {
    return completeName;
  }

  public void setCompleteName(String completeName) {
    this.completeName = completeName;
  }

}
