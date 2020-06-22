package ru.alfabank.alfabattle.task1.modelalfa;

import java.util.Objects;
import com.google.gson.annotations.SerializedName;

/**
 * JSONResponseBankATMDetails
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2020-06-15T16:27:15.806525800+03:00[Europe/Moscow]")
public class JSONResponseBankATMDetails {
  public static final String SERIALIZED_NAME_DATA = "data";
  @SerializedName(SERIALIZED_NAME_DATA)
  private BankATMDetails data;

  public static final String SERIALIZED_NAME_ERROR = "error";
  @SerializedName(SERIALIZED_NAME_ERROR)
  private Error error;

  public static final String SERIALIZED_NAME_SUCCESS = "success";
  @SerializedName(SERIALIZED_NAME_SUCCESS)
  private Boolean success;

  public static final String SERIALIZED_NAME_TOTAL = "total";
  @SerializedName(SERIALIZED_NAME_TOTAL)
  private Integer total;


  public JSONResponseBankATMDetails data(BankATMDetails data) {
    
    this.data = data;
    return this;
  }

   /**
   * Get data
   * @return data
  **/
  @javax.annotation.Nullable

  public BankATMDetails getData() {
    return data;
  }


  public void setData(BankATMDetails data) {
    this.data = data;
  }


  public JSONResponseBankATMDetails error(Error error) {
    
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


  public JSONResponseBankATMDetails success(Boolean success) {
    
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


  public JSONResponseBankATMDetails total(Integer total) {
    
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
    JSONResponseBankATMDetails jsONResponseBankATMDetails = (JSONResponseBankATMDetails) o;
    return Objects.equals(this.data, jsONResponseBankATMDetails.data) &&
        Objects.equals(this.error, jsONResponseBankATMDetails.error) &&
        Objects.equals(this.success, jsONResponseBankATMDetails.success) &&
        Objects.equals(this.total, jsONResponseBankATMDetails.total);
  }

  @Override
  public int hashCode() {
    return Objects.hash(data, error, success, total);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class JSONResponseBankATMDetails {\n");
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

