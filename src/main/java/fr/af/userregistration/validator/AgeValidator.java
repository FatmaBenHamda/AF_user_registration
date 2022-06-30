package fr.af.userregistration.validator;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AgeValidator implements ConstraintValidator<AgeConstraint, Date> {

  @Override
  public void initialize(AgeConstraint constraintAnnotation) {
    ConstraintValidator.super.initialize(constraintAnnotation);
  }

  @Override
  public boolean isValid(Date birthDateField, ConstraintValidatorContext constraintValidatorContext) {
    if(birthDateField != null) {
      LocalDate birthDate = birthDateField.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
      LocalDate today = LocalDate.now();
      return Period.between(birthDate, today).getYears() >= 18;
    }
    return false;
  }
}
