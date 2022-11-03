import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../product-transaction-chad.test-samples';

import { ProductTransactionChadFormService } from './product-transaction-chad-form.service';

describe('ProductTransactionChad Form Service', () => {
  let service: ProductTransactionChadFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProductTransactionChadFormService);
  });

  describe('Service methods', () => {
    describe('createProductTransactionChadFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createProductTransactionChadFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            transactionType: expect.any(Object),
            description: expect.any(Object),
            stockProduct: expect.any(Object),
            product: expect.any(Object),
          })
        );
      });

      it('passing IProductTransactionChad should create a new form with FormGroup', () => {
        const formGroup = service.createProductTransactionChadFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            transactionType: expect.any(Object),
            description: expect.any(Object),
            stockProduct: expect.any(Object),
            product: expect.any(Object),
          })
        );
      });
    });

    describe('getProductTransactionChad', () => {
      it('should return NewProductTransactionChad for default ProductTransactionChad initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createProductTransactionChadFormGroup(sampleWithNewData);

        const productTransaction = service.getProductTransactionChad(formGroup) as any;

        expect(productTransaction).toMatchObject(sampleWithNewData);
      });

      it('should return NewProductTransactionChad for empty ProductTransactionChad initial value', () => {
        const formGroup = service.createProductTransactionChadFormGroup();

        const productTransaction = service.getProductTransactionChad(formGroup) as any;

        expect(productTransaction).toMatchObject({});
      });

      it('should return IProductTransactionChad', () => {
        const formGroup = service.createProductTransactionChadFormGroup(sampleWithRequiredData);

        const productTransaction = service.getProductTransactionChad(formGroup) as any;

        expect(productTransaction).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IProductTransactionChad should not enable id FormControl', () => {
        const formGroup = service.createProductTransactionChadFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewProductTransactionChad should disable id FormControl', () => {
        const formGroup = service.createProductTransactionChadFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
