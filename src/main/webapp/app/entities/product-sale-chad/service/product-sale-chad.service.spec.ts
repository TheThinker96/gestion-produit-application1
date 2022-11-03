import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IProductSaleChad } from '../product-sale-chad.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../product-sale-chad.test-samples';

import { ProductSaleChadService } from './product-sale-chad.service';

const requireRestSample: IProductSaleChad = {
  ...sampleWithRequiredData,
};

describe('ProductSaleChad Service', () => {
  let service: ProductSaleChadService;
  let httpMock: HttpTestingController;
  let expectedResult: IProductSaleChad | IProductSaleChad[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ProductSaleChadService);
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

    it('should create a ProductSaleChad', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const productSale = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(productSale).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ProductSaleChad', () => {
      const productSale = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(productSale).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ProductSaleChad', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ProductSaleChad', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ProductSaleChad', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addProductSaleChadToCollectionIfMissing', () => {
      it('should add a ProductSaleChad to an empty array', () => {
        const productSale: IProductSaleChad = sampleWithRequiredData;
        expectedResult = service.addProductSaleChadToCollectionIfMissing([], productSale);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(productSale);
      });

      it('should not add a ProductSaleChad to an array that contains it', () => {
        const productSale: IProductSaleChad = sampleWithRequiredData;
        const productSaleCollection: IProductSaleChad[] = [
          {
            ...productSale,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addProductSaleChadToCollectionIfMissing(productSaleCollection, productSale);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ProductSaleChad to an array that doesn't contain it", () => {
        const productSale: IProductSaleChad = sampleWithRequiredData;
        const productSaleCollection: IProductSaleChad[] = [sampleWithPartialData];
        expectedResult = service.addProductSaleChadToCollectionIfMissing(productSaleCollection, productSale);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(productSale);
      });

      it('should add only unique ProductSaleChad to an array', () => {
        const productSaleArray: IProductSaleChad[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const productSaleCollection: IProductSaleChad[] = [sampleWithRequiredData];
        expectedResult = service.addProductSaleChadToCollectionIfMissing(productSaleCollection, ...productSaleArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const productSale: IProductSaleChad = sampleWithRequiredData;
        const productSale2: IProductSaleChad = sampleWithPartialData;
        expectedResult = service.addProductSaleChadToCollectionIfMissing([], productSale, productSale2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(productSale);
        expect(expectedResult).toContain(productSale2);
      });

      it('should accept null and undefined values', () => {
        const productSale: IProductSaleChad = sampleWithRequiredData;
        expectedResult = service.addProductSaleChadToCollectionIfMissing([], null, productSale, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(productSale);
      });

      it('should return initial array if no ProductSaleChad is added', () => {
        const productSaleCollection: IProductSaleChad[] = [sampleWithRequiredData];
        expectedResult = service.addProductSaleChadToCollectionIfMissing(productSaleCollection, undefined, null);
        expect(expectedResult).toEqual(productSaleCollection);
      });
    });

    describe('compareProductSaleChad', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareProductSaleChad(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareProductSaleChad(entity1, entity2);
        const compareResult2 = service.compareProductSaleChad(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareProductSaleChad(entity1, entity2);
        const compareResult2 = service.compareProductSaleChad(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareProductSaleChad(entity1, entity2);
        const compareResult2 = service.compareProductSaleChad(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
