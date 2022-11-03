import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IProductTransactionViewChad, NewProductTransactionViewChad } from '../product-transaction-view-chad.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProductTransactionViewChad for edit and NewProductTransactionViewChadFormGroupInput for create.
 */
type ProductTransactionViewChadFormGroupInput = IProductTransactionViewChad | PartialWithRequiredKeyOf<NewProductTransactionViewChad>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IProductTransactionViewChad | NewProductTransactionViewChad> = Omit<T, 'createdDate' | 'lastModifiedDate'> & {
  createdDate?: string | null;
  lastModifiedDate?: string | null;
};

type ProductTransactionViewChadFormRawValue = FormValueOf<IProductTransactionViewChad>;

type NewProductTransactionViewChadFormRawValue = FormValueOf<NewProductTransactionViewChad>;

type ProductTransactionViewChadFormDefaults = Pick<NewProductTransactionViewChad, 'id' | 'createdDate' | 'lastModifiedDate'>;

type ProductTransactionViewChadFormGroupContent = {
  id: FormControl<ProductTransactionViewChadFormRawValue['id'] | NewProductTransactionViewChad['id']>;
  productName: FormControl<ProductTransactionViewChadFormRawValue['productName']>;
  stockName: FormControl<ProductTransactionViewChadFormRawValue['stockName']>;
  quantite: FormControl<ProductTransactionViewChadFormRawValue['quantite']>;
  transactionType: FormControl<ProductTransactionViewChadFormRawValue['transactionType']>;
  description: FormControl<ProductTransactionViewChadFormRawValue['description']>;
  createdBy: FormControl<ProductTransactionViewChadFormRawValue['createdBy']>;
  createdDate: FormControl<ProductTransactionViewChadFormRawValue['createdDate']>;
  lastModifiedBy: FormControl<ProductTransactionViewChadFormRawValue['lastModifiedBy']>;
  lastModifiedDate: FormControl<ProductTransactionViewChadFormRawValue['lastModifiedDate']>;
};

export type ProductTransactionViewChadFormGroup = FormGroup<ProductTransactionViewChadFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProductTransactionViewChadFormService {
  createProductTransactionViewChadFormGroup(
    productTransactionView: ProductTransactionViewChadFormGroupInput = { id: null }
  ): ProductTransactionViewChadFormGroup {
    const productTransactionViewRawValue = this.convertProductTransactionViewChadToProductTransactionViewChadRawValue({
      ...this.getFormDefaults(),
      ...productTransactionView,
    });
    return new FormGroup<ProductTransactionViewChadFormGroupContent>({
      id: new FormControl(
        { value: productTransactionViewRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      productName: new FormControl(productTransactionViewRawValue.productName),
      stockName: new FormControl(productTransactionViewRawValue.stockName),
      quantite: new FormControl(productTransactionViewRawValue.quantite),
      transactionType: new FormControl(productTransactionViewRawValue.transactionType),
      description: new FormControl(productTransactionViewRawValue.description),
      createdBy: new FormControl(productTransactionViewRawValue.createdBy),
      createdDate: new FormControl(productTransactionViewRawValue.createdDate),
      lastModifiedBy: new FormControl(productTransactionViewRawValue.lastModifiedBy),
      lastModifiedDate: new FormControl(productTransactionViewRawValue.lastModifiedDate),
    });
  }

  getProductTransactionViewChad(form: ProductTransactionViewChadFormGroup): IProductTransactionViewChad | NewProductTransactionViewChad {
    return this.convertProductTransactionViewChadRawValueToProductTransactionViewChad(
      form.getRawValue() as ProductTransactionViewChadFormRawValue | NewProductTransactionViewChadFormRawValue
    );
  }

  resetForm(form: ProductTransactionViewChadFormGroup, productTransactionView: ProductTransactionViewChadFormGroupInput): void {
    const productTransactionViewRawValue = this.convertProductTransactionViewChadToProductTransactionViewChadRawValue({
      ...this.getFormDefaults(),
      ...productTransactionView,
    });
    form.reset(
      {
        ...productTransactionViewRawValue,
        id: { value: productTransactionViewRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ProductTransactionViewChadFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      createdDate: currentTime,
      lastModifiedDate: currentTime,
    };
  }

  private convertProductTransactionViewChadRawValueToProductTransactionViewChad(
    rawProductTransactionViewChad: ProductTransactionViewChadFormRawValue | NewProductTransactionViewChadFormRawValue
  ): IProductTransactionViewChad | NewProductTransactionViewChad {
    return {
      ...rawProductTransactionViewChad,
      createdDate: dayjs(rawProductTransactionViewChad.createdDate, DATE_TIME_FORMAT),
      lastModifiedDate: dayjs(rawProductTransactionViewChad.lastModifiedDate, DATE_TIME_FORMAT),
    };
  }

  private convertProductTransactionViewChadToProductTransactionViewChadRawValue(
    productTransactionView: IProductTransactionViewChad | (Partial<NewProductTransactionViewChad> & ProductTransactionViewChadFormDefaults)
  ): ProductTransactionViewChadFormRawValue | PartialWithRequiredKeyOf<NewProductTransactionViewChadFormRawValue> {
    return {
      ...productTransactionView,
      createdDate: productTransactionView.createdDate ? productTransactionView.createdDate.format(DATE_TIME_FORMAT) : undefined,
      lastModifiedDate: productTransactionView.lastModifiedDate
        ? productTransactionView.lastModifiedDate.format(DATE_TIME_FORMAT)
        : undefined,
    };
  }
}
