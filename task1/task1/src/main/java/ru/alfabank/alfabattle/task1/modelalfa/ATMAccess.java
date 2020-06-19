package ru.alfabank.alfabattle.task1.modelalfa;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

/**
 * ATMAccess
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2020-06-15T16:27:15.806525800+03:00[Europe/Moscow]")
public class ATMAccess {
  @JsonAdapter(ModeEnum.Adapter.class)
  public enum ModeEnum {
    @JsonProperty("alltime")
    ALLTIME("alltime"),
    
    @JsonProperty("schedule")
    SCHEDULE("schedule"),
    
    @JsonProperty("noinfo")
    NOINFO("noinfo");

    private String value;

    ModeEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static ModeEnum fromValue(String value) {
      for (ModeEnum b : ModeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<ModeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final ModeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public ModeEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return ModeEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_MODE = "mode";
  @SerializedName(SERIALIZED_NAME_MODE)
  private ModeEnum mode;

  public static final String SERIALIZED_NAME_SCHEDULE = "schedule";
  @SerializedName(SERIALIZED_NAME_SCHEDULE)
  private String schedule;


  public ATMAccess mode(ModeEnum mode) {
    
    this.mode = mode;
    return this;
  }

  @javax.annotation.Nullable

  public ModeEnum getMode() {
    return mode;
  }


  public void setMode(ModeEnum mode) {
    this.mode = mode;
  }


  public ATMAccess schedule(String schedule) {
    
    this.schedule = schedule;
    return this;
  }

  @javax.annotation.Nullable

  public String getSchedule() {
    return schedule;
  }


  public void setSchedule(String schedule) {
    this.schedule = schedule;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ATMAccess atMAccess = (ATMAccess) o;
    return Objects.equals(this.mode, atMAccess.mode) &&
        Objects.equals(this.schedule, atMAccess.schedule);
  }

  @Override
  public int hashCode() {
    return Objects.hash(mode, schedule);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ATMAccess {\n");
    sb.append("    mode: ").append(toIndentedString(mode)).append("\n");
    sb.append("    schedule: ").append(toIndentedString(schedule)).append("\n");
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

