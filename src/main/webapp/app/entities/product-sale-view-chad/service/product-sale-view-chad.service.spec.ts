import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IProductSaleViewChad } from '../product-sale-view-chad.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../product-sale-view-chad.test-samples';

import { ProductSaleViewChadService, RestProductSaleViewChad } from './product-sale-view-chad.service';

const requireRestSample: RestProductSaleViewChad = {
  ...sampleWithRequiredData,
  createdDate: sampleWithRequiredData.createdDate?.toJSON(),
  lastModifiedDate: sampleWithRequiredData.lastModifiedDate?.toJSON(),
};

describe('ProductSaleViewChad Service', () => {
  let service: ProductSaleViewChadService;
  let httpMock: HttpTestingController;
  let expectedResult: IProductSaleViewChad | IProductSaleViewChad[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ProductSaleViewChadService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a ProductSaleViewChad', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const productSaleView = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(productSaleView).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ProductSaleViewChad', () => {
      const productSaleView = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(productSaleView).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ProductSaleViewChad', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ProductSaleViewChad', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ProductSaleViewChad', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addProductSaleViewChadToCollectionIfMissing', () => {
      it('should add a ProductSaleViewChad to an empty array', () => {
        const productSaleView: IProductSaleViewChad = sampleWithRequiredData;
        expectedResult = service.addProductSaleViewChadToCollectionIfMissing([], productSaleView);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(productSaleView);
      });

      it('should not add a ProductSaleViewChad to an array that contains it', () => {
        const productSaleView: IProductSaleViewChad = sampleWithRequiredData;
        const productSaleViewCollection: IProductSaleViewChad[] = [
          {
            ...productSaleView,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addProductSaleViewChadToCollectionIfMissing(productSaleViewCollection, productSaleView);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ProductSaleViewChad to an array that doesn't contain it", () => {
        const productSaleView: IProductSaleViewChad = sampleWithRequiredData;
        const productSaleViewCollection: IProductSaleViewChad[] = [sampleWithPartialData];
        expectedResult = service.addProductSaleViewChadToCollectionIfMissing(productSaleViewCollection, productSaleView);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(productSaleView);
      });

      it('should add only unique ProductSaleViewChad to an array', () => {
        const productSaleViewArray: IProductSaleViewChad[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const productSaleViewCollection: IProductSaleViewChad[] = [sampleWithRequiredData];
        expectedResult = service.addProductSaleViewChadToCollectionIfMissing(productSaleViewCollection, ...productSaleViewArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const productSaleView: IProductSaleViewChad = sampleWithRequiredData;
        const productSaleView2: IProductSaleViewChad = sampleWithPartialData;
        expectedResult = service.addProductSaleViewChadToCollectionIfMissing([], productSaleView, productSaleView2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(productSaleView);
        expect(expectedResult).toContain(productSaleView2);
      });

      it('should accept null and undefined values', () => {
        const productSaleView: IProductSaleViewChad = sampleWithRequiredData;
        expectedResult = service.addProductSaleViewChadToCollectionIfMissing([], null, productSaleView, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(productSaleView);
      });

      it('should return initial array if no ProductSaleViewChad is added', () => {
        const productSaleViewCollection: IProductSaleViewChad[] = [sampleWithRequiredData];
        expectedResult = service.addProductSaleViewChadToCollectionIfMissing(productSaleViewCollection, undefined, null);
        expect(expectedResult).toEqual(productSaleViewCollection);
      });
    });

    describe('compareProductSaleViewChad', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareProductSaleViewChad(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareProductSaleViewChad(entity1, entity2);
        const compareResult2 = service.compareProductSaleViewChad(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareProductSaleViewChad(entity1, entity2);
        const compareResult2 = service.compareProductSaleViewChad(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareProductSaleViewChad(entity1, entity2);
        const compareResult2 = service.compareProductSaleViewChad(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
