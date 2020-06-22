package ru.alfabank.alfabattle.task1.modelalfa;

import java.util.Objects;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

/**
 * BankATMStatus
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2020-06-15T16:27:15.806525800+03:00[Europe/Moscow]")
public class BankATMStatus {
  public static final String SERIALIZED_NAME_ATMS = "atms";
  @SerializedName(SERIALIZED_NAME_ATMS)
  private List<ATMStatus> atms = null;

  public static final String SERIALIZED_NAME_BANK_LICENSE = "bankLicense";
  @SerializedName(SERIALIZED_NAME_BANK_LICENSE)
  private String bankLicense;


  public BankATMStatus atms(List<ATMStatus> atms) {
    
    this.atms = atms;
    return this;
  }

  public BankATMStatus addAtmsItem(ATMStatus atmsItem) {
    if (this.atms == null) {
      this.atms = new ArrayList<ATMStatus>();
    }
    this.atms.add(atmsItem);
    return this;
  }

  @javax.annotation.Nullable

  public List<ATMStatus> getAtms() {
    return atms;
  }


  public void setAtms(List<ATMStatus> atms) {
    this.atms = atms;
  }


  public BankATMStatus bankLicense(String bankLicense) {
    
    this.bankLicense = bankLicense;
    return this;
  }

  @javax.annotation.Nullable

  public String getBankLicense() {
    return bankLicense;
  }


  public void setBankLicense(String bankLicense) {
    this.bankLicense = bankLicense;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BankATMStatus bankATMStatus = (BankATMStatus) o;
    return Objects.equals(this.atms, bankATMStatus.atms) &&
        Objects.equals(this.bankLicense, bankATMStatus.bankLicense);
  }

  @Override
  public int hashCode() {
    return Objects.hash(atms, bankLicense);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BankATMStatus {\n");
    sb.append("    atms: ").append(toIndentedString(atms)).append("\n");
    sb.append("    bankLicense: ").append(toIndentedString(bankLicense)).append("\n");
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

