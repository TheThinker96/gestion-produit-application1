import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../product-sale-chad.test-samples';

import { ProductSaleChadFormService } from './product-sale-chad-form.service';

describe('ProductSaleChad Form Service', () => {
  let service: ProductSaleChadFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProductSaleChadFormService);
  });

  describe('Service methods', () => {
    describe('createProductSaleChadFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createProductSaleChadFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            quantite: expect.any(Object),
            prixTotal: expect.any(Object),
            statut: expect.any(Object),
            product: expect.any(Object),
            stockProduct: expect.any(Object),
            userSaleAccount: expect.any(Object),
          })
        );
      });

      it('passing IProductSaleChad should create a new form with FormGroup', () => {
        const formGroup = service.createProductSaleChadFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            quantite: expect.any(Object),
            prixTotal: expect.any(Object),
            statut: expect.any(Object),
            product: expect.any(Object),
            stockProduct: expect.any(Object),
            userSaleAccount: expect.any(Object),
          })
        );
      });
    });

    describe('getProductSaleChad', () => {
      it('should return NewProductSaleChad for default ProductSaleChad initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createProductSaleChadFormGroup(sampleWithNewData);

        const productSale = service.getProductSaleChad(formGroup) as any;

        expect(productSale).toMatchObject(sampleWithNewData);
      });

      it('should return NewProductSaleChad for empty ProductSaleChad initial value', () => {
        const formGroup = service.createProductSaleChadFormGroup();

        const productSale = service.getProductSaleChad(formGroup) as any;

        expect(productSale).toMatchObject({});
      });

      it('should return IProductSaleChad', () => {
        const formGroup = service.createProductSaleChadFormGroup(sampleWithRequiredData);

        const productSale = service.getProductSaleChad(formGroup) as any;

        expect(productSale).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IProductSaleChad should not enable id FormControl', () => {
        const formGroup = service.createProductSaleChadFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewProductSaleChad should disable id FormControl', () => {
        const formGroup = service.createProductSaleChadFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
