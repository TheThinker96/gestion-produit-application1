import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IUserCashBackChad } from '../user-cash-back-chad.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../user-cash-back-chad.test-samples';

import { UserCashBackChadService } from './user-cash-back-chad.service';

const requireRestSample: IUserCashBackChad = {
  ...sampleWithRequiredData,
};

describe('UserCashBackChad Service', () => {
  let service: UserCashBackChadService;
  let httpMock: HttpTestingController;
  let expectedResult: IUserCashBackChad | IUserCashBackChad[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(UserCashBackChadService);
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

    it('should create a UserCashBackChad', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const userCashBack = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(userCashBack).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a UserCashBackChad', () => {
      const userCashBack = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(userCashBack).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a UserCashBackChad', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of UserCashBackChad', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a UserCashBackChad', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addUserCashBackChadToCollectionIfMissing', () => {
      it('should add a UserCashBackChad to an empty array', () => {
        const userCashBack: IUserCashBackChad = sampleWithRequiredData;
        expectedResult = service.addUserCashBackChadToCollectionIfMissing([], userCashBack);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(userCashBack);
      });

      it('should not add a UserCashBackChad to an array that contains it', () => {
        const userCashBack: IUserCashBackChad = sampleWithRequiredData;
        const userCashBackCollection: IUserCashBackChad[] = [
          {
            ...userCashBack,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addUserCashBackChadToCollectionIfMissing(userCashBackCollection, userCashBack);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a UserCashBackChad to an array that doesn't contain it", () => {
        const userCashBack: IUserCashBackChad = sampleWithRequiredData;
        const userCashBackCollection: IUserCashBackChad[] = [sampleWithPartialData];
        expectedResult = service.addUserCashBackChadToCollectionIfMissing(userCashBackCollection, userCashBack);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(userCashBack);
      });

      it('should add only unique UserCashBackChad to an array', () => {
        const userCashBackArray: IUserCashBackChad[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const userCashBackCollection: IUserCashBackChad[] = [sampleWithRequiredData];
        expectedResult = service.addUserCashBackChadToCollectionIfMissing(userCashBackCollection, ...userCashBackArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const userCashBack: IUserCashBackChad = sampleWithRequiredData;
        const userCashBack2: IUserCashBackChad = sampleWithPartialData;
        expectedResult = service.addUserCashBackChadToCollectionIfMissing([], userCashBack, userCashBack2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(userCashBack);
        expect(expectedResult).toContain(userCashBack2);
      });

      it('should accept null and undefined values', () => {
        const userCashBack: IUserCashBackChad = sampleWithRequiredData;
        expectedResult = service.addUserCashBackChadToCollectionIfMissing([], null, userCashBack, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(userCashBack);
      });

      it('should return initial array if no UserCashBackChad is added', () => {
        const userCashBackCollection: IUserCashBackChad[] = [sampleWithRequiredData];
        expectedResult = service.addUserCashBackChadToCollectionIfMissing(userCashBackCollection, undefined, null);
        expect(expectedResult).toEqual(userCashBackCollection);
      });
    });

    describe('compareUserCashBackChad', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareUserCashBackChad(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareUserCashBackChad(entity1, entity2);
        const compareResult2 = service.compareUserCashBackChad(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareUserCashBackChad(entity1, entity2);
        const compareResult2 = service.compareUserCashBackChad(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareUserCashBackChad(entity1, entity2);
        const compareResult2 = service.compareUserCashBackChad(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
