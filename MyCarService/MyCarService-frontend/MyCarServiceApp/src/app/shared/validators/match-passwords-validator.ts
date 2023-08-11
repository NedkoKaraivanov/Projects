import { AbstractControl, FormGroup, ValidationErrors, ValidatorFn } from '@angular/forms';

export function matchPasswordsValidator(
  password: string,
  confirmPassword: string
): ValidatorFn {
  return (control) => {
    const group = control as FormGroup;
    const passCtrl1 = group.get(password);
    const passCtrl2 = group.get(confirmPassword);

    return passCtrl1?.value !== passCtrl2?.value
      ? { passwordMismatch: true }
      : null;
  };
}

