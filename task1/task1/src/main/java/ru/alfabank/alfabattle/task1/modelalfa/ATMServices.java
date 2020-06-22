package ru.alfabank.alfabattle.task1.modelalfa;

import java.util.Objects;
import com.google.gson.annotations.SerializedName;

/**
 * ATMServices
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2020-06-15T16:27:15.806525800+03:00[Europe/Moscow]")
public class ATMServices {
  public static final String SERIALIZED_NAME_CARD_CASH_IN = "cardCashIn";
  @SerializedName(SERIALIZED_NAME_CARD_CASH_IN)
  private String cardCashIn;

  public static final String SERIALIZED_NAME_CARD_CASH_OUT = "cardCashOut";
  @SerializedName(SERIALIZED_NAME_CARD_CASH_OUT)
  private String cardCashOut;

  public static final String SERIALIZED_NAME_CARD_PAYMENTS = "cardPayments";
  @SerializedName(SERIALIZED_NAME_CARD_PAYMENTS)
  private String cardPayments;

  public static final String SERIALIZED_NAME_CASH_IN = "cashIn";
  @SerializedName(SERIALIZED_NAME_CASH_IN)
  private String cashIn;

  public static final String SERIALIZED_NAME_CASH_OUT = "cashOut";
  @SerializedName(SERIALIZED_NAME_CASH_OUT)
  private String cashOut;

  public static final String SERIALIZED_NAME_PAYMENTS = "payments";
  @SerializedName(SERIALIZED_NAME_PAYMENTS)
  private String payments;


  public ATMServices cardCashIn(String cardCashIn) {
    
    this.cardCashIn = cardCashIn;
    return this;
  }

  @javax.annotation.Nullable

  public String getCardCashIn() {
    return cardCashIn;
  }


  public void setCardCashIn(String cardCashIn) {
    this.cardCashIn = cardCashIn;
  }


  public ATMServices cardCashOut(String cardCashOut) {
    
    this.cardCashOut = cardCashOut;
    return this;
  }

  @javax.annotation.Nullable

  public String getCardCashOut() {
    return cardCashOut;
  }


  public void setCardCashOut(String cardCashOut) {
    this.cardCashOut = cardCashOut;
  }


  public ATMServices cardPayments(String cardPayments) {
    
    this.cardPayments = cardPayments;
    return this;
  }

  @javax.annotation.Nullable

  public String getCardPayments() {
    return cardPayments;
  }


  public void setCardPayments(String cardPayments) {
    this.cardPayments = cardPayments;
  }


  public ATMServices cashIn(String cashIn) {
    
    this.cashIn = cashIn;
    return this;
  }

  @javax.annotation.Nullable

  public String getCashIn() {
    return cashIn;
  }


  public void setCashIn(String cashIn) {
    this.cashIn = cashIn;
  }


  public ATMServices cashOut(String cashOut) {
    
    this.cashOut = cashOut;
    return this;
  }

  @javax.annotation.Nullable

  public String getCashOut() {
    return cashOut;
  }


  public void setCashOut(String cashOut) {
    this.cashOut = cashOut;
  }


  public ATMServices payments(String payments) {
    
    this.payments = payments;
    return this;
  }

  @javax.annotation.Nullable

  public String getPayments() {
    return payments;
  }


  public void setPayments(String payments) {
    this.payments = payments;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ATMServices atMServices = (ATMServices) o;
    return Objects.equals(this.cardCashIn, atMServices.cardCashIn) &&
        Objects.equals(this.cardCashOut, atMServices.cardCashOut) &&
        Objects.equals(this.cardPayments, atMServices.cardPayments) &&
        Objects.equals(this.cashIn, atMServices.cashIn) &&
        Objects.equals(this.cashOut, atMServices.cashOut) &&
        Objects.equals(this.payments, atMServices.payments);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cardCashIn, cardCashOut, cardPayments, cashIn, cashOut, payments);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ATMServices {\n");
    sb.append("    cardCashIn: ").append(toIndentedString(cardCashIn)).append("\n");
    sb.append("    cardCashOut: ").append(toIndentedString(cardCashOut)).append("\n");
    sb.append("    cardPayments: ").append(toIndentedString(cardPayments)).append("\n");
    sb.append("    cashIn: ").append(toIndentedString(cashIn)).append("\n");
    sb.append("    cashOut: ").append(toIndentedString(cashOut)).append("\n");
    sb.append("    payments: ").append(toIndentedString(payments)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

