import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IUserCashBackChad, NewUserCashBackChad } from '../user-cash-back-chad.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IUserCashBackChad for edit and NewUserCashBackChadFormGroupInput for create.
 */
type UserCashBackChadFormGroupInput = IUserCashBackChad | PartialWithRequiredKeyOf<NewUserCashBackChad>;

type UserCashBackChadFormDefaults = Pick<NewUserCashBackChad, 'id'>;

type UserCashBackChadFormGroupContent = {
  id: FormControl<IUserCashBackChad['id'] | NewUserCashBackChad['id']>;
  montant: FormControl<IUserCashBackChad['montant']>;
  balance: FormControl<IUserCashBackChad['balance']>;
  statut: FormControl<IUserCashBackChad['statut']>;
};

export type UserCashBackChadFormGroup = FormGroup<UserCashBackChadFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class UserCashBackChadFormService {
  createUserCashBackChadFormGroup(userCashBack: UserCashBackChadFormGroupInput = { id: null }): UserCashBackChadFormGroup {
    const userCashBackRawValue = {
      ...this.getFormDefaults(),
      ...userCashBack,
    };
    return new FormGroup<UserCashBackChadFormGroupContent>({
      id: new FormControl(
        { value: userCashBackRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      montant: new FormControl(userCashBackRawValue.montant, {
        validators: [Validators.required],
      }),
      balance: new FormControl(userCashBackRawValue.balance, {
        validators: [Validators.required],
      }),
      statut: new FormControl(userCashBackRawValue.statut, {
        validators: [Validators.required],
      }),
    });
  }

  getUserCashBackChad(form: UserCashBackChadFormGroup): IUserCashBackChad | NewUserCashBackChad {
    return form.getRawValue() as IUserCashBackChad | NewUserCashBackChad;
  }

  resetForm(form: UserCashBackChadFormGroup, userCashBack: UserCashBackChadFormGroupInput): void {
    const userCashBackRawValue = { ...this.getFormDefaults(), ...userCashBack };
    form.reset(
      {
        ...userCashBackRawValue,
        id: { value: userCashBackRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): UserCashBackChadFormDefaults {
    return {
      id: null,
    };
  }
}
