import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../product-chad.test-samples';

import { ProductChadFormService } from './product-chad-form.service';

describe('ProductChad Form Service', () => {
  let service: ProductChadFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProductChadFormService);
  });

  describe('Service methods', () => {
    describe('createProductChadFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createProductChadFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            prix: expect.any(Object),
          })
        );
      });

      it('passing IProductChad should create a new form with FormGroup', () => {
        const formGroup = service.createProductChadFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            prix: expect.any(Object),
          })
        );
      });
    });

    describe('getProductChad', () => {
      it('should return NewProductChad for default ProductChad initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createProductChadFormGroup(sampleWithNewData);

        const product = service.getProductChad(formGroup) as any;

        expect(product).toMatchObject(sampleWithNewData);
      });

      it('should return NewProductChad for empty ProductChad initial value', () => {
        const formGroup = service.createProductChadFormGroup();

        const product = service.getProductChad(formGroup) as any;

        expect(product).toMatchObject({});
      });

      it('should return IProductChad', () => {
        const formGroup = service.createProductChadFormGroup(sampleWithRequiredData);

        const product = service.getProductChad(formGroup) as any;

        expect(product).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IProductChad should not enable id FormControl', () => {
        const formGroup = service.createProductChadFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewProductChad should disable id FormControl', () => {
        const formGroup = service.createProductChadFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
