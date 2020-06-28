package ru.alfabank.alfabattle.task1.modelalfa;

import java.util.Objects;
import com.google.gson.annotations.SerializedName;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2020-06-15T16:27:15.806525800+03:00[Europe/Moscow]")
public class ATMStatus {
  public static final String SERIALIZED_NAME_AVAILABLE_NOW = "availableNow";
  @SerializedName(SERIALIZED_NAME_AVAILABLE_NOW)
  private AvailableNow availableNow;

  public static final String SERIALIZED_NAME_DEVICE_ID = "deviceId";
  @SerializedName(SERIALIZED_NAME_DEVICE_ID)
  private Integer deviceId;

//  public static final String SERIALIZED_NAME_RECORD_UPDATED = "recordUpdated";
//  @SerializedName(SERIALIZED_NAME_RECORD_UPDATED)
//  private OffsetDateTime recordUpdated;


  public ATMStatus availableNow(AvailableNow availableNow) {
    
    this.availableNow = availableNow;
    return this;
  }

   /**
   * Get availableNow
   * @return availableNow
  **/
  @javax.annotation.Nullable

  public AvailableNow getAvailableNow() {
    return availableNow;
  }


  public void setAvailableNow(AvailableNow availableNow) {
    this.availableNow = availableNow;
  }


  public ATMStatus deviceId(Integer deviceId) {
    
    this.deviceId = deviceId;
    return this;
  }

  @javax.annotation.Nullable

  public Integer getDeviceId() {
    return deviceId;
  }


  public void setDeviceId(Integer deviceId) {
    this.deviceId = deviceId;
  }


//  public ATMStatus recordUpdated(OffsetDateTime recordUpdated) {
//    
//    this.recordUpdated = recordUpdated;
//    return this;
//  }
//
//  @javax.annotation.Nullable
//
//  public OffsetDateTime getRecordUpdated() {
//    return recordUpdated;
//  }
//
//
//  public void setRecordUpdated(OffsetDateTime recordUpdated) {
//    this.recordUpdated = recordUpdated;
//  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ATMStatus atMStatus = (ATMStatus) o;
    return Objects.equals(this.availableNow, atMStatus.availableNow) &&
        Objects.equals(this.deviceId, atMStatus.deviceId)
//        && Objects.equals(this.recordUpdated, atMStatus.recordUpdated)
        ;
  }

  @Override
  public int hashCode() {
    return Objects.hash(availableNow, deviceId
//            , recordUpdated
            );
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ATMStatus {\n");
    sb.append("    availableNow: ").append(toIndentedString(availableNow)).append("\n");
    sb.append("    deviceId: ").append(toIndentedString(deviceId)).append("\n");
//    sb.append("    recordUpdated: ").append(toIndentedString(recordUpdated)).append("\n");
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

