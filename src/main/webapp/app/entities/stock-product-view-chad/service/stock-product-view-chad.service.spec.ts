import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IStockProductViewChad } from '../stock-product-view-chad.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../stock-product-view-chad.test-samples';

import { StockProductViewChadService, RestStockProductViewChad } from './stock-product-view-chad.service';

const requireRestSample: RestStockProductViewChad = {
  ...sampleWithRequiredData,
  deliveryDate: sampleWithRequiredData.deliveryDate?.toJSON(),
  expirationDate: sampleWithRequiredData.expirationDate?.toJSON(),
  createdDate: sampleWithRequiredData.createdDate?.toJSON(),
  lastModifiedDate: sampleWithRequiredData.lastModifiedDate?.toJSON(),
};

describe('StockProductViewChad Service', () => {
  let service: StockProductViewChadService;
  let httpMock: HttpTestingController;
  let expectedResult: IStockProductViewChad | IStockProductViewChad[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(StockProductViewChadService);
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

    it('should create a StockProductViewChad', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const stockProductView = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(stockProductView).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a StockProductViewChad', () => {
      const stockProductView = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(stockProductView).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a StockProductViewChad', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of StockProductViewChad', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a StockProductViewChad', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addStockProductViewChadToCollectionIfMissing', () => {
      it('should add a StockProductViewChad to an empty array', () => {
        const stockProductView: IStockProductViewChad = sampleWithRequiredData;
        expectedResult = service.addStockProductViewChadToCollectionIfMissing([], stockProductView);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(stockProductView);
      });

      it('should not add a StockProductViewChad to an array that contains it', () => {
        const stockProductView: IStockProductViewChad = sampleWithRequiredData;
        const stockProductViewCollection: IStockProductViewChad[] = [
          {
            ...stockProductView,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addStockProductViewChadToCollectionIfMissing(stockProductViewCollection, stockProductView);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a StockProductViewChad to an array that doesn't contain it", () => {
        const stockProductView: IStockProductViewChad = sampleWithRequiredData;
        const stockProductViewCollection: IStockProductViewChad[] = [sampleWithPartialData];
        expectedResult = service.addStockProductViewChadToCollectionIfMissing(stockProductViewCollection, stockProductView);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(stockProductView);
      });

      it('should add only unique StockProductViewChad to an array', () => {
        const stockProductViewArray: IStockProductViewChad[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const stockProductViewCollection: IStockProductViewChad[] = [sampleWithRequiredData];
        expectedResult = service.addStockProductViewChadToCollectionIfMissing(stockProductViewCollection, ...stockProductViewArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const stockProductView: IStockProductViewChad = sampleWithRequiredData;
        const stockProductView2: IStockProductViewChad = sampleWithPartialData;
        expectedResult = service.addStockProductViewChadToCollectionIfMissing([], stockProductView, stockProductView2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(stockProductView);
        expect(expectedResult).toContain(stockProductView2);
      });

      it('should accept null and undefined values', () => {
        const stockProductView: IStockProductViewChad = sampleWithRequiredData;
        expectedResult = service.addStockProductViewChadToCollectionIfMissing([], null, stockProductView, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(stockProductView);
      });

      it('should return initial array if no StockProductViewChad is added', () => {
        const stockProductViewCollection: IStockProductViewChad[] = [sampleWithRequiredData];
        expectedResult = service.addStockProductViewChadToCollectionIfMissing(stockProductViewCollection, undefined, null);
        expect(expectedResult).toEqual(stockProductViewCollection);
      });
    });

    describe('compareStockProductViewChad', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareStockProductViewChad(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareStockProductViewChad(entity1, entity2);
        const compareResult2 = service.compareStockProductViewChad(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareStockProductViewChad(entity1, entity2);
        const compareResult2 = service.compareStockProductViewChad(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareStockProductViewChad(entity1, entity2);
        const compareResult2 = service.compareStockProductViewChad(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
