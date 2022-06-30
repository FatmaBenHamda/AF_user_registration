package fr.af.userregistration.constants;

public interface ValidationMessages {

  public final String USERNAME_REQUIRED = "User name is mandatory";
  public final String BIRTHDATE_REQUIRED = "Birthdate is mandatory";
  public final String COUNTRY_REQUIRED = "Country of residence is mandatory";
  public final String COUNTRY_CONSTRAINT = "Invalid country of residence";
  public final String ADULT_CONSTRAINT = "You must be 18 or above to create an account";

  public final String INVALID_PHONE_NUMBER = "Invalid phone Number: must be 0CXXXXXXXX with C ranging from 1 to 9";
}
