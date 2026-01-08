package grupo4.banco_back.persistence.dao.jpa;

import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "credit_cards")
public class CreditCardJpaEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;
  @Column(name = "cardNumber")
  Long cardNumber;
  @Column(name = "expirationDate")
  Date expirationDate;
  @Column(name = "cvv")
  int cvv;
  @Column(name = "completeName")
  String completeName;

  public CreditCardJpaEntity() {
  }

  public CreditCardJpaEntity(Long id, Long cardNumber, Date expirationDate, int cvv, String completeName) {
    this.id = id;
    this.cardNumber = cardNumber;
    this.expirationDate = expirationDate;
    this.cvv = cvv;
    this.completeName = completeName;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
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

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof CreditCardJpaEntity other)) {
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
