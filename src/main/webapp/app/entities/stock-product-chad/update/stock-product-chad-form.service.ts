import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IStockProductChad, NewStockProductChad } from '../stock-product-chad.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IStockProductChad for edit and NewStockProductChadFormGroupInput for create.
 */
type StockProductChadFormGroupInput = IStockProductChad | PartialWithRequiredKeyOf<NewStockProductChad>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IStockProductChad | NewStockProductChad> = Omit<T, 'deliveryDate' | 'expirationDate'> & {
  deliveryDate?: string | null;
  expirationDate?: string | null;
};

type StockProductChadFormRawValue = FormValueOf<IStockProductChad>;

type NewStockProductChadFormRawValue = FormValueOf<NewStockProductChad>;

type StockProductChadFormDefaults = Pick<NewStockProductChad, 'id' | 'deliveryDate' | 'expirationDate'>;

type StockProductChadFormGroupContent = {
  id: FormControl<StockProductChadFormRawValue['id'] | NewStockProductChad['id']>;
  quantite: FormControl<StockProductChadFormRawValue['quantite']>;
  name: FormControl<StockProductChadFormRawValue['name']>;
  deliveryDate: FormControl<StockProductChadFormRawValue['deliveryDate']>;
  expirationDate: FormControl<StockProductChadFormRawValue['expirationDate']>;
  prixStock: FormControl<StockProductChadFormRawValue['prixStock']>;
  product: FormControl<StockProductChadFormRawValue['product']>;
};

export type StockProductChadFormGroup = FormGroup<StockProductChadFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class StockProductChadFormService {
  createStockProductChadFormGroup(stockProduct: StockProductChadFormGroupInput = { id: null }): StockProductChadFormGroup {
    const stockProductRawValue = this.convertStockProductChadToStockProductChadRawValue({
      ...this.getFormDefaults(),
      ...stockProduct,
    });
    return new FormGroup<StockProductChadFormGroupContent>({
      id: new FormControl(
        { value: stockProductRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      quantite: new FormControl(stockProductRawValue.quantite),
      name: new FormControl(stockProductRawValue.name, {
        validators: [Validators.required, Validators.minLength(3)],
      }),
      deliveryDate: new FormControl(stockProductRawValue.deliveryDate, {
        validators: [Validators.required],
      }),
      expirationDate: new FormControl(stockProductRawValue.expirationDate, {
        validators: [Validators.required],
      }),
      prixStock: new FormControl(stockProductRawValue.prixStock, {
        validators: [Validators.required],
      }),
      product: new FormControl(stockProductRawValue.product),
    });
  }

  getStockProductChad(form: StockProductChadFormGroup): IStockProductChad | NewStockProductChad {
    return this.convertStockProductChadRawValueToStockProductChad(
      form.getRawValue() as StockProductChadFormRawValue | NewStockProductChadFormRawValue
    );
  }

  resetForm(form: StockProductChadFormGroup, stockProduct: StockProductChadFormGroupInput): void {
    const stockProductRawValue = this.convertStockProductChadToStockProductChadRawValue({ ...this.getFormDefaults(), ...stockProduct });
    form.reset(
      {
        ...stockProductRawValue,
        id: { value: stockProductRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): StockProductChadFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      deliveryDate: currentTime,
      expirationDate: currentTime,
    };
  }

  private convertStockProductChadRawValueToStockProductChad(
    rawStockProductChad: StockProductChadFormRawValue | NewStockProductChadFormRawValue
  ): IStockProductChad | NewStockProductChad {
    return {
      ...rawStockProductChad,
      deliveryDate: dayjs(rawStockProductChad.deliveryDate, DATE_TIME_FORMAT),
      expirationDate: dayjs(rawStockProductChad.expirationDate, DATE_TIME_FORMAT),
    };
  }

  private convertStockProductChadToStockProductChadRawValue(
    stockProduct: IStockProductChad | (Partial<NewStockProductChad> & StockProductChadFormDefaults)
  ): StockProductChadFormRawValue | PartialWithRequiredKeyOf<NewStockProductChadFormRawValue> {
    return {
      ...stockProduct,
      deliveryDate: stockProduct.deliveryDate ? stockProduct.deliveryDate.format(DATE_TIME_FORMAT) : undefined,
      expirationDate: stockProduct.expirationDate ? stockProduct.expirationDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
