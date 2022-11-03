import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IUserSaleAccountChad, NewUserSaleAccountChad } from '../user-sale-account-chad.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IUserSaleAccountChad for edit and NewUserSaleAccountChadFormGroupInput for create.
 */
type UserSaleAccountChadFormGroupInput = IUserSaleAccountChad | PartialWithRequiredKeyOf<NewUserSaleAccountChad>;

type UserSaleAccountChadFormDefaults = Pick<NewUserSaleAccountChad, 'id'>;

type UserSaleAccountChadFormGroupContent = {
  id: FormControl<IUserSaleAccountChad['id'] | NewUserSaleAccountChad['id']>;
  statut: FormControl<IUserSaleAccountChad['statut']>;
  balance: FormControl<IUserSaleAccountChad['balance']>;
};

export type UserSaleAccountChadFormGroup = FormGroup<UserSaleAccountChadFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class UserSaleAccountChadFormService {
  createUserSaleAccountChadFormGroup(userSaleAccount: UserSaleAccountChadFormGroupInput = { id: null }): UserSaleAccountChadFormGroup {
    const userSaleAccountRawValue = {
      ...this.getFormDefaults(),
      ...userSaleAccount,
    };
    return new FormGroup<UserSaleAccountChadFormGroupContent>({
      id: new FormControl(
        { value: userSaleAccountRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      statut: new FormControl(userSaleAccountRawValue.statut),
      balance: new FormControl(userSaleAccountRawValue.balance),
    });
  }

  getUserSaleAccountChad(form: UserSaleAccountChadFormGroup): IUserSaleAccountChad | NewUserSaleAccountChad {
    return form.getRawValue() as IUserSaleAccountChad | NewUserSaleAccountChad;
  }

  resetForm(form: UserSaleAccountChadFormGroup, userSaleAccount: UserSaleAccountChadFormGroupInput): void {
    const userSaleAccountRawValue = { ...this.getFormDefaults(), ...userSaleAccount };
    form.reset(
      {
        ...userSaleAccountRawValue,
        id: { value: userSaleAccountRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): UserSaleAccountChadFormDefaults {
    return {
      id: null,
    };
  }
}
