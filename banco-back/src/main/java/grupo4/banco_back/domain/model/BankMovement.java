package grupo4.banco_back.domain.model;

import java.math.BigDecimal;
import java.util.Date;

import grupo4.banco_back.domain.enums.*;

public class BankMovement {
  long id;
  MOVEMENT_TYPE type;
  MOVEMENT_ORIGIN origin;
  CreditCard creditCard;
  Date date;
  BigDecimal amount;
  String concept;

  public BankMovement(long id, MOVEMENT_TYPE type, MOVEMENT_ORIGIN origin, CreditCard creditCard, Date date,
      BigDecimal amount, String concept) {
    this.id = id;
    this.type = type;
    this.origin = origin;
    this.creditCard = creditCard;
    this.date = date;
    this.amount = amount;
    this.concept = concept;
  }

  public BankMovement() {
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public MOVEMENT_TYPE getType() {
    return type;
  }

  public void setType(MOVEMENT_TYPE type) {
    this.type = type;
  }

  public MOVEMENT_ORIGIN getOrigin() {
    return origin;
  }

  public void setOrigin(MOVEMENT_ORIGIN origin) {
    this.origin = origin;
  }

  public CreditCard getCreditCard() {
    return creditCard;
  }

  public void setCreditCard(CreditCard creditCard) {
    this.creditCard = creditCard;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public String getConcept() {
    return concept;
  }

  public void setConcept(String concept) {
    this.concept = concept;
  }

}
