package ru.alfabank.alfabattle.task1.modelalfa;

import java.util.Objects;
import com.google.gson.annotations.SerializedName;

/**
 * JSONResponseBankATMStatus
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2020-06-15T16:27:15.806525800+03:00[Europe/Moscow]")
public class JSONResponseBankATMStatus {
  public static final String SERIALIZED_NAME_DATA = "data";
  @SerializedName(SERIALIZED_NAME_DATA)
  private BankATMStatus data;

  public static final String SERIALIZED_NAME_ERROR = "error";
  @SerializedName(SERIALIZED_NAME_ERROR)
  private Error error;

  public static final String SERIALIZED_NAME_SUCCESS = "success";
  @SerializedName(SERIALIZED_NAME_SUCCESS)
  private Boolean success;

  public static final String SERIALIZED_NAME_TOTAL = "total";
  @SerializedName(SERIALIZED_NAME_TOTAL)
  private Integer total;


  public JSONResponseBankATMStatus data(BankATMStatus data) {
    
    this.data = data;
    return this;
  }

   /**
   * Get data
   * @return data
  **/
  @javax.annotation.Nullable

  public BankATMStatus getData() {
    return data;
  }


  public void setData(BankATMStatus data) {
    this.data = data;
  }


  public JSONResponseBankATMStatus error(Error error) {
    
    this.error = error;
    return this;
  }

   /**
   * Get error
   * @return error
  **/
  @javax.annotation.Nullable

  public Error getError() {
    return error;
  }


  public void setError(Error error) {
    this.error = error;
  }


  public JSONResponseBankATMStatus success(Boolean success) {
    
    this.success = success;
    return this;
  }

  @javax.annotation.Nullable

  public Boolean getSuccess() {
    return success;
  }


  public void setSuccess(Boolean success) {
    this.success = success;
  }


  public JSONResponseBankATMStatus total(Integer total) {
    
    this.total = total;
    return this;
  }

  @javax.annotation.Nullable

  public Integer getTotal() {
    return total;
  }


  public void setTotal(Integer total) {
    this.total = total;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    JSONResponseBankATMStatus jsONResponseBankATMStatus = (JSONResponseBankATMStatus) o;
    return Objects.equals(this.data, jsONResponseBankATMStatus.data) &&
        Objects.equals(this.error, jsONResponseBankATMStatus.error) &&
        Objects.equals(this.success, jsONResponseBankATMStatus.success) &&
        Objects.equals(this.total, jsONResponseBankATMStatus.total);
  }

  @Override
  public int hashCode() {
    return Objects.hash(data, error, success, total);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class JSONResponseBankATMStatus {\n");
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
    sb.append("    error: ").append(toIndentedString(error)).append("\n");
    sb.append("    success: ").append(toIndentedString(success)).append("\n");
    sb.append("    total: ").append(toIndentedString(total)).append("\n");
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

