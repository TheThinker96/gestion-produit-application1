import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IProductSaleViewChad, NewProductSaleViewChad } from '../product-sale-view-chad.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProductSaleViewChad for edit and NewProductSaleViewChadFormGroupInput for create.
 */
type ProductSaleViewChadFormGroupInput = IProductSaleViewChad | PartialWithRequiredKeyOf<NewProductSaleViewChad>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IProductSaleViewChad | NewProductSaleViewChad> = Omit<T, 'createdDate' | 'lastModifiedDate'> & {
  createdDate?: string | null;
  lastModifiedDate?: string | null;
};

type ProductSaleViewChadFormRawValue = FormValueOf<IProductSaleViewChad>;

type NewProductSaleViewChadFormRawValue = FormValueOf<NewProductSaleViewChad>;

type ProductSaleViewChadFormDefaults = Pick<NewProductSaleViewChad, 'id' | 'createdDate' | 'lastModifiedDate'>;

type ProductSaleViewChadFormGroupContent = {
  id: FormControl<ProductSaleViewChadFormRawValue['id'] | NewProductSaleViewChad['id']>;
  productName: FormControl<ProductSaleViewChadFormRawValue['productName']>;
  stockName: FormControl<ProductSaleViewChadFormRawValue['stockName']>;
  quantite: FormControl<ProductSaleViewChadFormRawValue['quantite']>;
  productPrice: FormControl<ProductSaleViewChadFormRawValue['productPrice']>;
  total: FormControl<ProductSaleViewChadFormRawValue['total']>;
  createdBy: FormControl<ProductSaleViewChadFormRawValue['createdBy']>;
  createdDate: FormControl<ProductSaleViewChadFormRawValue['createdDate']>;
  lastModifiedBy: FormControl<ProductSaleViewChadFormRawValue['lastModifiedBy']>;
  lastModifiedDate: FormControl<ProductSaleViewChadFormRawValue['lastModifiedDate']>;
};

export type ProductSaleViewChadFormGroup = FormGroup<ProductSaleViewChadFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProductSaleViewChadFormService {
  createProductSaleViewChadFormGroup(productSaleView: ProductSaleViewChadFormGroupInput = { id: null }): ProductSaleViewChadFormGroup {
    const productSaleViewRawValue = this.convertProductSaleViewChadToProductSaleViewChadRawValue({
      ...this.getFormDefaults(),
      ...productSaleView,
    });
    return new FormGroup<ProductSaleViewChadFormGroupContent>({
      id: new FormControl(
        { value: productSaleViewRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      productName: new FormControl(productSaleViewRawValue.productName),
      stockName: new FormControl(productSaleViewRawValue.stockName),
      quantite: new FormControl(productSaleViewRawValue.quantite),
      productPrice: new FormControl(productSaleViewRawValue.productPrice),
      total: new FormControl(productSaleViewRawValue.total),
      createdBy: new FormControl(productSaleViewRawValue.createdBy),
      createdDate: new FormControl(productSaleViewRawValue.createdDate),
      lastModifiedBy: new FormControl(productSaleViewRawValue.lastModifiedBy),
      lastModifiedDate: new FormControl(productSaleViewRawValue.lastModifiedDate),
    });
  }

  getProductSaleViewChad(form: ProductSaleViewChadFormGroup): IProductSaleViewChad | NewProductSaleViewChad {
    return this.convertProductSaleViewChadRawValueToProductSaleViewChad(
      form.getRawValue() as ProductSaleViewChadFormRawValue | NewProductSaleViewChadFormRawValue
    );
  }

  resetForm(form: ProductSaleViewChadFormGroup, productSaleView: ProductSaleViewChadFormGroupInput): void {
    const productSaleViewRawValue = this.convertProductSaleViewChadToProductSaleViewChadRawValue({
      ...this.getFormDefaults(),
      ...productSaleView,
    });
    form.reset(
      {
        ...productSaleViewRawValue,
        id: { value: productSaleViewRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ProductSaleViewChadFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      createdDate: currentTime,
      lastModifiedDate: currentTime,
    };
  }

  private convertProductSaleViewChadRawValueToProductSaleViewChad(
    rawProductSaleViewChad: ProductSaleViewChadFormRawValue | NewProductSaleViewChadFormRawValue
  ): IProductSaleViewChad | NewProductSaleViewChad {
    return {
      ...rawProductSaleViewChad,
      createdDate: dayjs(rawProductSaleViewChad.createdDate, DATE_TIME_FORMAT),
      lastModifiedDate: dayjs(rawProductSaleViewChad.lastModifiedDate, DATE_TIME_FORMAT),
    };
  }

  private convertProductSaleViewChadToProductSaleViewChadRawValue(
    productSaleView: IProductSaleViewChad | (Partial<NewProductSaleViewChad> & ProductSaleViewChadFormDefaults)
  ): ProductSaleViewChadFormRawValue | PartialWithRequiredKeyOf<NewProductSaleViewChadFormRawValue> {
    return {
      ...productSaleView,
      createdDate: productSaleView.createdDate ? productSaleView.createdDate.format(DATE_TIME_FORMAT) : undefined,
      lastModifiedDate: productSaleView.lastModifiedDate ? productSaleView.lastModifiedDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
