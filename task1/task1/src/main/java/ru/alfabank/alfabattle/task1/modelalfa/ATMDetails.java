package ru.alfabank.alfabattle.task1.modelalfa;

import java.util.Objects;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2020-06-15T16:27:15.806525800+03:00[Europe/Moscow]")
public class ATMDetails {
  public static final String SERIALIZED_NAME_ADDRESS = "address";
  @SerializedName(SERIALIZED_NAME_ADDRESS)
  private PostAddress address;

  public static final String SERIALIZED_NAME_ADDRESS_COMMENTS = "addressComments";
  @SerializedName(SERIALIZED_NAME_ADDRESS_COMMENTS)
  private String addressComments;

  public static final String SERIALIZED_NAME_AVAILABLE_PAYMENT_SYSTEMS = "availablePaymentSystems";
  @SerializedName(SERIALIZED_NAME_AVAILABLE_PAYMENT_SYSTEMS)
  private List<String> availablePaymentSystems = null;

  public static final String SERIALIZED_NAME_CASH_IN_CURRENCIES = "cashInCurrencies";
  @SerializedName(SERIALIZED_NAME_CASH_IN_CURRENCIES)
  private List<String> cashInCurrencies = null;

  public static final String SERIALIZED_NAME_CASH_OUT_CURRENCIES = "cashOutCurrencies";
  @SerializedName(SERIALIZED_NAME_CASH_OUT_CURRENCIES)
  private List<String> cashOutCurrencies = null;

  public static final String SERIALIZED_NAME_COORDINATES = "coordinates";
  @SerializedName(SERIALIZED_NAME_COORDINATES)
  private Coordinates coordinates;

  public static final String SERIALIZED_NAME_DEVICE_ID = "deviceId";
  @SerializedName(SERIALIZED_NAME_DEVICE_ID)
  private Integer deviceId;

  public static final String SERIALIZED_NAME_NFC = "nfc";
  @SerializedName(SERIALIZED_NAME_NFC)
  private String nfc;

  public static final String SERIALIZED_NAME_PUBLIC_ACCESS = "publicAccess";
  @SerializedName(SERIALIZED_NAME_PUBLIC_ACCESS)
  private String publicAccess;

//  public static final String SERIALIZED_NAME_RECORD_UPDATED = "recordUpdated";
//  @SerializedName(SERIALIZED_NAME_RECORD_UPDATED)
//  private OffsetDateTime recordUpdated;

  public static final String SERIALIZED_NAME_SERVICES = "services";
  @SerializedName(SERIALIZED_NAME_SERVICES)
  private ATMServices services;

  public static final String SERIALIZED_NAME_SUPPORT_INFO = "supportInfo";
  @SerializedName(SERIALIZED_NAME_SUPPORT_INFO)
  private SupportInfo supportInfo;

  public static final String SERIALIZED_NAME_TIME_ACCESS = "timeAccess";
  @SerializedName(SERIALIZED_NAME_TIME_ACCESS)
  private ATMAccess timeAccess;

  public static final String SERIALIZED_NAME_TIME_SHIFT = "timeShift";
  @SerializedName(SERIALIZED_NAME_TIME_SHIFT)
  private Integer timeShift;


  public ATMDetails address(PostAddress address) {
    
    this.address = address;
    return this;
  }

   /**
   * Get address
   * @return address
  **/
  @javax.annotation.Nullable

  public PostAddress getAddress() {
    return address;
  }


  public void setAddress(PostAddress address) {
    this.address = address;
  }


  public ATMDetails addressComments(String addressComments) {
    
    this.addressComments = addressComments;
    return this;
  }

  @javax.annotation.Nullable

  public String getAddressComments() {
    return addressComments;
  }


  public void setAddressComments(String addressComments) {
    this.addressComments = addressComments;
  }


  public ATMDetails availablePaymentSystems(List<String> availablePaymentSystems) {
    
    this.availablePaymentSystems = availablePaymentSystems;
    return this;
  }

  public ATMDetails addAvailablePaymentSystemsItem(String availablePaymentSystemsItem) {
    if (this.availablePaymentSystems == null) {
      this.availablePaymentSystems = new ArrayList<String>();
    }
    this.availablePaymentSystems.add(availablePaymentSystemsItem);
    return this;
  }

   /**
   * Get availablePaymentSystems
   * @return availablePaymentSystems
  **/
  @javax.annotation.Nullable

  public List<String> getAvailablePaymentSystems() {
    return availablePaymentSystems;
  }


  public void setAvailablePaymentSystems(List<String> availablePaymentSystems) {
    this.availablePaymentSystems = availablePaymentSystems;
  }


  public ATMDetails cashInCurrencies(List<String> cashInCurrencies) {
    
    this.cashInCurrencies = cashInCurrencies;
    return this;
  }

  public ATMDetails addCashInCurrenciesItem(String cashInCurrenciesItem) {
    if (this.cashInCurrencies == null) {
      this.cashInCurrencies = new ArrayList<String>();
    }
    this.cashInCurrencies.add(cashInCurrenciesItem);
    return this;
  }

  @javax.annotation.Nullable

  public List<String> getCashInCurrencies() {
    return cashInCurrencies;
  }


  public void setCashInCurrencies(List<String> cashInCurrencies) {
    this.cashInCurrencies = cashInCurrencies;
  }


  public ATMDetails cashOutCurrencies(List<String> cashOutCurrencies) {
    
    this.cashOutCurrencies = cashOutCurrencies;
    return this;
  }

  public ATMDetails addCashOutCurrenciesItem(String cashOutCurrenciesItem) {
    if (this.cashOutCurrencies == null) {
      this.cashOutCurrencies = new ArrayList<String>();
    }
    this.cashOutCurrencies.add(cashOutCurrenciesItem);
    return this;
  }

  @javax.annotation.Nullable

  public List<String> getCashOutCurrencies() {
    return cashOutCurrencies;
  }


  public void setCashOutCurrencies(List<String> cashOutCurrencies) {
    this.cashOutCurrencies = cashOutCurrencies;
  }


  public ATMDetails coordinates(Coordinates coordinates) {
    
    this.coordinates = coordinates;
    return this;
  }

   /**
   * Get coordinates
   * @return coordinates
  **/
  @javax.annotation.Nullable

  public Coordinates getCoordinates() {
    return coordinates;
  }


  public void setCoordinates(Coordinates coordinates) {
    this.coordinates = coordinates;
  }


  public ATMDetails deviceId(Integer deviceId) {
    
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


  public ATMDetails nfc(String nfc) {
    
    this.nfc = nfc;
    return this;
  }

   /**
   * Get nfc
   * @return nfc
  **/
  @javax.annotation.Nullable

  public String getNfc() {
    return nfc;
  }


  public void setNfc(String nfc) {
    this.nfc = nfc;
  }


  public ATMDetails publicAccess(String publicAccess) {
    
    this.publicAccess = publicAccess;
    return this;
  }

  @javax.annotation.Nullable

  public String getPublicAccess() {
    return publicAccess;
  }


  public void setPublicAccess(String publicAccess) {
    this.publicAccess = publicAccess;
  }


//  public ATMDetails recordUpdated(OffsetDateTime recordUpdated) {
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


  public ATMDetails services(ATMServices services) {
    
    this.services = services;
    return this;
  }

   /**
   * Get services
   * @return services
  **/
  @javax.annotation.Nullable

  public ATMServices getServices() {
    return services;
  }


  public void setServices(ATMServices services) {
    this.services = services;
  }


  public ATMDetails supportInfo(SupportInfo supportInfo) {
    
    this.supportInfo = supportInfo;
    return this;
  }

   /**
   * Get supportInfo
   * @return supportInfo
  **/
  @javax.annotation.Nullable

  public SupportInfo getSupportInfo() {
    return supportInfo;
  }


  public void setSupportInfo(SupportInfo supportInfo) {
    this.supportInfo = supportInfo;
  }


  public ATMDetails timeAccess(ATMAccess timeAccess) {
    
    this.timeAccess = timeAccess;
    return this;
  }

   /**
   * Get timeAccess
   * @return timeAccess
  **/
  @javax.annotation.Nullable

  public ATMAccess getTimeAccess() {
    return timeAccess;
  }


  public void setTimeAccess(ATMAccess timeAccess) {
    this.timeAccess = timeAccess;
  }


  public ATMDetails timeShift(Integer timeShift) {
    
    this.timeShift = timeShift;
    return this;
  }

  @javax.annotation.Nullable

  public Integer getTimeShift() {
    return timeShift;
  }


  public void setTimeShift(Integer timeShift) {
    this.timeShift = timeShift;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ATMDetails atMDetails = (ATMDetails) o;
    return Objects.equals(this.address, atMDetails.address) &&
        Objects.equals(this.addressComments, atMDetails.addressComments) &&
        Objects.equals(this.availablePaymentSystems, atMDetails.availablePaymentSystems) &&
        Objects.equals(this.cashInCurrencies, atMDetails.cashInCurrencies) &&
        Objects.equals(this.cashOutCurrencies, atMDetails.cashOutCurrencies) &&
        Objects.equals(this.coordinates, atMDetails.coordinates) &&
        Objects.equals(this.deviceId, atMDetails.deviceId) &&
        Objects.equals(this.nfc, atMDetails.nfc) &&
        Objects.equals(this.publicAccess, atMDetails.publicAccess) &&
//        Objects.equals(this.recordUpdated, atMDetails.recordUpdated) &&
        Objects.equals(this.services, atMDetails.services) &&
        Objects.equals(this.supportInfo, atMDetails.supportInfo) &&
        Objects.equals(this.timeAccess, atMDetails.timeAccess) &&
        Objects.equals(this.timeShift, atMDetails.timeShift);
  }

  @Override
  public int hashCode() {
    return Objects.hash(address, addressComments, availablePaymentSystems, cashInCurrencies, cashOutCurrencies, coordinates, deviceId, nfc, publicAccess,
//            recordUpdated,
            services, supportInfo, timeAccess, timeShift);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ATMDetails {\n");
    sb.append("    address: ").append(toIndentedString(address)).append("\n");
    sb.append("    addressComments: ").append(toIndentedString(addressComments)).append("\n");
    sb.append("    availablePaymentSystems: ").append(toIndentedString(availablePaymentSystems)).append("\n");
    sb.append("    cashInCurrencies: ").append(toIndentedString(cashInCurrencies)).append("\n");
    sb.append("    cashOutCurrencies: ").append(toIndentedString(cashOutCurrencies)).append("\n");
    sb.append("    coordinates: ").append(toIndentedString(coordinates)).append("\n");
    sb.append("    deviceId: ").append(toIndentedString(deviceId)).append("\n");
    sb.append("    nfc: ").append(toIndentedString(nfc)).append("\n");
    sb.append("    publicAccess: ").append(toIndentedString(publicAccess)).append("\n");
//    sb.append("    recordUpdated: ").append(toIndentedString(recordUpdated)).append("\n");
    sb.append("    services: ").append(toIndentedString(services)).append("\n");
    sb.append("    supportInfo: ").append(toIndentedString(supportInfo)).append("\n");
    sb.append("    timeAccess: ").append(toIndentedString(timeAccess)).append("\n");
    sb.append("    timeShift: ").append(toIndentedString(timeShift)).append("\n");
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

