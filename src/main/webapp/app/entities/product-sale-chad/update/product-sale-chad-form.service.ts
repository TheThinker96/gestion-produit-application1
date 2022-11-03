import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IProductSaleChad, NewProductSaleChad } from '../product-sale-chad.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProductSaleChad for edit and NewProductSaleChadFormGroupInput for create.
 */
type ProductSaleChadFormGroupInput = IProductSaleChad | PartialWithRequiredKeyOf<NewProductSaleChad>;

type ProductSaleChadFormDefaults = Pick<NewProductSaleChad, 'id' | 'statut'>;

type ProductSaleChadFormGroupContent = {
  id: FormControl<IProductSaleChad['id'] | NewProductSaleChad['id']>;
  quantite: FormControl<IProductSaleChad['quantite']>;
  prixTotal: FormControl<IProductSaleChad['prixTotal']>;
  statut: FormControl<IProductSaleChad['statut']>;
  product: FormControl<IProductSaleChad['product']>;
  stockProduct: FormControl<IProductSaleChad['stockProduct']>;
  userSaleAccount: FormControl<IProductSaleChad['userSaleAccount']>;
};

export type ProductSaleChadFormGroup = FormGroup<ProductSaleChadFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProductSaleChadFormService {
  createProductSaleChadFormGroup(productSale: ProductSaleChadFormGroupInput = { id: null }): ProductSaleChadFormGroup {
    const productSaleRawValue = {
      ...this.getFormDefaults(),
      ...productSale,
    };
    return new FormGroup<ProductSaleChadFormGroupContent>({
      id: new FormControl(
        { value: productSaleRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      quantite: new FormControl(productSaleRawValue.quantite, {
        validators: [Validators.required],
      }),
      prixTotal: new FormControl(productSaleRawValue.prixTotal, {
        validators: [Validators.required],
      }),
      statut: new FormControl(productSaleRawValue.statut, {
        validators: [Validators.required],
      }),
      product: new FormControl(productSaleRawValue.product),
      stockProduct: new FormControl(productSaleRawValue.stockProduct),
      userSaleAccount: new FormControl(productSaleRawValue.userSaleAccount),
    });
  }

  getProductSaleChad(form: ProductSaleChadFormGroup): IProductSaleChad | NewProductSaleChad {
    return form.getRawValue() as IProductSaleChad | NewProductSaleChad;
  }

  resetForm(form: ProductSaleChadFormGroup, productSale: ProductSaleChadFormGroupInput): void {
    const productSaleRawValue = { ...this.getFormDefaults(), ...productSale };
    form.reset(
      {
        ...productSaleRawValue,
        id: { value: productSaleRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ProductSaleChadFormDefaults {
    return {
      id: null,
      statut: false,
    };
  }
}
