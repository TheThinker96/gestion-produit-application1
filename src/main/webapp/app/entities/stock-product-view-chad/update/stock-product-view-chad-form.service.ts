import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IStockProductViewChad, NewStockProductViewChad } from '../stock-product-view-chad.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IStockProductViewChad for edit and NewStockProductViewChadFormGroupInput for create.
 */
type StockProductViewChadFormGroupInput = IStockProductViewChad | PartialWithRequiredKeyOf<NewStockProductViewChad>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IStockProductViewChad | NewStockProductViewChad> = Omit<
  T,
  'deliveryDate' | 'expirationDate' | 'createdDate' | 'lastModifiedDate'
> & {
  deliveryDate?: string | null;
  expirationDate?: string | null;
  createdDate?: string | null;
  lastModifiedDate?: string | null;
};

type StockProductViewChadFormRawValue = FormValueOf<IStockProductViewChad>;

type NewStockProductViewChadFormRawValue = FormValueOf<NewStockProductViewChad>;

type StockProductViewChadFormDefaults = Pick<
  NewStockProductViewChad,
  'id' | 'deliveryDate' | 'expirationDate' | 'createdDate' | 'lastModifiedDate'
>;

type StockProductViewChadFormGroupContent = {
  id: FormControl<StockProductViewChadFormRawValue['id'] | NewStockProductViewChad['id']>;
  quantite: FormControl<StockProductViewChadFormRawValue['quantite']>;
  stockName: FormControl<StockProductViewChadFormRawValue['stockName']>;
  productName: FormControl<StockProductViewChadFormRawValue['productName']>;
  deliveryDate: FormControl<StockProductViewChadFormRawValue['deliveryDate']>;
  expirationDate: FormControl<StockProductViewChadFormRawValue['expirationDate']>;
  createdBy: FormControl<StockProductViewChadFormRawValue['createdBy']>;
  createdDate: FormControl<StockProductViewChadFormRawValue['createdDate']>;
  lastModifiedBy: FormControl<StockProductViewChadFormRawValue['lastModifiedBy']>;
  lastModifiedDate: FormControl<StockProductViewChadFormRawValue['lastModifiedDate']>;
};

export type StockProductViewChadFormGroup = FormGroup<StockProductViewChadFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class StockProductViewChadFormService {
  createStockProductViewChadFormGroup(stockProductView: StockProductViewChadFormGroupInput = { id: null }): StockProductViewChadFormGroup {
    const stockProductViewRawValue = this.convertStockProductViewChadToStockProductViewChadRawValue({
      ...this.getFormDefaults(),
      ...stockProductView,
    });
    return new FormGroup<StockProductViewChadFormGroupContent>({
      id: new FormControl(
        { value: stockProductViewRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      quantite: new FormControl(stockProductViewRawValue.quantite),
      stockName: new FormControl(stockProductViewRawValue.stockName),
      productName: new FormControl(stockProductViewRawValue.productName),
      deliveryDate: new FormControl(stockProductViewRawValue.deliveryDate),
      expirationDate: new FormControl(stockProductViewRawValue.expirationDate),
      createdBy: new FormControl(stockProductViewRawValue.createdBy),
      createdDate: new FormControl(stockProductViewRawValue.createdDate),
      lastModifiedBy: new FormControl(stockProductViewRawValue.lastModifiedBy),
      lastModifiedDate: new FormControl(stockProductViewRawValue.lastModifiedDate),
    });
  }

  getStockProductViewChad(form: StockProductViewChadFormGroup): IStockProductViewChad | NewStockProductViewChad {
    return this.convertStockProductViewChadRawValueToStockProductViewChad(
      form.getRawValue() as StockProductViewChadFormRawValue | NewStockProductViewChadFormRawValue
    );
  }

  resetForm(form: StockProductViewChadFormGroup, stockProductView: StockProductViewChadFormGroupInput): void {
    const stockProductViewRawValue = this.convertStockProductViewChadToStockProductViewChadRawValue({
      ...this.getFormDefaults(),
      ...stockProductView,
    });
    form.reset(
      {
        ...stockProductViewRawValue,
        id: { value: stockProductViewRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): StockProductViewChadFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      deliveryDate: currentTime,
      expirationDate: currentTime,
      createdDate: currentTime,
      lastModifiedDate: currentTime,
    };
  }

  private convertStockProductViewChadRawValueToStockProductViewChad(
    rawStockProductViewChad: StockProductViewChadFormRawValue | NewStockProductViewChadFormRawValue
  ): IStockProductViewChad | NewStockProductViewChad {
    return {
      ...rawStockProductViewChad,
      deliveryDate: dayjs(rawStockProductViewChad.deliveryDate, DATE_TIME_FORMAT),
      expirationDate: dayjs(rawStockProductViewChad.expirationDate, DATE_TIME_FORMAT),
      createdDate: dayjs(rawStockProductViewChad.createdDate, DATE_TIME_FORMAT),
      lastModifiedDate: dayjs(rawStockProductViewChad.lastModifiedDate, DATE_TIME_FORMAT),
    };
  }

  private convertStockProductViewChadToStockProductViewChadRawValue(
    stockProductView: IStockProductViewChad | (Partial<NewStockProductViewChad> & StockProductViewChadFormDefaults)
  ): StockProductViewChadFormRawValue | PartialWithRequiredKeyOf<NewStockProductViewChadFormRawValue> {
    return {
      ...stockProductView,
      deliveryDate: stockProductView.deliveryDate ? stockProductView.deliveryDate.format(DATE_TIME_FORMAT) : undefined,
      expirationDate: stockProductView.expirationDate ? stockProductView.expirationDate.format(DATE_TIME_FORMAT) : undefined,
      createdDate: stockProductView.createdDate ? stockProductView.createdDate.format(DATE_TIME_FORMAT) : undefined,
      lastModifiedDate: stockProductView.lastModifiedDate ? stockProductView.lastModifiedDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
