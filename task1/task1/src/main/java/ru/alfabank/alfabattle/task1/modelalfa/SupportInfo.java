package ru.alfabank.alfabattle.task1.modelalfa;

import java.util.Objects;
import com.google.gson.annotations.SerializedName;

/**
 * SupportInfo
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2020-06-15T16:27:15.806525800+03:00[Europe/Moscow]")
public class SupportInfo {
  public static final String SERIALIZED_NAME_EMAIL = "email";
  @SerializedName(SERIALIZED_NAME_EMAIL)
  private String email;

  public static final String SERIALIZED_NAME_OTHER = "other";
  @SerializedName(SERIALIZED_NAME_OTHER)
  private String other;

  public static final String SERIALIZED_NAME_PHONE = "phone";
  @SerializedName(SERIALIZED_NAME_PHONE)
  private String phone;


  public SupportInfo email(String email) {
    
    this.email = email;
    return this;
  }

  @javax.annotation.Nullable

  public String getEmail() {
    return email;
  }


  public void setEmail(String email) {
    this.email = email;
  }


  public SupportInfo other(String other) {
    
    this.other = other;
    return this;
  }

  @javax.annotation.Nullable

  public String getOther() {
    return other;
  }


  public void setOther(String other) {
    this.other = other;
  }


  public SupportInfo phone(String phone) {
    
    this.phone = phone;
    return this;
  }

  @javax.annotation.Nullable

  public String getPhone() {
    return phone;
  }


  public void setPhone(String phone) {
    this.phone = phone;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SupportInfo supportInfo = (SupportInfo) o;
    return Objects.equals(this.email, supportInfo.email) &&
        Objects.equals(this.other, supportInfo.other) &&
        Objects.equals(this.phone, supportInfo.phone);
  }

  @Override
  public int hashCode() {
    return Objects.hash(email, other, phone);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SupportInfo {\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    other: ").append(toIndentedString(other)).append("\n");
    sb.append("    phone: ").append(toIndentedString(phone)).append("\n");
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

