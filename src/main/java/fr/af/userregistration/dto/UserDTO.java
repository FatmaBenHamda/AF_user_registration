package fr.af.userregistration.dto;

import fr.af.userregistration.constants.ValidationMessages;
import fr.af.userregistration.enumeration.Gender;
import fr.af.userregistration.validator.AgeConstraint;
import fr.af.userregistration.validator.CountryConstraint;
import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserDTO implements Serializable {

  /**
   * Represents a user id.
   */
  private Long id;
  @NotBlank(message = ValidationMessages.USERNAME_REQUIRED)
  private String userName;

  /**
   * Represents first name of a user
   * It is optional.
   */
  private String firstName;

  /**
   * Represents last name of a user
   * It is optional.
   */
  private String lastName;

  /**
   * Represents the residence country of a user.
   * It's mandatory.
   * A user must be a FRENCH resident.
   * It is ISO 34466-1 Alpha-2 code.
   * Ex : AL,BV,FR
   */
  @NotNull( message = ValidationMessages.COUNTRY_REQUIRED)
  @CountryConstraint (message = ValidationMessages.COUNTRY_CONSTRAINT)
  private String residenceCountry;

  /**
   * Represents the birthdate of a user.
   * It's mandatory.
   * A user must be 18 or above
   */
  @NotNull( message = ValidationMessages.BIRTHDATE_REQUIRED)
  @AgeConstraint(message = ValidationMessages.ADULT_CONSTRAINT)
  private Date birthDate;

  /**
   * Represents the phone number of a user.
   * It's optional.
   * It must be french phone number.
   * It starts with 0, followed y number between 1 and 9.
   * It has to be 10 digits number
   */
  @Pattern(regexp = "0[1-9]\\d{8}", message = ValidationMessages.INVALID_PHONE_NUMBER)
  private String phoneNumber;

  /**
   * Represents the gender of a user.
   * It's optional.
   * It must be either:
   * F - For Female
   * M- For Male
   * OTHER - For Other
   */
  private Gender gender;

  /**
   * Gets id of user
   * @return user's id
   */
  public Long getId() {
    return id;
  }

  /**
   * Changes the id of user
   * @param id - new user id
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Gets username of user
   * @return user's username
   */
  public String getUserName() {
    return userName;
  }

  /**
   * Changes the username of user
   * @param userName - new user username.
   *           Should be unique.
   */
  public void setUserName(String userName) {
    this.userName = userName;
  }

  /**
   * Gets first name of user
   * @return user's id
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * Changes the first name of user
   * @param firstName - new user first name.
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * Gets last name of user
   * @return user's id
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * Changes the last name of user
   * @param lastName - new user last name.
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * Gets user's country of residence
   * @return user's id
   */
  public String getResidenceCountry() {
    return residenceCountry;
  }

  /**
   * Changes user's country of residence
   * @param residenceCountry - new user country of residence.
   *                         Should be French resident.
   *                         Should be ISO 34466-1 Alpha-2 code.
   */
  public void setResidenceCountry(String residenceCountry) {
    this.residenceCountry = residenceCountry;
  }

  /**
   * Gets birthdate of user
   * @return user's birthdate
   */
  public Date getBirthDate() {
    return birthDate;
  }

  /**
   * Changes birthdate of user
   * @param birthDate - new user birthdate.
   *                         Should be 18 or above.
   */
  public void setBirthDate(Date birthDate) {
    this.birthDate = birthDate;
  }

  /**
   * Gets phone number of user
   * @return user's id
   */
  public String getPhoneNumber() {
    return phoneNumber;
  }

  /**
   * Changes phone number of user
   * @param phoneNumber - new user phone number.
   *                         Should be French Number.
   *                         Should start with 0, followed y number between 1 and 9.
   *                         Should be 10 digits number
   */
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  /**
   * Gets gender of user
   * @return user's id
   */
  public Gender getGender() {
    return gender;
  }

  /**
   * Changes gender of user
   * @param gender - new user gender.
   *               Should be either:
   *               F - For Female
   *               M- For Male
   *               OTHER - For Other
   */
  public void setGender(Gender gender) {
    this.gender = gender;
  }


  @Override
  public String toString() {
    return "UserDTO{" +
        "id=" + id +
        ", userName='" + userName + '\'' +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", residenceCountry='" + residenceCountry + '\'' +
        ", birthDate=" + birthDate +
        ", phoneNumber='" + phoneNumber + '\'' +
        ", gender=" + gender +
        '}';
  }
}
