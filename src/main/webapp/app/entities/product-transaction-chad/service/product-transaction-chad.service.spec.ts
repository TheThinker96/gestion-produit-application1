import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IProductTransactionChad } from '../product-transaction-chad.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../product-transaction-chad.test-samples';

import { ProductTransactionChadService } from './product-transaction-chad.service';

const requireRestSample: IProductTransactionChad = {
  ...sampleWithRequiredData,
};

describe('ProductTransactionChad Service', () => {
  let service: ProductTransactionChadService;
  let httpMock: HttpTestingController;
  let expectedResult: IProductTransactionChad | IProductTransactionChad[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ProductTransactionChadService);
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

    it('should create a ProductTransactionChad', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const productTransaction = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(productTransaction).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ProductTransactionChad', () => {
      const productTransaction = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(productTransaction).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ProductTransactionChad', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ProductTransactionChad', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ProductTransactionChad', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addProductTransactionChadToCollectionIfMissing', () => {
      it('should add a ProductTransactionChad to an empty array', () => {
        const productTransaction: IProductTransactionChad = sampleWithRequiredData;
        expectedResult = service.addProductTransactionChadToCollectionIfMissing([], productTransaction);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(productTransaction);
      });

      it('should not add a ProductTransactionChad to an array that contains it', () => {
        const productTransaction: IProductTransactionChad = sampleWithRequiredData;
        const productTransactionCollection: IProductTransactionChad[] = [
          {
            ...productTransaction,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addProductTransactionChadToCollectionIfMissing(productTransactionCollection, productTransaction);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ProductTransactionChad to an array that doesn't contain it", () => {
        const productTransaction: IProductTransactionChad = sampleWithRequiredData;
        const productTransactionCollection: IProductTransactionChad[] = [sampleWithPartialData];
        expectedResult = service.addProductTransactionChadToCollectionIfMissing(productTransactionCollection, productTransaction);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(productTransaction);
      });

      it('should add only unique ProductTransactionChad to an array', () => {
        const productTransactionArray: IProductTransactionChad[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const productTransactionCollection: IProductTransactionChad[] = [sampleWithRequiredData];
        expectedResult = service.addProductTransactionChadToCollectionIfMissing(productTransactionCollection, ...productTransactionArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const productTransaction: IProductTransactionChad = sampleWithRequiredData;
        const productTransaction2: IProductTransactionChad = sampleWithPartialData;
        expectedResult = service.addProductTransactionChadToCollectionIfMissing([], productTransaction, productTransaction2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(productTransaction);
        expect(expectedResult).toContain(productTransaction2);
      });

      it('should accept null and undefined values', () => {
        const productTransaction: IProductTransactionChad = sampleWithRequiredData;
        expectedResult = service.addProductTransactionChadToCollectionIfMissing([], null, productTransaction, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(productTransaction);
      });

      it('should return initial array if no ProductTransactionChad is added', () => {
        const productTransactionCollection: IProductTransactionChad[] = [sampleWithRequiredData];
        expectedResult = service.addProductTransactionChadToCollectionIfMissing(productTransactionCollection, undefined, null);
        expect(expectedResult).toEqual(productTransactionCollection);
      });
    });

    describe('compareProductTransactionChad', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareProductTransactionChad(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareProductTransactionChad(entity1, entity2);
        const compareResult2 = service.compareProductTransactionChad(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareProductTransactionChad(entity1, entity2);
        const compareResult2 = service.compareProductTransactionChad(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareProductTransactionChad(entity1, entity2);
        const compareResult2 = service.compareProductTransactionChad(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
