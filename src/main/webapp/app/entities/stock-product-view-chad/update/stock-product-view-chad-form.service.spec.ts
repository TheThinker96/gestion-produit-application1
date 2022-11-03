import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../stock-product-view-chad.test-samples';

import { StockProductViewChadFormService } from './stock-product-view-chad-form.service';

describe('StockProductViewChad Form Service', () => {
  let service: StockProductViewChadFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StockProductViewChadFormService);
  });

  describe('Service methods', () => {
    describe('createStockProductViewChadFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createStockProductViewChadFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            quantite: expect.any(Object),
            stockName: expect.any(Object),
            productName: expect.any(Object),
            deliveryDate: expect.any(Object),
            expirationDate: expect.any(Object),
            createdBy: expect.any(Object),
            createdDate: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            lastModifiedDate: expect.any(Object),
          })
        );
      });

      it('passing IStockProductViewChad should create a new form with FormGroup', () => {
        const formGroup = service.createStockProductViewChadFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            quantite: expect.any(Object),
            stockName: expect.any(Object),
            productName: expect.any(Object),
            deliveryDate: expect.any(Object),
            expirationDate: expect.any(Object),
            createdBy: expect.any(Object),
            createdDate: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            lastModifiedDate: expect.any(Object),
          })
        );
      });
    });

    describe('getStockProductViewChad', () => {
      it('should return NewStockProductViewChad for default StockProductViewChad initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createStockProductViewChadFormGroup(sampleWithNewData);

        const stockProductView = service.getStockProductViewChad(formGroup) as any;

        expect(stockProductView).toMatchObject(sampleWithNewData);
      });

      it('should return NewStockProductViewChad for empty StockProductViewChad initial value', () => {
        const formGroup = service.createStockProductViewChadFormGroup();

        const stockProductView = service.getStockProductViewChad(formGroup) as any;

        expect(stockProductView).toMatchObject({});
      });

      it('should return IStockProductViewChad', () => {
        const formGroup = service.createStockProductViewChadFormGroup(sampleWithRequiredData);

        const stockProductView = service.getStockProductViewChad(formGroup) as any;

        expect(stockProductView).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IStockProductViewChad should not enable id FormControl', () => {
        const formGroup = service.createStockProductViewChadFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewStockProductViewChad should disable id FormControl', () => {
        const formGroup = service.createStockProductViewChadFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
