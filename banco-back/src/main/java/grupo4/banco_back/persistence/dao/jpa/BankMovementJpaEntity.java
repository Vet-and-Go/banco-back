package grupo4.banco_back.persistence.dao.jpa;

import grupo4.banco_back.domain.enums.*;
import grupo4.banco_back.domain.model.CreditCard;

import jakarta.persistence.*;
import java.util.Date;
import java.math.BigDecimal;

@Entity
@Table(name = "bank_movements")
public class BankMovementJpaEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;
  @Column(name = "type")
  MOVEMENT_TYPE type;
  @Column(name = "origin")
  MOVEMENT_ORIGIN origin;
  @Column(name = "creditCard")
  CreditCard creditCard;
  @Column(name = "date")
  Date date;
  @Column(name = "amount")
  BigDecimal amount;
  @Column(name = "concept")
  String concept;

  public BankMovementJpaEntity() {
  }

  public BankMovementJpaEntity(Long id, MOVEMENT_TYPE type, MOVEMENT_ORIGIN origin, CreditCard creditCard, Date date,
      BigDecimal amount, String concept) {
    this.id = id;
    this.type = type;
    this.origin = origin;
    this.creditCard = creditCard;
    this.date = date;
    this.amount = amount;
    this.concept = concept;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
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

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof ClientJpaEntity other)) {
      return false;
    }
    return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (id != null ? id.hashCode() : 0);
    return hash;
  }
}
