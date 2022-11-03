import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IUserSaleAccountChad } from '../user-sale-account-chad.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../user-sale-account-chad.test-samples';

import { UserSaleAccountChadService } from './user-sale-account-chad.service';

const requireRestSample: IUserSaleAccountChad = {
  ...sampleWithRequiredData,
};

describe('UserSaleAccountChad Service', () => {
  let service: UserSaleAccountChadService;
  let httpMock: HttpTestingController;
  let expectedResult: IUserSaleAccountChad | IUserSaleAccountChad[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(UserSaleAccountChadService);
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

    it('should create a UserSaleAccountChad', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const userSaleAccount = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(userSaleAccount).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a UserSaleAccountChad', () => {
      const userSaleAccount = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(userSaleAccount).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a UserSaleAccountChad', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of UserSaleAccountChad', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a UserSaleAccountChad', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addUserSaleAccountChadToCollectionIfMissing', () => {
      it('should add a UserSaleAccountChad to an empty array', () => {
        const userSaleAccount: IUserSaleAccountChad = sampleWithRequiredData;
        expectedResult = service.addUserSaleAccountChadToCollectionIfMissing([], userSaleAccount);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(userSaleAccount);
      });

      it('should not add a UserSaleAccountChad to an array that contains it', () => {
        const userSaleAccount: IUserSaleAccountChad = sampleWithRequiredData;
        const userSaleAccountCollection: IUserSaleAccountChad[] = [
          {
            ...userSaleAccount,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addUserSaleAccountChadToCollectionIfMissing(userSaleAccountCollection, userSaleAccount);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a UserSaleAccountChad to an array that doesn't contain it", () => {
        const userSaleAccount: IUserSaleAccountChad = sampleWithRequiredData;
        const userSaleAccountCollection: IUserSaleAccountChad[] = [sampleWithPartialData];
        expectedResult = service.addUserSaleAccountChadToCollectionIfMissing(userSaleAccountCollection, userSaleAccount);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(userSaleAccount);
      });

      it('should add only unique UserSaleAccountChad to an array', () => {
        const userSaleAccountArray: IUserSaleAccountChad[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const userSaleAccountCollection: IUserSaleAccountChad[] = [sampleWithRequiredData];
        expectedResult = service.addUserSaleAccountChadToCollectionIfMissing(userSaleAccountCollection, ...userSaleAccountArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const userSaleAccount: IUserSaleAccountChad = sampleWithRequiredData;
        const userSaleAccount2: IUserSaleAccountChad = sampleWithPartialData;
        expectedResult = service.addUserSaleAccountChadToCollectionIfMissing([], userSaleAccount, userSaleAccount2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(userSaleAccount);
        expect(expectedResult).toContain(userSaleAccount2);
      });

      it('should accept null and undefined values', () => {
        const userSaleAccount: IUserSaleAccountChad = sampleWithRequiredData;
        expectedResult = service.addUserSaleAccountChadToCollectionIfMissing([], null, userSaleAccount, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(userSaleAccount);
      });

      it('should return initial array if no UserSaleAccountChad is added', () => {
        const userSaleAccountCollection: IUserSaleAccountChad[] = [sampleWithRequiredData];
        expectedResult = service.addUserSaleAccountChadToCollectionIfMissing(userSaleAccountCollection, undefined, null);
        expect(expectedResult).toEqual(userSaleAccountCollection);
      });
    });

    describe('compareUserSaleAccountChad', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareUserSaleAccountChad(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareUserSaleAccountChad(entity1, entity2);
        const compareResult2 = service.compareUserSaleAccountChad(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareUserSaleAccountChad(entity1, entity2);
        const compareResult2 = service.compareUserSaleAccountChad(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareUserSaleAccountChad(entity1, entity2);
        const compareResult2 = service.compareUserSaleAccountChad(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
