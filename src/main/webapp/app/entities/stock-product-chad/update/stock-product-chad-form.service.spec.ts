import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../stock-product-chad.test-samples';

import { StockProductChadFormService } from './stock-product-chad-form.service';

describe('StockProductChad Form Service', () => {
  let service: StockProductChadFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StockProductChadFormService);
  });

  describe('Service methods', () => {
    describe('createStockProductChadFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createStockProductChadFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            quantite: expect.any(Object),
            name: expect.any(Object),
            deliveryDate: expect.any(Object),
            expirationDate: expect.any(Object),
            prixStock: expect.any(Object),
            product: expect.any(Object),
          })
        );
      });

      it('passing IStockProductChad should create a new form with FormGroup', () => {
        const formGroup = service.createStockProductChadFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            quantite: expect.any(Object),
            name: expect.any(Object),
            deliveryDate: expect.any(Object),
            expirationDate: expect.any(Object),
            prixStock: expect.any(Object),
            product: expect.any(Object),
          })
        );
      });
    });

    describe('getStockProductChad', () => {
      it('should return NewStockProductChad for default StockProductChad initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createStockProductChadFormGroup(sampleWithNewData);

        const stockProduct = service.getStockProductChad(formGroup) as any;

        expect(stockProduct).toMatchObject(sampleWithNewData);
      });

      it('should return NewStockProductChad for empty StockProductChad initial value', () => {
        const formGroup = service.createStockProductChadFormGroup();

        const stockProduct = service.getStockProductChad(formGroup) as any;

        expect(stockProduct).toMatchObject({});
      });

      it('should return IStockProductChad', () => {
        const formGroup = service.createStockProductChadFormGroup(sampleWithRequiredData);

        const stockProduct = service.getStockProductChad(formGroup) as any;

        expect(stockProduct).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IStockProductChad should not enable id FormControl', () => {
        const formGroup = service.createStockProductChadFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewStockProductChad should disable id FormControl', () => {
        const formGroup = service.createStockProductChadFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
