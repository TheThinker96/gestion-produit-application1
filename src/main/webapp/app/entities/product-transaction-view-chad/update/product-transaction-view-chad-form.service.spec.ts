import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../product-transaction-view-chad.test-samples';

import { ProductTransactionViewChadFormService } from './product-transaction-view-chad-form.service';

describe('ProductTransactionViewChad Form Service', () => {
  let service: ProductTransactionViewChadFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProductTransactionViewChadFormService);
  });

  describe('Service methods', () => {
    describe('createProductTransactionViewChadFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createProductTransactionViewChadFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            productName: expect.any(Object),
            stockName: expect.any(Object),
            quantite: expect.any(Object),
            transactionType: expect.any(Object),
            description: expect.any(Object),
            createdBy: expect.any(Object),
            createdDate: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            lastModifiedDate: expect.any(Object),
          })
        );
      });

      it('passing IProductTransactionViewChad should create a new form with FormGroup', () => {
        const formGroup = service.createProductTransactionViewChadFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            productName: expect.any(Object),
            stockName: expect.any(Object),
            quantite: expect.any(Object),
            transactionType: expect.any(Object),
            description: expect.any(Object),
            createdBy: expect.any(Object),
            createdDate: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            lastModifiedDate: expect.any(Object),
          })
        );
      });
    });

    describe('getProductTransactionViewChad', () => {
      it('should return NewProductTransactionViewChad for default ProductTransactionViewChad initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createProductTransactionViewChadFormGroup(sampleWithNewData);

        const productTransactionView = service.getProductTransactionViewChad(formGroup) as any;

        expect(productTransactionView).toMatchObject(sampleWithNewData);
      });

      it('should return NewProductTransactionViewChad for empty ProductTransactionViewChad initial value', () => {
        const formGroup = service.createProductTransactionViewChadFormGroup();

        const productTransactionView = service.getProductTransactionViewChad(formGroup) as any;

        expect(productTransactionView).toMatchObject({});
      });

      it('should return IProductTransactionViewChad', () => {
        const formGroup = service.createProductTransactionViewChadFormGroup(sampleWithRequiredData);

        const productTransactionView = service.getProductTransactionViewChad(formGroup) as any;

        expect(productTransactionView).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IProductTransactionViewChad should not enable id FormControl', () => {
        const formGroup = service.createProductTransactionViewChadFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewProductTransactionViewChad should disable id FormControl', () => {
        const formGroup = service.createProductTransactionViewChadFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
