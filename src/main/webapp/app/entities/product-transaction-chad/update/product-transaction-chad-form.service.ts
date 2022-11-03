import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IProductTransactionChad, NewProductTransactionChad } from '../product-transaction-chad.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProductTransactionChad for edit and NewProductTransactionChadFormGroupInput for create.
 */
type ProductTransactionChadFormGroupInput = IProductTransactionChad | PartialWithRequiredKeyOf<NewProductTransactionChad>;

type ProductTransactionChadFormDefaults = Pick<NewProductTransactionChad, 'id'>;

type ProductTransactionChadFormGroupContent = {
  id: FormControl<IProductTransactionChad['id'] | NewProductTransactionChad['id']>;
  transactionType: FormControl<IProductTransactionChad['transactionType']>;
  description: FormControl<IProductTransactionChad['description']>;
  stockProduct: FormControl<IProductTransactionChad['stockProduct']>;
  product: FormControl<IProductTransactionChad['product']>;
};

export type ProductTransactionChadFormGroup = FormGroup<ProductTransactionChadFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProductTransactionChadFormService {
  createProductTransactionChadFormGroup(
    productTransaction: ProductTransactionChadFormGroupInput = { id: null }
  ): ProductTransactionChadFormGroup {
    const productTransactionRawValue = {
      ...this.getFormDefaults(),
      ...productTransaction,
    };
    return new FormGroup<ProductTransactionChadFormGroupContent>({
      id: new FormControl(
        { value: productTransactionRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      transactionType: new FormControl(productTransactionRawValue.transactionType, {
        validators: [Validators.required],
      }),
      description: new FormControl(productTransactionRawValue.description),
      stockProduct: new FormControl(productTransactionRawValue.stockProduct),
      product: new FormControl(productTransactionRawValue.product),
    });
  }

  getProductTransactionChad(form: ProductTransactionChadFormGroup): IProductTransactionChad | NewProductTransactionChad {
    return form.getRawValue() as IProductTransactionChad | NewProductTransactionChad;
  }

  resetForm(form: ProductTransactionChadFormGroup, productTransaction: ProductTransactionChadFormGroupInput): void {
    const productTransactionRawValue = { ...this.getFormDefaults(), ...productTransaction };
    form.reset(
      {
        ...productTransactionRawValue,
        id: { value: productTransactionRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ProductTransactionChadFormDefaults {
    return {
      id: null,
    };
  }
}
