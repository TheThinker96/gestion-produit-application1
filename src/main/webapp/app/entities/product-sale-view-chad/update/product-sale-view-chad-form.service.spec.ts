import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../product-sale-view-chad.test-samples';

import { ProductSaleViewChadFormService } from './product-sale-view-chad-form.service';

describe('ProductSaleViewChad Form Service', () => {
  let service: ProductSaleViewChadFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProductSaleViewChadFormService);
  });

  describe('Service methods', () => {
    describe('createProductSaleViewChadFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createProductSaleViewChadFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            productName: expect.any(Object),
            stockName: expect.any(Object),
            quantite: expect.any(Object),
            productPrice: expect.any(Object),
            total: expect.any(Object),
            createdBy: expect.any(Object),
            createdDate: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            lastModifiedDate: expect.any(Object),
          })
        );
      });

      it('passing IProductSaleViewChad should create a new form with FormGroup', () => {
        const formGroup = service.createProductSaleViewChadFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            productName: expect.any(Object),
            stockName: expect.any(Object),
            quantite: expect.any(Object),
            productPrice: expect.any(Object),
            total: expect.any(Object),
            createdBy: expect.any(Object),
            createdDate: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            lastModifiedDate: expect.any(Object),
          })
        );
      });
    });

    describe('getProductSaleViewChad', () => {
      it('should return NewProductSaleViewChad for default ProductSaleViewChad initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createProductSaleViewChadFormGroup(sampleWithNewData);

        const productSaleView = service.getProductSaleViewChad(formGroup) as any;

        expect(productSaleView).toMatchObject(sampleWithNewData);
      });

      it('should return NewProductSaleViewChad for empty ProductSaleViewChad initial value', () => {
        const formGroup = service.createProductSaleViewChadFormGroup();

        const productSaleView = service.getProductSaleViewChad(formGroup) as any;

        expect(productSaleView).toMatchObject({});
      });

      it('should return IProductSaleViewChad', () => {
        const formGroup = service.createProductSaleViewChadFormGroup(sampleWithRequiredData);

        const productSaleView = service.getProductSaleViewChad(formGroup) as any;

        expect(productSaleView).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IProductSaleViewChad should not enable id FormControl', () => {
        const formGroup = service.createProductSaleViewChadFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewProductSaleViewChad should disable id FormControl', () => {
        const formGroup = service.createProductSaleViewChadFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
