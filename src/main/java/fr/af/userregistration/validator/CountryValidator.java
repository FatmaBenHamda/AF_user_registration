package fr.af.userregistration.validator;

import java.util.Arrays;
import java.util.Locale;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CountryValidator implements ConstraintValidator<CountryConstraint, String> {

  @Override
  public void initialize(CountryConstraint countryConstraint) {
    ConstraintValidator.super.initialize(countryConstraint);
  }

  @Override
  public boolean isValid(String countryField,
      ConstraintValidatorContext cxt) {
    return countryField != null && Arrays.asList(Locale.getISOCountries()).contains(countryField.toUpperCase())
        && "FR".equals(countryField.toUpperCase());
  }
}
