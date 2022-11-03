import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IProductChad, NewProductChad } from '../product-chad.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProductChad for edit and NewProductChadFormGroupInput for create.
 */
type ProductChadFormGroupInput = IProductChad | PartialWithRequiredKeyOf<NewProductChad>;

type ProductChadFormDefaults = Pick<NewProductChad, 'id'>;

type ProductChadFormGroupContent = {
  id: FormControl<IProductChad['id'] | NewProductChad['id']>;
  name: FormControl<IProductChad['name']>;
  prix: FormControl<IProductChad['prix']>;
};

export type ProductChadFormGroup = FormGroup<ProductChadFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProductChadFormService {
  createProductChadFormGroup(product: ProductChadFormGroupInput = { id: null }): ProductChadFormGroup {
    const productRawValue = {
      ...this.getFormDefaults(),
      ...product,
    };
    return new FormGroup<ProductChadFormGroupContent>({
      id: new FormControl(
        { value: productRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(productRawValue.name, {
        validators: [Validators.required, Validators.minLength(3)],
      }),
      prix: new FormControl(productRawValue.prix),
    });
  }

  getProductChad(form: ProductChadFormGroup): IProductChad | NewProductChad {
    return form.getRawValue() as IProductChad | NewProductChad;
  }

  resetForm(form: ProductChadFormGroup, product: ProductChadFormGroupInput): void {
    const productRawValue = { ...this.getFormDefaults(), ...product };
    form.reset(
      {
        ...productRawValue,
        id: { value: productRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ProductChadFormDefaults {
    return {
      id: null,
    };
  }
}
