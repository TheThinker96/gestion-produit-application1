import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../user-sale-account-chad.test-samples';

import { UserSaleAccountChadFormService } from './user-sale-account-chad-form.service';

describe('UserSaleAccountChad Form Service', () => {
  let service: UserSaleAccountChadFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UserSaleAccountChadFormService);
  });

  describe('Service methods', () => {
    describe('createUserSaleAccountChadFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createUserSaleAccountChadFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            statut: expect.any(Object),
            balance: expect.any(Object),
          })
        );
      });

      it('passing IUserSaleAccountChad should create a new form with FormGroup', () => {
        const formGroup = service.createUserSaleAccountChadFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            statut: expect.any(Object),
            balance: expect.any(Object),
          })
        );
      });
    });

    describe('getUserSaleAccountChad', () => {
      it('should return NewUserSaleAccountChad for default UserSaleAccountChad initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createUserSaleAccountChadFormGroup(sampleWithNewData);

        const userSaleAccount = service.getUserSaleAccountChad(formGroup) as any;

        expect(userSaleAccount).toMatchObject(sampleWithNewData);
      });

      it('should return NewUserSaleAccountChad for empty UserSaleAccountChad initial value', () => {
        const formGroup = service.createUserSaleAccountChadFormGroup();

        const userSaleAccount = service.getUserSaleAccountChad(formGroup) as any;

        expect(userSaleAccount).toMatchObject({});
      });

      it('should return IUserSaleAccountChad', () => {
        const formGroup = service.createUserSaleAccountChadFormGroup(sampleWithRequiredData);

        const userSaleAccount = service.getUserSaleAccountChad(formGroup) as any;

        expect(userSaleAccount).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IUserSaleAccountChad should not enable id FormControl', () => {
        const formGroup = service.createUserSaleAccountChadFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewUserSaleAccountChad should disable id FormControl', () => {
        const formGroup = service.createUserSaleAccountChadFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
