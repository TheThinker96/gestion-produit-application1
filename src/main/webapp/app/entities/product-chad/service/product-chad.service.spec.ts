import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IProductChad } from '../product-chad.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../product-chad.test-samples';

import { ProductChadService } from './product-chad.service';

const requireRestSample: IProductChad = {
  ...sampleWithRequiredData,
};

describe('ProductChad Service', () => {
  let service: ProductChadService;
  let httpMock: HttpTestingController;
  let expectedResult: IProductChad | IProductChad[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ProductChadService);
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

    it('should create a ProductChad', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const product = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(product).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ProductChad', () => {
      const product = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(product).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ProductChad', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ProductChad', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ProductChad', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addProductChadToCollectionIfMissing', () => {
      it('should add a ProductChad to an empty array', () => {
        const product: IProductChad = sampleWithRequiredData;
        expectedResult = service.addProductChadToCollectionIfMissing([], product);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(product);
      });

      it('should not add a ProductChad to an array that contains it', () => {
        const product: IProductChad = sampleWithRequiredData;
        const productCollection: IProductChad[] = [
          {
            ...product,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addProductChadToCollectionIfMissing(productCollection, product);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ProductChad to an array that doesn't contain it", () => {
        const product: IProductChad = sampleWithRequiredData;
        const productCollection: IProductChad[] = [sampleWithPartialData];
        expectedResult = service.addProductChadToCollectionIfMissing(productCollection, product);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(product);
      });

      it('should add only unique ProductChad to an array', () => {
        const productArray: IProductChad[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const productCollection: IProductChad[] = [sampleWithRequiredData];
        expectedResult = service.addProductChadToCollectionIfMissing(productCollection, ...productArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const product: IProductChad = sampleWithRequiredData;
        const product2: IProductChad = sampleWithPartialData;
        expectedResult = service.addProductChadToCollectionIfMissing([], product, product2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(product);
        expect(expectedResult).toContain(product2);
      });

      it('should accept null and undefined values', () => {
        const product: IProductChad = sampleWithRequiredData;
        expectedResult = service.addProductChadToCollectionIfMissing([], null, product, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(product);
      });

      it('should return initial array if no ProductChad is added', () => {
        const productCollection: IProductChad[] = [sampleWithRequiredData];
        expectedResult = service.addProductChadToCollectionIfMissing(productCollection, undefined, null);
        expect(expectedResult).toEqual(productCollection);
      });
    });

    describe('compareProductChad', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareProductChad(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareProductChad(entity1, entity2);
        const compareResult2 = service.compareProductChad(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareProductChad(entity1, entity2);
        const compareResult2 = service.compareProductChad(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareProductChad(entity1, entity2);
        const compareResult2 = service.compareProductChad(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
