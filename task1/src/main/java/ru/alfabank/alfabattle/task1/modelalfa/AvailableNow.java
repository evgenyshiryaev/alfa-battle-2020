package ru.alfabank.alfabattle.task1.modelalfa;

import java.util.Objects;
import com.google.gson.annotations.SerializedName;

/**
 * AvailableNow
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2020-06-15T16:27:15.806525800+03:00[Europe/Moscow]")
public class AvailableNow {
  public static final String SERIALIZED_NAME_CASH_IN = "cashIn";
  @SerializedName(SERIALIZED_NAME_CASH_IN)
  private String cashIn;

  public static final String SERIALIZED_NAME_CASH_OUT = "cashOut";
  @SerializedName(SERIALIZED_NAME_CASH_OUT)
  private String cashOut;

  public static final String SERIALIZED_NAME_ONLINE = "online";
  @SerializedName(SERIALIZED_NAME_ONLINE)
  private String online;

  public static final String SERIALIZED_NAME_PAYMENTS = "payments";
  @SerializedName(SERIALIZED_NAME_PAYMENTS)
  private String payments;


  public AvailableNow cashIn(String cashIn) {
    
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


  public AvailableNow cashOut(String cashOut) {
    
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


  public AvailableNow online(String online) {
    
    this.online = online;
    return this;
  }

  @javax.annotation.Nullable

  public String getOnline() {
    return online;
  }


  public void setOnline(String online) {
    this.online = online;
  }


  public AvailableNow payments(String payments) {
    
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
    AvailableNow availableNow = (AvailableNow) o;
    return Objects.equals(this.cashIn, availableNow.cashIn) &&
        Objects.equals(this.cashOut, availableNow.cashOut) &&
        Objects.equals(this.online, availableNow.online) &&
        Objects.equals(this.payments, availableNow.payments);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cashIn, cashOut, online, payments);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AvailableNow {\n");
    sb.append("    cashIn: ").append(toIndentedString(cashIn)).append("\n");
    sb.append("    cashOut: ").append(toIndentedString(cashOut)).append("\n");
    sb.append("    online: ").append(toIndentedString(online)).append("\n");
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

