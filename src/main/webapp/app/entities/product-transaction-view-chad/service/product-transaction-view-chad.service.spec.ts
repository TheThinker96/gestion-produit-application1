import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IProductTransactionViewChad } from '../product-transaction-view-chad.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../product-transaction-view-chad.test-samples';

import { ProductTransactionViewChadService, RestProductTransactionViewChad } from './product-transaction-view-chad.service';

const requireRestSample: RestProductTransactionViewChad = {
  ...sampleWithRequiredData,
  createdDate: sampleWithRequiredData.createdDate?.toJSON(),
  lastModifiedDate: sampleWithRequiredData.lastModifiedDate?.toJSON(),
};

describe('ProductTransactionViewChad Service', () => {
  let service: ProductTransactionViewChadService;
  let httpMock: HttpTestingController;
  let expectedResult: IProductTransactionViewChad | IProductTransactionViewChad[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ProductTransactionViewChadService);
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

    it('should create a ProductTransactionViewChad', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const productTransactionView = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(productTransactionView).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ProductTransactionViewChad', () => {
      const productTransactionView = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(productTransactionView).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ProductTransactionViewChad', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ProductTransactionViewChad', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ProductTransactionViewChad', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addProductTransactionViewChadToCollectionIfMissing', () => {
      it('should add a ProductTransactionViewChad to an empty array', () => {
        const productTransactionView: IProductTransactionViewChad = sampleWithRequiredData;
        expectedResult = service.addProductTransactionViewChadToCollectionIfMissing([], productTransactionView);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(productTransactionView);
      });

      it('should not add a ProductTransactionViewChad to an array that contains it', () => {
        const productTransactionView: IProductTransactionViewChad = sampleWithRequiredData;
        const productTransactionViewCollection: IProductTransactionViewChad[] = [
          {
            ...productTransactionView,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addProductTransactionViewChadToCollectionIfMissing(
          productTransactionViewCollection,
          productTransactionView
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ProductTransactionViewChad to an array that doesn't contain it", () => {
        const productTransactionView: IProductTransactionViewChad = sampleWithRequiredData;
        const productTransactionViewCollection: IProductTransactionViewChad[] = [sampleWithPartialData];
        expectedResult = service.addProductTransactionViewChadToCollectionIfMissing(
          productTransactionViewCollection,
          productTransactionView
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(productTransactionView);
      });

      it('should add only unique ProductTransactionViewChad to an array', () => {
        const productTransactionViewArray: IProductTransactionViewChad[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const productTransactionViewCollection: IProductTransactionViewChad[] = [sampleWithRequiredData];
        expectedResult = service.addProductTransactionViewChadToCollectionIfMissing(
          productTransactionViewCollection,
          ...productTransactionViewArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const productTransactionView: IProductTransactionViewChad = sampleWithRequiredData;
        const productTransactionView2: IProductTransactionViewChad = sampleWithPartialData;
        expectedResult = service.addProductTransactionViewChadToCollectionIfMissing([], productTransactionView, productTransactionView2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(productTransactionView);
        expect(expectedResult).toContain(productTransactionView2);
      });

      it('should accept null and undefined values', () => {
        const productTransactionView: IProductTransactionViewChad = sampleWithRequiredData;
        expectedResult = service.addProductTransactionViewChadToCollectionIfMissing([], null, productTransactionView, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(productTransactionView);
      });

      it('should return initial array if no ProductTransactionViewChad is added', () => {
        const productTransactionViewCollection: IProductTransactionViewChad[] = [sampleWithRequiredData];
        expectedResult = service.addProductTransactionViewChadToCollectionIfMissing(productTransactionViewCollection, undefined, null);
        expect(expectedResult).toEqual(productTransactionViewCollection);
      });
    });

    describe('compareProductTransactionViewChad', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareProductTransactionViewChad(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareProductTransactionViewChad(entity1, entity2);
        const compareResult2 = service.compareProductTransactionViewChad(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareProductTransactionViewChad(entity1, entity2);
        const compareResult2 = service.compareProductTransactionViewChad(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareProductTransactionViewChad(entity1, entity2);
        const compareResult2 = service.compareProductTransactionViewChad(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
