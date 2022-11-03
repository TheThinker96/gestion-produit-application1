import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IStockProductChad } from '../stock-product-chad.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../stock-product-chad.test-samples';

import { StockProductChadService, RestStockProductChad } from './stock-product-chad.service';

const requireRestSample: RestStockProductChad = {
  ...sampleWithRequiredData,
  deliveryDate: sampleWithRequiredData.deliveryDate?.toJSON(),
  expirationDate: sampleWithRequiredData.expirationDate?.toJSON(),
};

describe('StockProductChad Service', () => {
  let service: StockProductChadService;
  let httpMock: HttpTestingController;
  let expectedResult: IStockProductChad | IStockProductChad[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(StockProductChadService);
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

    it('should create a StockProductChad', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const stockProduct = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(stockProduct).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a StockProductChad', () => {
      const stockProduct = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(stockProduct).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a StockProductChad', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of StockProductChad', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a StockProductChad', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addStockProductChadToCollectionIfMissing', () => {
      it('should add a StockProductChad to an empty array', () => {
        const stockProduct: IStockProductChad = sampleWithRequiredData;
        expectedResult = service.addStockProductChadToCollectionIfMissing([], stockProduct);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(stockProduct);
      });

      it('should not add a StockProductChad to an array that contains it', () => {
        const stockProduct: IStockProductChad = sampleWithRequiredData;
        const stockProductCollection: IStockProductChad[] = [
          {
            ...stockProduct,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addStockProductChadToCollectionIfMissing(stockProductCollection, stockProduct);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a StockProductChad to an array that doesn't contain it", () => {
        const stockProduct: IStockProductChad = sampleWithRequiredData;
        const stockProductCollection: IStockProductChad[] = [sampleWithPartialData];
        expectedResult = service.addStockProductChadToCollectionIfMissing(stockProductCollection, stockProduct);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(stockProduct);
      });

      it('should add only unique StockProductChad to an array', () => {
        const stockProductArray: IStockProductChad[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const stockProductCollection: IStockProductChad[] = [sampleWithRequiredData];
        expectedResult = service.addStockProductChadToCollectionIfMissing(stockProductCollection, ...stockProductArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const stockProduct: IStockProductChad = sampleWithRequiredData;
        const stockProduct2: IStockProductChad = sampleWithPartialData;
        expectedResult = service.addStockProductChadToCollectionIfMissing([], stockProduct, stockProduct2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(stockProduct);
        expect(expectedResult).toContain(stockProduct2);
      });

      it('should accept null and undefined values', () => {
        const stockProduct: IStockProductChad = sampleWithRequiredData;
        expectedResult = service.addStockProductChadToCollectionIfMissing([], null, stockProduct, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(stockProduct);
      });

      it('should return initial array if no StockProductChad is added', () => {
        const stockProductCollection: IStockProductChad[] = [sampleWithRequiredData];
        expectedResult = service.addStockProductChadToCollectionIfMissing(stockProductCollection, undefined, null);
        expect(expectedResult).toEqual(stockProductCollection);
      });
    });

    describe('compareStockProductChad', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareStockProductChad(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareStockProductChad(entity1, entity2);
        const compareResult2 = service.compareStockProductChad(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareStockProductChad(entity1, entity2);
        const compareResult2 = service.compareStockProductChad(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareStockProductChad(entity1, entity2);
        const compareResult2 = service.compareStockProductChad(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
